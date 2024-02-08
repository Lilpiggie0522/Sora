package com.piggie.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("page query dto")
public class EmployeePageQueryDTO implements Serializable {

    @ApiModelProperty("query name")
    //员工姓名
    private String name;

    @ApiModelProperty("query page number")
    //页码
    private int page;

    @ApiModelProperty("query page size")
    //每页显示记录数
    private int pageSize;

}
