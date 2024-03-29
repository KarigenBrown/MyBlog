package me.myblog.framework.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import me.myblog.framework.enums.HttpCodeEnum;

import java.io.Serializable;

/**
 * @author Karigen B
 * @create 2022-10-29 20:00
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> Response<T> ok(T data) {
        return new Response<T>()
                .setCode(HttpCodeEnum.SUCCESS.getCode())
                .setMessage(HttpCodeEnum.SUCCESS.getMessage())
                .setData(data);
    }

    public static Response<Object> ok() {
        return Response.ok(null);
    }

    public static <T> Response<T> error(int code, String message) {
        return new Response<T>()
                .setCode(code)
                .setMessage(message);
    }

    public static Response<Object> error(HttpCodeEnum httpCodeEnum) {
        return new Response<>()
                .setCode(httpCodeEnum.getCode())
                .setMessage(httpCodeEnum.getMessage());
    }
}
