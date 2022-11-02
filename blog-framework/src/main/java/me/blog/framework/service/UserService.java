package me.blog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.blog.framework.domain.entity.User;
import me.blog.framework.domain.vo.LoginUserVo;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
public interface UserService extends IService<User> {

    LoginUserVo login(User user);
}

