package com.piggie.aspect;

import com.piggie.annotation.AutoFill;
import com.piggie.constant.AutoFillConstant;
import com.piggie.context.BaseContext;
import com.piggie.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * ClassName: AutoFillAspect
 * Package: com.piggie.aspect
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 2:11 pm
 * @Version 1.0
 */
@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    /**
     * point cut definition
     */
    @Pointcut("execution(* com.piggie.mapper.*.*(..)) && @annotation(com.piggie.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * Before advice binds to autoFillPointCut
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("start auto-filling public columns");
        //  Obtain current intercepted method's database operation type, insert? update,etc
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();

        //  Obtain parameters of this method, the entity that is passed in, Employee or Category?
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];
        //  Prepare updateTime, updateUser to entity acquired above
        LocalDateTime updateTime = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //  Assign values to entity according to intercepted method's operation type
        if (operationType == OperationType.INSERT) {
            //  all 4 columns
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entity, LocalDateTime.now());
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, updateTime);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType == OperationType.UPDATE) {
            //  just 2 columns
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity, updateTime);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
