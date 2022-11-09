package me.blog.framework.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.config.MinioConfig;
import me.blog.framework.domain.vo.LoginUserVo;
import me.blog.framework.domain.vo.UserDetailsVo;
import me.blog.framework.enums.HttpCodeEnum;
import me.blog.framework.exception.SystemException;
import me.blog.framework.mapper.UserMapper;
import me.blog.framework.domain.entity.User;
import me.blog.framework.service.UserService;
import me.blog.framework.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
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

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

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
    public void logout() {
        // 获取token,解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = (User) authentication.getPrincipal();
        // 获取userid
        Long userId = loginUser.getId();
        // 删除redis中的用户信息
        redisCacheUtils.deleteCacheObject("blogLogin:" + userId);
    }

    @Override
    public User userDetails() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        return this.getById(userId);
    }

    @Override
    public String userPhoto(MultipartFile userPhoto) {
        // 判断文件类型或者文件大小
        // 获取原始文件名
        String originalFilename = userPhoto.getOriginalFilename();
        // 对原始文件名进行判断
        if (originalFilename == null || !originalFilename.matches("^.+\\.(png|jpg|jpeg)$")) {
            throw new SystemException(HttpCodeEnum.FILE_TYPE_ERROR);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 1.
        /*String[] split = Objects.requireNonNull(originalFilename).split("\\.");
        String extendName = split[split.length - 1];
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + "." + extendName;*/

        // 2.
        int index = originalFilename.lastIndexOf(".");
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + originalFilename.substring(index);

        // 3.
        /*StringBuilder stringBuilder = new StringBuilder();
        int index = originalFilename.lastIndexOf(".");
        String fileName = stringBuilder.append(dateFormat.format(new Date()))
                .append("/")
                .append(IdUtil.fastSimpleUUID())
                .append(originalFilename.lastIndexOf(index))
                .toString();*/

        // 如果判断通过上传文件到OSS
        minioUtils.upload(
                minioConfig.getBucketName(),
                fileName,
                userPhoto
        );
        return minioUtils.getUploadObjectUrl(
                minioConfig.getBucketName(),
                fileName,
                7 * 24 * 60 * 60
        );
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

