package com.piggie.service;

import com.piggie.dto.DishDTO;
import com.piggie.dto.DishPageQueryDTO;
import com.piggie.entity.Dish;
import com.piggie.result.PageResult;
import com.piggie.vo.DishVO;

import java.util.List;

/**
 * ClassName: DishService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 6:36 pm
 * @Version 1.0
 */
public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavors(Long id);

    void updateById(DishDTO dishDTO);

    List<Dish> getDishesByCategoryId(Long categoryId);
}
