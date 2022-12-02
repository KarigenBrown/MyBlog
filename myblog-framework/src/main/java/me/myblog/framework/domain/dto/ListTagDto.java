package me.myblog.framework.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Karigen B
 * @create 2022-12-02 21:23
 */
@Data
@ApiModel
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ListTagDto {
    private String name;
    private String remark;
}
