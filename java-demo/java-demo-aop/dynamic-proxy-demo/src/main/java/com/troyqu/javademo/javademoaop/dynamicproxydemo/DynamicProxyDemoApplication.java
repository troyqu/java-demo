package com.troyqu.javademo.javademoaop.dynamicproxydemo;

import com.troyqu.javademo.javademoaop.dynamicproxydemo.jdkproxy.LogProxyFactory;
import com.troyqu.javademo.javademoaop.dynamicproxydemo.service.UserService;
import com.troyqu.javademo.javademoaop.dynamicproxydemo.service.UserStoryService;
import com.troyqu.javademo.javademoaop.dynamicproxydemo.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamicProxyDemoApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(DynamicProxyDemoApplication.class, args);
//    }

    //    JDK动态代理示例
    public static void main(String[] args) {
        UserService target = new UserServiceImpl();
        System.out.println("target目标对象信息" + target.getClass());
        UserService proxy = (UserService) new LogProxyFactory(target).getProxyInstance();
        System.out.println("proxy目标对象信息" + proxy.getClass());
        proxy.add("qujf");
        proxy.query(4);
        UserStoryService userStoryService = new UserStoryService();
        System.out.println("target目标对象信息" + userStoryService.getClass());
//        以下代码执行会报错，因为JDK动态代理的target必须至少实现一个接口，UserStoryService没有实现任何接口无法正常代理
        UserStoryService userStoryServiceProxy = (UserStoryService) new LogProxyFactory(userStoryService).getProxyInstance();
        System.out.println("proxy目标对象信息" + userStoryServiceProxy.getClass());
        userStoryServiceProxy.show();
    }

//    //CGlib动态代理示例
//    public static void main(String[] args) {
//        UserService target = new UserServiceImpl();
//        System.out.println("target目标对象信息"+target.getClass());
//        UserService proxy = (UserService) new TxProxyFactory(target).getProxyInstance();
//        System.out.println("proxy目标对象信息"+proxy.getClass());
//        proxy.add("qujf");
//        proxy.query(4);
//
//        //使用CGlib对于非接口类生成代理
//        UserStoryService userStoryService = new UserStoryService();
//        System.out.println("target目标对象信息"+userStoryService.getClass());
//        UserStoryService userStoryServiceProxy = (UserStoryService) new TxProxyFactory(userStoryService).getProxyInstance();
//        System.out.println("proxy目标对象信息"+userStoryServiceProxy.getClass());
//        userStoryServiceProxy.show();
//    }

}
