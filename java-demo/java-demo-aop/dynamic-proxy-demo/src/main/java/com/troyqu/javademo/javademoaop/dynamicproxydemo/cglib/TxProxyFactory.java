package com.troyqu.javademo.javademoaop.dynamicproxydemo.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGlib实现动态代理，即使代理对象没有实现任何接口类，也可以生成代理
 */
public class TxProxyFactory implements MethodInterceptor {

    private Object target;

    public TxProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开启事务");
        Object returnvalue = method.invoke(target, objects);
        System.out.println("关闭事务");
        return null;
    }
}
