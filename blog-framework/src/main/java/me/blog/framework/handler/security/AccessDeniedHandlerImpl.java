package me.blog.framework.handler.security;

import com.alibaba.fastjson2.JSON;
import me.blog.framework.domain.Response;
import me.blog.framework.enums.HttpCodeEnum;
import me.blog.framework.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Karigen B
 * @create 2022-11-04 22:23
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        Response<Object> result = Response.error(HttpCodeEnum.NO_OPERATOR_AUTH);
        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
