package com.troyqu.javademo.javademoaop.dynamicproxydemo.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理要求被代理的对象必须实现1个或多个接口，如果没有实现接口就无法使用jdk的动态代理
 */
public class LogProxyFactory {

    private Object target;

    public LogProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("操作开始记录日志");
                method.invoke(target, args);
                System.out.println("操作完成记录日志，退出程序");
                return null;
            }
        });
    }

}
