package com.piggie.controller.admin;

import com.github.pagehelper.Page;
import com.piggie.dto.DishDTO;
import com.piggie.dto.DishPageQueryDTO;
import com.piggie.entity.Dish;
import com.piggie.result.PageResult;
import com.piggie.result.Result;
import com.piggie.service.DishService;
import com.piggie.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/page")
    @ApiOperation("dish page query")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("dish page queries {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("delete dishes by batch/single")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("delete dishes by batch {}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("search dish according to id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("searching dish according to id");
        DishVO dishVO = dishService.getByIdWithFlavors(id);
        return Result.success(dishVO);
    }

    /**
     * get dishes by category id
     * @param categoryId
     * @return list of dishes
     */
    @GetMapping("/list")
    @ApiOperation("get dishes by category id")
    public Result<List<Dish>> getDishesByCategoryId(@RequestParam("categoryId") Long categoryId) {
        log.info("cateogryId is {}", categoryId);
        List<Dish> list = dishService.getDishesByCategoryId(categoryId);
        return Result.success(list);
    }

    @PutMapping
    @ApiOperation("update dish by id")
    public Result<Object> updateDish(@RequestBody DishDTO dishDTO) {
        log.info("dishDto is {}", dishDTO);
        dishService.updateById(dishDTO);
        return Result.success();
    }
}
