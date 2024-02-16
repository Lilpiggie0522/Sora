package com.piggie.mapper;

import com.github.pagehelper.Page;
import com.piggie.annotation.AutoFill;
import com.piggie.dto.SetmealDTO;
import com.piggie.dto.SetmealPageQueryDTO;
import com.piggie.entity.Setmeal;
import com.piggie.enumeration.OperationType;
import com.piggie.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ComboMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal combo);

    Page<Setmeal> pageQuery(SetmealPageQueryDTO comboPageDto);

    SetmealVO getComboById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal combo);

    @Update("update setmeal set status=#{status} where id=#{id}")
    @AutoFill(value = OperationType.UPDATE)
    void setComboStatus(Setmeal combo);

    void deleteByIds(List<Long> ids);

    @Select("select * from setmeal where category_id=#{categoryId}")
    List<Setmeal> getComboByCategoryId(Long categoryId);
}
