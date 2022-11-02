package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.domain.vo.LoginUserVo;
import me.blog.framework.domain.vo.UserDetailsVo;
import me.blog.framework.mapper.UserMapper;
import me.blog.framework.domain.entity.User;
import me.blog.framework.service.UserService;
import me.blog.framework.utils.BeanCopyUtils;
import me.blog.framework.utils.JwtUtils;
import me.blog.framework.utils.RedisCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @Override
    public LoginUserVo login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        Optional.ofNullable(authentication)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 获取userid生成token
        User loginUser = (User) authentication.getPrincipal();
        String userId = loginUser.getId().toString();
        String jwt = JwtUtils.createJWT(userId);

        // 把用户信息存入redis
        redisCacheUtils.setCacheObject("blogLogin:" + userId, loginUser);

        // 把User转化成UserInfoVo
        UserDetailsVo userDetailsVo = BeanCopyUtils.copyBean(loginUser, UserDetailsVo.class);

        // 把token和userInfo封装返回
        return new LoginUserVo(jwt, userDetailsVo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        User user = baseMapper.selectOne(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserName, username)
        );

        // 判断是否查到用户,如果没查到抛出异常
        Optional.ofNullable(user)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 返回用户信息
        return user;
    }
}

