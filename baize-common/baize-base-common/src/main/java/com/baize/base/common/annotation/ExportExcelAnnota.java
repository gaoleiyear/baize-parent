package com.baize.base.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel报表导出
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
public @interface ExportExcelAnnota {

    /**
     * 中文值
     * @return
     */
    String value() default "";

    /**
     * 名称
     * @return
     */
    String name() default "";

    /**
     * 是否是字典
     * @return
     */
    boolean dic() default false;

    /**
     * 字典主表名称
     * @return
     */
    String dicName() default "";

    /**
     * 字典主表编码
     * @return
     */
    String dicCode() default "";

    /**
     * 是否是时间
     * @return
     */
    boolean date() default false;

    /**
     * 时间格式化
     * @return
     */
    String pattern() default "yyyy-MM-dd";
}

