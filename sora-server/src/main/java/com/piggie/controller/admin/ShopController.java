package com.piggie.controller.admin;

import com.piggie.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ShopController
 * Package: com.piggie.controller.admin
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 10:19 am
 * @Version 1.0
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "shop interface")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("set shop status")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        log.info("setting shop status to {}", status == 1 ? "open":"close");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("get current shop status")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("fetched shop status is {}", status == 1 ? "open": "close");
        return Result.success(status);
    }
}
