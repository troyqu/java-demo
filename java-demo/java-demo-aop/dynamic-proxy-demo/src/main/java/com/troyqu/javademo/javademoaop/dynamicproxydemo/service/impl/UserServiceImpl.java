package com.troyqu.javademo.javademoaop.dynamicproxydemo.service.impl;

import com.troyqu.javademo.javademoaop.dynamicproxydemo.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    static Map<Integer, String> users = new HashMap<Integer, String>();

    static {
        users.put(1, "troyqu1");
        users.put(2, "troyqu2");
        users.put(3, "troyqu3");
    }

    @Override
    public void add(String name) {
        users.put(users.size() + 1, name);
        System.out.println("添加用户" + name);
    }

    @Override
    public String query(int id) {
        Map.Entry<Integer, String> user = users.entrySet().stream().filter(entry -> entry.getKey().equals(id)).findFirst().get();
        System.out.println("查找Id=" + id + "的用户，用户名" + user.getValue());
        return user.getValue();
    }
}
