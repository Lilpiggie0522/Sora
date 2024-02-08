package com.piggie.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("category model")
public class CategoryDTO implements Serializable {

    //主键
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("type of dish")
    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    @ApiModelProperty("name")
    //分类名称
    private String name;

    @ApiModelProperty("sort by what")
    //排序
    private Integer sort;

}
