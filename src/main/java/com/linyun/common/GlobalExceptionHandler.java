package com.linyun.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Rest<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return Rest.error(msg);
        }

        return Rest.error("未知错误");
    }

    /**
     * 自定义异常捕获
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Rest<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());
        return Rest.error(ex.getMessage());
    }
}
