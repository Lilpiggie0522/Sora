package com.piggie.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piggie.constant.MessageConstant;
import com.piggie.constant.StatusConstant;
import com.piggie.dto.DishDTO;
import com.piggie.dto.DishPageQueryDTO;
import com.piggie.entity.Dish;
import com.piggie.entity.DishFlavor;
import com.piggie.exception.DeletionNotAllowedException;
import com.piggie.mapper.CategoryMapper;
import com.piggie.mapper.ComboDishMapper;
import com.piggie.mapper.DishFlavorMapper;
import com.piggie.mapper.DishMapper;
import com.piggie.result.PageResult;
import com.piggie.service.DishService;
import com.piggie.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ComboDishMapper comboDishMapper;
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

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO, dish);
        Page<DishVO> page = dishMapper.pageQuery(dish);
        List<DishVO> result = page.getResult();
        long total = page.getTotal();
        return new PageResult(total, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getDishById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        List<Long> comboIds = comboDishMapper.getComboIdsByDishIds(ids);
        if (comboIds != null && comboIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteBatch(ids);
        /*for (Long id : ids) {
            dishFlavorMapper.deleteById(id);
        }*/


        dishFlavorMapper.deleteByDishIds(ids);
    }

    @Override
    public DishVO getByIdWithFlavors(Long id) {
        Dish dish = dishMapper.getDishById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public void updateById(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateById(dish);
        dishFlavorMapper.deleteById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public List<Dish> getDishesByCategoryId(Long categoryId) {
        List<Dish> list = dishMapper.getDishesByCategoryId(categoryId);
        return list;
    }
}
