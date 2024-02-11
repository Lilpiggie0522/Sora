package com.piggie.mapper;

import com.piggie.annotation.AutoFill;
import com.piggie.entity.DishFlavor;
import com.piggie.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: DishFlavorMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 8:26 pm
 * @Version 1.0
 */
@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> flavors);

    /**
     * delete flavors according to related dish id
     * @param dishId
     */

    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    void deleteById(Long dishId);

    /**
     * delete related flavors by dish ids
     */
    void deleteByDishIds(List<Long> ids);

    List<DishFlavor> getFlavorsByDishId(Long id);

}
