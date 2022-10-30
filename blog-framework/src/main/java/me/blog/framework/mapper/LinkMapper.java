package me.blog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.blog.framework.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 链接(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

