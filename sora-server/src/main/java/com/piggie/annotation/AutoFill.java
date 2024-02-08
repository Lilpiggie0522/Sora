package com.piggie.annotation;

import com.piggie.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: AutoFill
 * Package: com.piggie.annotation
 * Description: Customized annotation, used when certain method needs autofill
 *
 * @Author Piggie
 * @Create 8/02/2024 12:11 am
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();
}
