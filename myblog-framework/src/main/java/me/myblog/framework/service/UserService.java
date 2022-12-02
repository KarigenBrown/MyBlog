package me.myblog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.vo.LoginUserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:31
 */
public interface UserService extends IService<User> {

    // 多个token,没办法
    Map<String,Object> userLogin(User user);

    void logout();

    User getUserDetails();

    void putUserDetails(User user);

    String userPhoto(MultipartFile userPhoto);

    void register(User user);

    String administratorLogin(User user);

    void administratorLogout();
}

