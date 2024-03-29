package com.piggie.handler;

import com.piggie.constant.MessageConstant;
import com.piggie.exception.BaseException;
import com.piggie.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("Exception message：no no no {}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String returnMessage = username.replace("\'", "") + " " + MessageConstant.ALREADY_EXISTS;
            return Result.error(returnMessage);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
