package top.bluewort.Notes.base_utils.X008_reflt_add_bean_field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author csc
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictItem {
    /**
     * 指定字段项新加字段后缀区分
     * @return
     */
    String dictStr() default "Name";
}
