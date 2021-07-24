package top.bluewort.Notes.base_utils.X009_json_add_bean_field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Aspect
@Component
public class AddBeanField {
    @Resource
    private DictItemEnum dictItemEnum;

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

        if(!ObjectUtils.isEmpty(data) && !(data instanceof String)){
            data = dictItemEnum.addDictItem(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
            r.put("data",data);
            return r;
        }
        return  result;
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
        }
        return result;
    }
}
