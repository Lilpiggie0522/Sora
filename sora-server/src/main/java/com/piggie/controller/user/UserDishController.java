package com.piggie.controller.user;

import com.piggie.result.Result;
import com.piggie.service.UserService;
import com.piggie.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: UserDishController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 9:00 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/dish")
@Api(tags = "user dish interface")
@Slf4j

public class UserDishController {
    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/list")
    public Result<List<DishVO>> getDishesByCategoryId(@RequestParam Long categoryId) {
        log.info("fetching dishes by categoryId");
        //  search dishes in redis
        
        //  if in return right away

        //  if not, search database
        List<DishVO> dishesWithFlavors = userService.getDishesByCategoryId(categoryId);
        return Result.success(dishesWithFlavors);
    }
}
