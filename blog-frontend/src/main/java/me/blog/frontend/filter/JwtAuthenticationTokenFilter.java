package me.blog.frontend.filter;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import me.blog.framework.domain.Response;
import me.blog.framework.domain.entity.User;
import me.blog.framework.enums.HttpCodeEnum;
import me.blog.framework.utils.JwtUtils;
import me.blog.framework.utils.RedisCacheUtils;
import me.blog.framework.utils.WebUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karigen B
 * @create 2022-11-04 20:39
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 说明该接口不需要登陆,直接放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // token超时,token非法
            // 响应告诉前端需要重新登陆
            Response<Object> result = Response.error(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userid = claims.getSubject();
        // 从redis中获取用户信息
        User loginUser = redisCacheUtils.getCacheObject("blogLogin:" + userid);
        // 如果获取不到
        if (Objects.isNull(loginUser)) {
            Response<Object> result = Response.error(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        // 存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
