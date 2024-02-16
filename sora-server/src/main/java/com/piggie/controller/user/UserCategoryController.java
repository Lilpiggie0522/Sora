package com.piggie.controller.user;

import com.piggie.entity.Category;
import com.piggie.result.Result;
import com.piggie.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: UserCategoryController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 7:57 pm
 * @Version 1.0
 */
@RestController
@Api(tags = "user category interface")
@Slf4j
@RequestMapping("user/category")
public class UserCategoryController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result<List<Category>> getCategoriesByType(@RequestParam(name = "type", required = false) Integer type) {
        log.info("fetching categories by type {}", type);
        List<Category> categories = userService.getCategoriesByType(type);
        return Result.success(categories);
    }

}
