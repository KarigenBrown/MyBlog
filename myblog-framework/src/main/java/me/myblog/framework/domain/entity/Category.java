package me.myblog.framework.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Data
@TableName("category")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;
    //分类名
    private String name;
    //父分类id,如果没有父分类为-1
    private Long parentId;
    //描述
    private String description;
    //状态(0正常,1禁用)
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志(0代表未删除,1代表已删除)
    @TableLogic
    private Integer deleteFlag;

}
