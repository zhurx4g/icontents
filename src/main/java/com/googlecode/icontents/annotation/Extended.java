package com.googlecode.icontents.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注,用户表示某一个字段是扩充的字段
 * @author 朱瑞贤
 *
 */
@Documented
@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface Extended {
} 