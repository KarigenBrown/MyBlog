package me.blog.framework.domain.entity;

import java.util.Date;
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
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    private Long id;
    //分类名
    private String name;
    //父分类id,如果没有父分类为-1
    private Long parentId;
    //描述
    private String description;
    //状态(0正常,1禁用)
    private String status;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志(0代表未删除,1代表已删除)
    private Integer deleteFlag;

}
