package me.blog.framework.exception;

import lombok.Getter;
import me.blog.framework.enums.HttpCodeEnum;

/**
 * @author Karigen B
 * @create 2022-11-04 22:59
 */
@Getter
public class SystemException extends RuntimeException {
    private int code;
    private String message;

    public SystemException(HttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMessage());
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMessage();
    }
}
