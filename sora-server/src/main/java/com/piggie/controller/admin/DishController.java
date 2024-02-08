package com.piggie.controller.admin;

import com.piggie.dto.DishDTO;
import com.piggie.result.Result;
import com.piggie.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: DishController
 * Package: com.piggie.controller.admin
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 6:31 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "dish interface")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("create new dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("creating new dish {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

}
