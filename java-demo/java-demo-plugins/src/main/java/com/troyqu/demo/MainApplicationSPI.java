package com.troyqu.demo;

import java.util.Arrays;
import java.util.ServiceLoader;

/**
 * 通过java spi机制来动态加载插件信息，但是依赖于META-INF/services下的元数据配置
 */
public class MainApplicationSPI {
    public static void main(String[] args) {
        String[] tasks = new String[]{"ecs", "vm", "oss", "s3", "cloudwatch"};
        ServiceLoader<Message> plugins = ServiceLoader.load(Message.class);
        System.out.println(plugins);
        for (Message plugin : plugins) {
            System.out.println("Plugin: " + plugin.getClass().getName());
            Arrays.stream(tasks).forEach(t -> System.out.println(plugin.send(t)));
        }
    }

}
