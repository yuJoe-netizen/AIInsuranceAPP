package com.example.api.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.api.user.vo.UserLoginVO;
import com.example.common.RespVO;
import com.example.db.entity.User;
import com.example.db.mapper.UserMapper;
import com.example.db.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("login")
    public RespVO<Integer> login(@RequestBody UserLoginVO userLoginVO){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,userLoginVO.getPhone())
                .eq(User::getPasswd,userLoginVO.getPassword())
                .last("limit 1");

        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new RuntimeException("用户名或密码错误");
        }
        return RespVO.success(user.getId());
    }
}

