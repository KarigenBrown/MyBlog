package me.blog.framework.entity;

import java.io.Serializable;

/**
 * @author Karigen B
 * @create 2022-10-29 20:00
 */
public class JsonResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
}
