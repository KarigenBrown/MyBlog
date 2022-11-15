package me.myblog.framework.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.myblog.framework.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Karigen B
 * @create 2022-11-06 17:56
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception exception) {
            exception.printStackTrace();
            userId = -1L;// 表示是自己创建的
        }
        this.setFieldValByName("createTime", new Date(), metaObject)
                .setFieldValByName("createBy", userId, metaObject)
                .setFieldValByName("updateTime", new Date(), metaObject)
                .setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", new Date(), metaObject)
                .setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}
