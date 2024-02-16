package com.piggie.mapper;

import com.github.pagehelper.Page;
import com.piggie.annotation.AutoFill;
import com.piggie.dto.CategoryPageQueryDTO;
import com.piggie.entity.Category;
import com.piggie.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ClassName: CategoryMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 7/02/2024 12:20 am
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper {

    /*@Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values" +
            "(#{type}," +
            "#{name}," +
            "#{sort}," +
            "#{status}," +
            "#{createTime}," +
            "#{updateTime}," +
            "#{createUser}," +
            "#{updateUser}" + ")")*/
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);

    @Delete("delete from category where id=#{id}")
    void deleteCategory(Long id);

    @AutoFill(value = OperationType.UPDATE)
    @Update("update category set status=#{status},update_time=#{updateTime},update_user=#{updateUser} where id=#{id}")
    void suspendCategory(Category category);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);

    String getCategoryById(Long id);

    List<Category> getCategoriesByType(Integer type);
}
