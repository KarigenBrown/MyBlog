package me.myblog.framework.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 链接(Link)表实体类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Data
@TableName("link")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String logo;
    
    private String description;
    //网站地址
    private String address;
    //审核状态(0代表审核通过,1代表审核未通过,2代表未审核)
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
