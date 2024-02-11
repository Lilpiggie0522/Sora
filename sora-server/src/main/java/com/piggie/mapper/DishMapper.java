package com.piggie.mapper;

import com.github.pagehelper.Page;
import com.piggie.annotation.AutoFill;
import com.piggie.entity.Dish;
import com.piggie.enumeration.OperationType;
import com.piggie.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * create dish row
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);


    Page<DishVO> pageQuery(Dish dish);

    void deleteBatch(List<Long> ids);

    @Select("select * from dish where id=#{id}")
    Dish getDishById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateById(Dish dish);

    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> getDishesByCategoryId(Long categoryId);
}
