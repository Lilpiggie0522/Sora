package com.piggie.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piggie.annotation.AutoFill;
import com.piggie.constant.MessageConstant;
import com.piggie.constant.StatusConstant;
import com.piggie.context.BaseContext;
import com.piggie.enumeration.OperationType;
import com.piggie.mapper.ComboMapper;
import com.piggie.mapper.DishMapper;
import com.piggie.dto.CategoryDTO;
import com.piggie.dto.CategoryPageQueryDTO;
import com.piggie.entity.Category;
import com.piggie.exception.DeletionNotAllowedException;
import com.piggie.mapper.CategoryMapper;
import com.piggie.result.PageResult;
import com.piggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: CategoryServiceImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 7/02/2024 12:27 am
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    ComboMapper comboMapper;
    @Override
    public void save(CategoryDTO categoryDTO) {
    /*    category.setStatus(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());*/

        Category category = Category.builder()
                .status(StatusConstant.DISABLE)
                .build();
        BeanUtils.copyProperties(categoryDTO, category);
        log.info("category is {}", category);
        categoryMapper.save(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        List<Category> result = page.getResult();
        long total = page.getTotal();
        return new PageResult(total, result);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Integer count = dishMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count = comboMapper.countByCategoryId(id);
        if (count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_COMBO);
        }
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void suspendCategory(Long id, Integer status) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.suspendCategory(category);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
