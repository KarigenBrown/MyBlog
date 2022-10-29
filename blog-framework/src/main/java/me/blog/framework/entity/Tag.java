package me.blog.framework.entity;

import java.util.Date;
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
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    
    private Long id;
    //标签名
    private String name;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志(0代表未删除,1代表已删除)
    private Integer deleteFlag;
    //备注
    private String remark;

}
