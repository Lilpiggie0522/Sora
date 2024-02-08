package com.piggie.mapper;

import com.piggie.entity.DishFlavor;
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
}
