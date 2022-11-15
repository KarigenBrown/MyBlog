package me.myblog.framework.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.myblog.framework.config.MinioConfig;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.vo.LoginUserVo;
import me.myblog.framework.domain.vo.UserDetailsVo;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.mapper.UserMapper;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.service.UserService;
import me.myblog.framework.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Map<String, Object> userLogin(User user) {
        /*UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        Optional.ofNullable(authentication)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 获取userid生成token
        User loginUser = (User) authentication.getPrincipal();*/
        User loginUser = getAuthenticatedUser(user);
        String userId = loginUser.getId().toString();
        String jwt = JwtUtils.createJWT(userId);

        // 把用户信息存入redis
        redisCacheUtils.setCacheObject(SystemConstants.USER_LOGIN_KEY_PREFIX + userId, loginUser);

        return Map.of(
                "jwt", jwt,
                "loginUser", loginUser
        );
    }

    private User getAuthenticatedUser(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        Optional.ofNullable(authentication)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 获取userid生成token
        return (User) authentication.getPrincipal();
    }

    @Override
    public void logout() {
        // 获取token,解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = (User) authentication.getPrincipal();
        // 获取userid
        Long userId = loginUser.getId();
        // 删除redis中的用户信息
        redisCacheUtils.deleteCacheObject(SystemConstants.USER_LOGIN_KEY_PREFIX + userId);
    }

    @Override
    public User getUserDetails() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id查询用户信息
        return this.getById(userId);
    }

    @Override
    public void putUserDetails(User user) {
        this.updateById(user);
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

        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 1.
        /*String[] split = Objects.requireNonNull(originalFilename).split("\\.");
        String extendName = split[split.length - 1];
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + "." + extendName;*/

        // 2.
        /*int index = originalFilename.lastIndexOf(".");
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + originalFilename.substring(index);*/

        // 3.
        /*StringBuilder stringBuilder = new StringBuilder();
        int index = originalFilename.lastIndexOf(".");
        String fileName = stringBuilder.append(dateFormat.format(new Date()))
                .append("/")
                .append(IdUtil.fastSimpleUUID())
                .append(originalFilename.lastIndexOf(index))
                .toString();*/

        // 4.
        String fileName = PathUtils.dateUuidPath(originalFilename);

        // 如果判断通过上传文件到OSS
        return minioUtils.upload(
                minioConfig.getBucketName(),
                fileName,
                userPhoto
        );
    }

    @Override
    public void register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(HttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(HttpCodeEnum.NICKNAME_NOT_NULL);
        }
        // 对数据进行是否存在的判断
        if (userNameExist(user.getUserName())) {
            throw new SystemException(HttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(HttpCodeEnum.NICKNAME_EXIST);
        }
        // ...
        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // 存入数据库
        this.save(user);
    }

    @Override
    public String administratorLogin(User user) {
        /*UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        Optional.ofNullable(authentication)
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // 获取userid生成token
        User loginUser = (User) authentication.getPrincipal();*/
        User loginUser = getAuthenticatedUser(user);
        String userId = loginUser.getId().toString();
        String jwt = JwtUtils.createJWT(userId);

        // 把用户信息存入redis
        redisCacheUtils.setCacheObject(SystemConstants.ADMINISTRATOR_LOGIN_KEY_PREFIX + userId, loginUser);

        return jwt;
    }

    private boolean nickNameExist(String nickName) {
        return this.count(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getNickName, nickName)
        ) > 0;
    }

    private boolean userNameExist(String userName) {
        return this.count(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getUserName, userName)
        ) > 0;
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

