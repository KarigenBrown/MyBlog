package me.blog.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.blog.framework.mapper.UserMapper;
import me.blog.framework.entity.User;
import me.blog.framework.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

