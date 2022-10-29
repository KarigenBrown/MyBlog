package me.blog.framework.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import me.blog.framework.enums.HttpCodeEnum;

import java.io.Serializable;

/**
 * @author Karigen B
 * @create 2022-10-29 20:00
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseResult<T> ok(T data) {
        return new ResponseResult<T>()
                .setCode(HttpCodeEnum.SUCCESS.getCode())
                .setMessage(HttpCodeEnum.SUCCESS.getMessage())
                .setData(data);
    }

    public static <T> ResponseResult<T> error(int code, String message) {
        return new ResponseResult<T>()
                .setCode(code)
                .setMessage(message);
    }

    public static ResponseResult<Object> error(HttpCodeEnum httpCodeEnum) {
        return new ResponseResult<>()
                .setCode(httpCodeEnum.getCode())
                .setMessage(httpCodeEnum.getMessage());
    }
}