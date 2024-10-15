package com.example.db.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.db.entity.User;
import com.example.db.mapper.UserMapper;
import com.example.db.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
}
