package com.piggie.controller.user;

import com.piggie.entity.Setmeal;
import com.piggie.entity.SetmealDish;
import com.piggie.result.Result;
import com.piggie.service.UserService;
import com.piggie.vo.DishItemVO;
import com.piggie.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UserComboController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 8:17 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "user combo interface")
public class UserComboController {
    @Autowired
    UserService userService;
    @GetMapping("/list")
    @Cacheable(cacheNames = "comboCache", key = "#categoryId")
    public Result<List<Setmeal>> getCombosByCategoryId(@RequestParam Long categoryId) {
        log.info("fetching combo using categoryId: {}", categoryId);
        List<Setmeal> combos = userService.getCombosByCategoryId(categoryId);
        return Result.success(combos);
    }

    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> getDishesByComboId(@PathVariable Long id) {
        log.info("fetching dishes using combo id {}", id);
        List<DishItemVO> dishes = userService.getDishesByComboId(id);
        return Result.success(dishes);
    }
}
