package me.myblog.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.myblog.framework.domain.entity.Link;

import java.util.List;

/**
 * 链接(Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
public interface LinkService extends IService<Link> {

    List<Link> allLink();
}

