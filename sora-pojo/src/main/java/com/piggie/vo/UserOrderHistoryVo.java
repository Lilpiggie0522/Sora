package com.piggie.vo;

import com.piggie.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: UserOrderHistoryVo
 * Package: com.piggie.vo
 * Description:
 *
 * @Author Piggie
 * @Create 18/02/2024 8:41 pm
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderHistoryVo implements Serializable {
    private Long id;

    //订单号
    private String number; // done

    //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
    private Integer status; // done

    //下单用户id
    private Long userId; // done

    //地址id
    private Long addressBookId; // done

    //下单时间
    private LocalDateTime orderTime; // done

    //结账时间
    private LocalDateTime checkoutTime;

    //支付方式 1微信，2支付宝
    private Integer payMethod; // done

    //支付状态 0未支付 1已支付 2退款
    private Integer payStatus; // done

    //实收金额
    private BigDecimal amount; // done

    //备注
    private String remark; // done

    //用户名
    private String userName; // done

    //手机号
    private String phone; // done

    //地址
    private String address; // done

    //收货人
    private String consignee; // done

    //订单取消原因
    private String cancelReason;

    //订单拒绝原因
    private String rejectionReason;

    //订单取消时间
    private LocalDateTime cancelTime;

    //预计送达时间
    private LocalDateTime estimatedDeliveryTime; // done

    //配送状态  1立即送出  0选择具体时间
    private Integer deliveryStatus; // done

    //送达时间
    private LocalDateTime deliveryTime;

    //打包费
    private int packAmount; // done

    //餐具数量
    private int tablewareNumber; // done

    //餐具数量状态  1按餐量提供  0选择具体数量
    private Integer tablewareStatus; // done

    // orderDetail list
    private List<OrderDetail> orderDetailList;
}
