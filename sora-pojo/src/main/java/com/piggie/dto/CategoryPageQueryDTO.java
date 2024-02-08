package com.piggie.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("category page query")
public class CategoryPageQueryDTO implements Serializable {
    @ApiModelProperty("query page number")
    //页码
    private int page;

    //每页记录数
    @ApiModelProperty("query page size")
    private int pageSize;

    //分类名称
    @ApiModelProperty("query name")
    private String name;

    //分类类型 1菜品分类  2套餐分类
    @ApiModelProperty("combo type")
    private Integer type;

}
