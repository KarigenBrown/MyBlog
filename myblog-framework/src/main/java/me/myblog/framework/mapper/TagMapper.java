package me.myblog.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.myblog.framework.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

