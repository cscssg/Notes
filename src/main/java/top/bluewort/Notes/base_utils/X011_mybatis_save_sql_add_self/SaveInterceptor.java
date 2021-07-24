package top.bluewort.Notes.base_utils.X011_mybatis_save_sql_add_self;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @Auther: csc
 * @Date: 202
 * @Description:
 * //切面自动添加默认字段
 */
@Aspect
@Component
public class SaveInterceptor implements Ordered {

    private static Logger logger = LoggerFactory.getLogger(SaveInterceptor.class);

    @Resource
    private HttpServletRequest request;
    /**
     * 仅记录管理员工操作记录
     */
    @Pointcut("execution (* com.juncdt.pullfish_service.dao..*.*(..))")
    public void saveInter() {}

    /**
     * 环绕切面记录
     * @param joinPoint
     * @return
     */
    @Around(value = "saveInter()")
    public Object saveAround(ProceedingJoinPoint joinPoint){
        //1. 判断是否保存类方法
        String methodName = joinPoint.getSignature().getName().toLowerCase();
        if(methodName.contains("save") || methodName.contains("insert")){
            logger.info("start the method name is:【{}】",methodName);
            //2.修改参数
            filterBodyColums(joinPoint.getArgs());
        }
        Object result = proceed(joinPoint);
        return result;
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
     * 包装 参数在body中的数据（
     * @param args
     * @return
     */
    private void filterBodyColums(Object[] args){
        if(!ObjectUtils.isEmpty(args)){
            for(Object arg:args){
                //1 判断自定义对象（是否）
                if(findSelfDto(arg)){
                    Field[] fields = arg.getClass().getDeclaredFields();
                    for(int x=0;x<fields.length;x++){
                        Field field = fields[x];
                        field.setAccessible(true);
                        //2 查询参数名称
                        String fieldName = field.getName();
                        //3 查询参数值
                        String fieldValue = getFieldValue(field,args[0]);
                        //4 设置所有分类的默认值
                        try {
                            //4.1 删除状态设置
                            if (fieldName.equals("del_flag") && ObjectUtils.isEmpty(fieldValue)) {
                                field.set(arg, 0);
                            }
                            //4.2 创建时间设置
                            if (fieldName.equals("create_time") && ObjectUtils.isEmpty(fieldValue)) {
                                field.set(arg, new Date());
                            }
                        }catch (IllegalAccessException ie){
                            ie.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 查询对象是否自定义对象
     * @param obj
     * @return
     */
    private boolean findSelfDto(Object obj){
        String simpleName = obj.getClass().getSimpleName();
        boolean rtn = false;
        switch (simpleName){
            case "String":
                rtn= false;break;
            case "Integer":
                rtn =  false;break;
            case "Long":
                rtn = false;break;
            case "Double":
                rtn = false;break;
            case "Float":
                rtn = false;break;
            case "Boolean":
                rtn = false;break;
            case "Short":
                rtn = false;break;
            case "Byte":
                rtn = false;break;
            case "Character":
                rtn = false;break;
            case "Date":
                rtn = false;break;
            case "BigDecimal":
                rtn = false;break;
            case "List":
                rtn = false;break;
            case "LocalDate":
                rtn = false;break;
            case "LocalTime":
                rtn = false;break;
            case "LocalDateTime":
                rtn = false;break;
            default:
                rtn = true;
        }
        return rtn;
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

    @Override
    public int getOrder() {
        return 2;
    }
}

