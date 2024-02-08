package com.piggie.service.impl;

import com.piggie.dto.DishDTO;
import com.piggie.entity.Dish;
import com.piggie.entity.DishFlavor;
import com.piggie.mapper.DishFlavorMapper;
import com.piggie.mapper.DishMapper;
import com.piggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: DishServiceImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 6:36 pm
 * @Version 1.0
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     * create new dish and flavor
     * @param dishDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWithFlavor(DishDTO dishDTO) {
        //  insert one row to dish table
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long id = dish.getId();
        //  insert multiple rows to flavor table
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(id));
        if (flavors != null && flavors.size() > 0) {
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
