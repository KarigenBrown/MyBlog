package me.myblog.framework.utils;

import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Karigen B
 * @create 2022-11-06 17:43
 */
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getLoginUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        return getLoginUser().getId();
    }

    public static Boolean isAdministrator() {
        Long userId = getUserId();
        return SystemConstants.ADMINISTRATOR.equals(userId);
    }
}
