package com.piggie.mapper;

import com.piggie.annotation.AutoFill;
import com.piggie.entity.SetmealDish;
import com.piggie.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: ComboDishMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 9/02/2024 12:04 am
 * @Version 1.0
 */
@Mapper
public interface ComboDishMapper {
    /**
     * find combo id according to dish ids
     * @param dishIds
     * @return
     */
    List<Long> getComboIdsByDishIds(List<Long> dishIds);

    void saveBatch(List<SetmealDish> comboDishes);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getDishByComboId(Long id);

    @Delete("delete from setmeal_dish where setmeal_id=#{comboId}")
    void deleteByComboId(Long comboId);

    void deleteByComboIds(List<Long> ids);
}
