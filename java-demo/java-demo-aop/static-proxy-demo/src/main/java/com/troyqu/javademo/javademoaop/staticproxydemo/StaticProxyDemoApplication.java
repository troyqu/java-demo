package com.troyqu.javademo.javademoaop.staticproxydemo;

import com.troyqu.javademo.javademoaop.staticproxydemo.proxy.UserServiceProxy;
import com.troyqu.javademo.javademoaop.staticproxydemo.service.UserService;
import com.troyqu.javademo.javademoaop.staticproxydemo.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StaticProxyDemoApplication {

    public static void main(String[] args) {
//        SpringApplication.run(StaticProxyDemoApplication.class, args);
        UserService userService = new UserServiceImpl();
        UserService userProxy = new UserServiceProxy(userService);
        userProxy.add("troyqu");
    }

}
