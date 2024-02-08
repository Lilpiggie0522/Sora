package com.piggie.service;

import com.piggie.dto.CategoryDTO;
import com.piggie.dto.CategoryPageQueryDTO;
import com.piggie.entity.Category;
import com.piggie.result.PageResult;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 7/02/2024 12:27 am
 * @Version 1.0
 */
public interface CategoryService {
    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    void suspendCategory(Long id, Integer status);

    List<Category> list(Integer type);
}
