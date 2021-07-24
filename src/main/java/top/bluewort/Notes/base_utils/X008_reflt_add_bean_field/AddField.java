package top.bluewort.Notes.base_utils.X008_reflt_add_bean_field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * aop 通过反射的方式  为controller返回值对象增加属性
 */
@Aspect
@Component
public class AddField {
    private static Logger logger = LoggerFactory.getLogger(AddField.class);
    @Pointcut("execution (* com.juncdt.pullfish_api_mini.controller..*.*(..))")
    public void aop() {}
    /**
     * 环绕切面记录
     * @param joinPoint
     * @return
     */
    @Around(value = "aop()")
    public Object saveAround(ProceedingJoinPoint joinPoint){
        //1.流程执行完
        Object result = proceed(joinPoint);

        //2.解析返回参数类型  非json数据和空数据 直接返回
        R r = (R) result;
        Object data = r.get("data");

        if(ObjectUtils.isEmpty(data) && data instanceof String){
            return result;
        }

        //3.判断实体和具体参数注解
        //3.1 数据遍历（判定需要添加的属性值用 map 格式标识  其中map的格式为 {key1;key11;key111:String}）
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map = calcMap(map,data,3,"");
        //3.2 源数据JSON对象格式化 依次遍历和添加map中的新数据
        JSONObject js = JSONObject.parseObject(JSON.toJSONString(data,SerializerFeature.WriteMapNullValue), Feature.IgnoreAutoType);
        js = calcJSONObj(js,map);
        //3.2 判断是否分页对象 或 List对象  进行特殊遍历递归

        //4.注入新的参数
        r.put("data",js);
        return r;
    }
    /**
     * 执行切点
     * @param joinPoint
     * @return
     */
    private Object proceed(ProceedingJoinPoint joinPoint){
        Object result = null;
        try {
            result = joinPoint.proceed();
        }catch (Throwable e){
            e.printStackTrace();
            logger.error("呼叫管理员，执行业务报错");
        }
        return result;
    }

    /**
     * 外层迭代器遍历
     * @param jsonObject
     * @param map
     * @return
     */
    private JSONObject calcJSONObj(JSONObject jsonObject, ConcurrentHashMap map){
        //1. 分层  迭代  遍历
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Object> m = (Map.Entry)iterator.next();
            String key = m.getKey();
            key = key.substring(1);
            Object value = m.getValue();
            //2. 赋值 回填
            String[] strs = key.split(";");
            calcJSONObjchild(strs,value,jsonObject);
        }
        return jsonObject;
    }

    /**
     * 内层递归器 遍历
     * @param strs
     * @param val
     * @param jsonObject
     * @return
     */
    private void calcJSONObjchild(String[] strs,Object val,JSONObject jsonObject){
        //1.模拟从换种取出数据
        Object o = "DICTITEM:" + val;
        if(strs.length==1){
            jsonObject.put(strs[0], ObjectUtils.isEmpty(o)?val:o);
        }
        else if(strs.length>1){
            calcJSONObjchild(copyArray(strs,1,strs.length),val,jsonObject.getJSONObject(strs[0]));
        }
    }

    /**
     * 数组复制
     * @param strs
     * @param start
     * @param end
     * @return
     */
    private String[] copyArray(String[] strs,int start,int end){
        String[] rtn = new String[end-start];
        System.arraycopy(strs,start,rtn,0,rtn.length);
        return rtn;
    }

    /**
     * 递归遍历所有对象的属性  解析包含固定注解条件的key和val
     * @param map  递归返回map
     * @param clazzObj 类对象
     * @param deep 递归深度
     * @param pn key前缀
     * @return
     */
    private ConcurrentHashMap calcMap(ConcurrentHashMap map,Object clazzObj,int deep,String pn){
        //1. 判定递归深度
        if(deep<1){
            return map;
        }
        //2. 判定注解是否存在
        Field[] fields = clazzObj.getClass().getDeclaredFields();
        for(Field field: fields){
            DictItem annotation = field.getAnnotation(DictItem.class);
            if(!ObjectUtils.isEmpty(annotation)) {
                field.setAccessible(true);
                map.put(pn+";"+field.getName()+annotation.dictStr(),getFieldValue(field,clazzObj));
            }else {
                map = calcMap(map,field.getType(),--deep,pn+";"+field.getAnnotations());
            }
        }
        return map;
    }

    /**
     * 获取类对象的对应参数值
     * @param field  参数值域
     * @param object  类对象
     * @return
     */
    private String getFieldValue(Field field,Object object){
        String val = "";
        try {
            //判断反射域的类型
            //1.String类型
            if (field.getType().getName().equals("java.lang.String")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                String value = (String)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value;
            }
            //2. Integer 类型
            if (field.getType().getName().equals("java.lang.Integer")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Integer value = (Integer)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //3. Long 类型
            if (field.getType().getName().equals("java.lang.Long")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Long value = (Long)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //4. Double 类型
            if (field.getType().getName().equals("java.lang.Double")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Double value = (Double)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //5. Float 类型
            if (field.getType().getName().equals("java.lang.Float")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Float value = (Float)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //6. Boolean 类型
            if (field.getType().getName().equals("java.lang.Boolean")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Boolean value = (Boolean)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //7. Short 类型
            if (field.getType().getName().equals("java.lang.Short")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Short value = (Short)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //8. Byte 类型
            if (field.getType().getName().equals("java.lang.Byte")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Byte value = (Byte)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //9. Character 类型
            if (field.getType().getName().equals("java.lang.Character")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Character value = (Character)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //10. Date 类型
            if (field.getType().getName().equals("java.util.Date")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                Date value = (Date)m.invoke(object);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(!ObjectUtils.isEmpty(value))val = sdf.format(value);
            }
            //11. BigDecimal 类型
            if (field.getType().getName().equals("java.math.BigDecimal")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                BigDecimal value = (BigDecimal)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //12. List 类型
            if (field.getType().getName().equals("java.util.List")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                List value = (List)m.invoke(object);
                if(!ObjectUtils.isEmpty(value))val = value+"";
            }
            //13. LocalDate 类型
            if (field.getType().getName().equals("java.time.LocalDate")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                LocalDate value = (LocalDate)m.invoke(object);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if(!ObjectUtils.isEmpty(value))val = formatter.format(value);
            }
            //14. LocalTime 类型
            if (field.getType().getName().equals("java.time.LocalTime")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                LocalTime value = (LocalTime)m.invoke(object);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                if(!ObjectUtils.isEmpty(value))val = formatter.format(value);
            }
            //15. LocalDateTime 类型
            if (field.getType().getName().equals("java.time.LocalDateTime")) {
                Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
                LocalDateTime value = (LocalDateTime)m.invoke(object);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if(!ObjectUtils.isEmpty(value))val = formatter.format(value);
            }
        }catch (Exception e){}
        return val;
    }
    //拼接 参数实体get请求   属性值  首字母大写
    private String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte)((char)items[0]-'a'+'A');
        return new String(items);
    }
}
