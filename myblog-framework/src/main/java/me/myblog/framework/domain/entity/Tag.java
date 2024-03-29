package me.myblog.framework.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 标签(Tag)表实体类
 *
 * @author makejava
 * @since 2022-10-29 18:26:30
 */
@Data
@TableName("tag")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @TableId(type = IdType.AUTO)
    private Long id;
    //标签名
    private String name;
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
    //备注
    private String remark;

}
