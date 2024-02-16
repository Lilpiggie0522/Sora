package com.piggie.controller.user;

import com.piggie.constant.JwtClaimsConstant;
import com.piggie.dto.UserLoginDTO;
import com.piggie.entity.User;
import com.piggie.properties.JwtProperties;
import com.piggie.result.Result;
import com.piggie.service.UserService;
import com.piggie.utils.JwtUtil;
import com.piggie.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * ClassName: UserController
 * Package: com.piggie.controller.user
 * Description:
 *
 * @Author Piggie
 * @Create 15/02/2024 3:41 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "wechat user interface")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("wechat user login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("wechat user login {}", userLoginDTO.getCode());
        //  logs user in
        User user = userService.wechatLogin(userLoginDTO);
        //  generate jwt token
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .openid(user.getOpenid())
                .build();
        return Result.success(userLoginVO);
    }


}
