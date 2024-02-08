package com.piggie.controller.admin;

import com.piggie.dto.CategoryDTO;

import com.piggie.dto.CategoryPageQueryDTO;
import com.piggie.entity.Category;
import com.piggie.result.PageResult;
import com.piggie.result.Result;
import com.piggie.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: CategoryController
 * Package: com.piggie.controller.admin
 * Description:
 *
 * @Author Piggie
 * @Create 7/02/2024 12:15 am
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "Category Interface")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @PostMapping
    @ApiOperation("create category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("page query")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("update category")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("delete category")
    public Result deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @ApiOperation("suspend category")
    @PostMapping("/status/{status}")
    public Result suspendCategory(@RequestParam Long id, @PathVariable Integer status) {
        categoryService.suspendCategory(id, status);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
