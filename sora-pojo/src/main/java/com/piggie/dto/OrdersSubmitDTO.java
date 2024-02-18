package com.piggie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    //地址簿id
    private Long addressBookId; // done
    //付款方式
    private int payMethod; // done
    //备注
    private String remark; // done
    //预计送达时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime; // done
    //配送状态  1立即送出  0选择具体时间
    private Integer deliveryStatus; // done
    //餐具数量
    private Integer tablewareNumber; // done
    //餐具数量状态  1按餐量提供  0选择具体数量
    private Integer tablewareStatus; // done
    //打包费
    private Integer packAmount; // done
    //总金额
    private BigDecimal amount; // done
}
