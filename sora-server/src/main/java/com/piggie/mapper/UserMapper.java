package com.piggie.mapper;

import com.piggie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: UserMapper
 * Package: com.piggie.mapper
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 5:41 pm
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openId}")
    User getByOpenId(String openId);

    void insert(User user);

    @Select("select * from user where id=#{id}")
    User getByUserId(Long id);

}
