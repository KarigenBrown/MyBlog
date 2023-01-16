package me.myblog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.myblog.framework.domain.entity.User;
import me.myblog.framework.domain.vo.LoginUserVo;
import org.eclipse.collections.api.tuple.Pair;
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
    Pair<String, User> userLogin(User user);

    void logout();

    User getUserDetails();

    void putUserDetails(User user);

    void register(User user);

    String administratorLogin(User user);

    void administratorLogout();
}

