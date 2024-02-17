package com.piggie.service;

import com.piggie.dto.UserLoginDTO;
import com.piggie.entity.Category;
import com.piggie.entity.Setmeal;
import com.piggie.entity.User;
import com.piggie.vo.DishItemVO;
import com.piggie.vo.DishVO;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 3:43 pm
 * @Version 1.0
 */
public interface UserService {

    User wechatLogin(UserLoginDTO userLoginDTO);

    List<Category> getCategoriesByType(Integer type);

    List<Setmeal> getCombosByCategoryId(Long categoryId);

    List<DishItemVO> getDishesByComboId(Long comboId);

    List<DishVO> getDishesByCategoryId(Long categoryId);
}
