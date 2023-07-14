package com.troyqu.javademo.javademoaop.staticproxydemo.service.impl;

import com.troyqu.javademo.javademoaop.staticproxydemo.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void add(String name) {
        System.out.println("添加用户" + name);
    }
}
