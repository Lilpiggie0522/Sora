package com.piggie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggie.constant.JwtClaimsConstant;
import com.piggie.constant.MessageConstant;
import com.piggie.constant.WechatAuthConstant;
import com.piggie.dto.UserLoginDTO;
import com.piggie.entity.*;
import com.piggie.exception.LoginFailedException;
import com.piggie.mapper.*;
import com.piggie.properties.JwtProperties;
import com.piggie.properties.WeChatProperties;
import com.piggie.service.UserService;
import com.piggie.utils.HttpClientUtil;
import com.piggie.vo.DishItemVO;
import com.piggie.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 3:46 pm
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ComboMapper comboMapper;

    @Autowired
    ComboDishMapper comboDishMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;
    public static final String WECHAT_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * wechat login using non-password approach sending request from wechat
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wechatLogin(UserLoginDTO userLoginDTO) {
        //  use wechat interface to get openId of this user
        //  see method at the bottom called getOpenid
        String openid = getOpenId(userLoginDTO.getCode());

        //  check whether open id is null
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //  check if current user is new user, search from our user table
        User user = userMapper.getByOpenId(openid);

        //  if new user, then auto-register and save it to user table
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenId(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put(WechatAuthConstant.appid, weChatProperties.getAppid());
        map.put(WechatAuthConstant.secret, weChatProperties.getSecret());
        map.put(WechatAuthConstant.code, code);
        map.put(WechatAuthConstant.grant_type, WechatAuthConstant.auth_code);
        String response = HttpClientUtil.doGet(WECHAT_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(response);
        return jsonObject.getString("openid");
    }

    @Override
    public List<Category> getCategoriesByType(Integer type) {
        List<Category> categories = categoryMapper.getCategoriesByType(type);
        return categories;
    }

    @Override
    public List<Setmeal> getCombosByCategoryId(Long categoryId) {
        List<Setmeal> combos = comboMapper.getComboByCategoryId(categoryId);
        return combos;
    }

    @Override
    public List<DishItemVO> getDishesByComboId(Long comboId) {
        List<DishItemVO> dishes = comboDishMapper.getDishesByComboId(comboId);
        return dishes;
    }

    @Override
    public List<DishVO> getDishesByCategoryId(Long categoryId) {
        List<Dish> dishes = dishMapper.getDishesByCategoryId(categoryId);
        ArrayList<DishVO> dishVOS = new ArrayList<>();
        for (Dish dish : dishes) {
            DishVO dishVO = new DishVO();
            List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(dish.getId());
            dishVO.setFlavors(flavors);
            BeanUtils.copyProperties(dish, dishVO);
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }
}
