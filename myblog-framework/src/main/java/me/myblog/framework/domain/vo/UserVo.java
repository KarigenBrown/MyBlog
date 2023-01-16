package me.myblog.framework.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Karigen B
 * @create 2023-01-16 13:43
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String status;
    private String email;
    private String phoneNumber;
    private String sex;
    private String avatar;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
}
