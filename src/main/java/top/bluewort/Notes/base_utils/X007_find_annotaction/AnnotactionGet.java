package top.bluewort.Notes.base_utils.X007_find_annotaction;

import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 常用查询获取注解方式
 */
public class AnnotactionGet {
    public static void main(String[] args) {
        Object test = new BeautyTest();
        //1. jdk自带  注意RetentionPolicy 为run的注解才能被发现
        // 类查询
        System.out.println("class anno1::"+test.getClass().getAnnotations()[0]);
        System.out.println("class anno2::"+test.getClass().getAnnotation(Beauty.class));
        // 方法查询
        System.out.println("method anno1::"+test.getClass().getDeclaredMethods()[0].getAnnotations()[0]);
        System.out.println("method anno1::"+test.getClass().getDeclaredMethods()[0].getAnnotation(Beauty.class));
        //字段查询
        System.out.println("field anno1::"+test.getClass().getDeclaredFields()[0].getAnnotations()[0]);
        System.out.println("field anno2::"+test.getClass().getDeclaredFields()[0].getAnnotation(Beauty.class));
        //2. spring包
        System.out.println("-------------------------------------------------------");
        //注解查询注解
        System.out.println("getAnno::"+AnnotationUtils.getAnnotation(Beauty2.class,Beauty.class));
        //类查询
        System.out.println("class anno1::"+AnnotationUtils.findAnnotation(test.getClass(),Beauty.class));
        //方法查询
        System.out.println("method anno1::"+AnnotationUtils.findAnnotation(test.getClass().getDeclaredMethods()[0],Beauty.class));
        //字段查询
        System.out.println("field anno1::"+AnnotationUtils.findAnnotation(test.getClass().getDeclaredFields()[0],Beauty.class));

    }
}
