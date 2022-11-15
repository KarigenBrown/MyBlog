package me.myblog.framework.handler.exception;

import lombok.extern.slf4j.Slf4j;
import me.myblog.framework.domain.Response;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Karigen B
 * @create 2022-11-04 23:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public Response<Object> systemExceptionHandler(SystemException systemException) {
        // 打印异常信息
        log.error(systemException.getMessage());
        // 从异常对象中获取提示信息封装返回
        return Response.error(systemException.getCode(), systemException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response<Object> exceptionHandler(Exception exception) {
        // 打印异常信息
        log.error(exception.getMessage());
        // 从异常对象中获取提示信息封装返回
        return Response.error(HttpCodeEnum.SYSTEM_ERROR.getCode(), exception.getMessage());
    }
}
