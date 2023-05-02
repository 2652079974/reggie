package org.example.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Maplerain
 * @date 2023/4/6 13:16
 **/
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        String msg = e.getMessage();
        e.printStackTrace();
        if(msg.contains("Duplicate entry")){
            String value = msg.split(" ")[2];
            return Result.error(value+" 已存在!");
        }

        return Result.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(CustomException e) {
        String msg = e.getMessage();
        return Result.error(msg);
    }
}
