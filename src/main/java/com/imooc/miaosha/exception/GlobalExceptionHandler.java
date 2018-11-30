package com.imooc.miaosha.exception;


import com.imooc.miaosha.domain.CodeMsg;
import com.imooc.miaosha.domain.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler { //定义全局异常处理类

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) { //处理所有 Controller 层抛出的 Exception 及其子类的异常
        e.printStackTrace();
        if (e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.buildError(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
            return Result.buildError(CodeMsg.SERVER_ERROR);
        }
    }
}
