package com.troyqu.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApplication {

    /**
     * 1.根据引入的acs和aws的插件来处理tasks任务，acs和aws插件只处理各自插件支持的任务
     * 2.如果后期扩展其他厂商，主程序不用调整，只需要在实现其他厂商插件, 然后引入程序依赖即可
     * 3.依赖于plugins.properties的配置，如果扩展其他plugins需要在里面配置新的实现类
     *
     * @param args
     */
    public static void main(String[] args) {

        String[] tasks = new String[]{"ecs", "vm", "oss", "s3", "cloudwatch"};

        List<Class<?>> pluginClasses = loadPluginClasses();
        for (Class<?> pluginClass : pluginClasses) {
            try {
                if (Message.class.isAssignableFrom(pluginClass)) {
                    Message msg = (Message) pluginClass.getDeclaredConstructor().newInstance();

                    Arrays.stream(tasks).forEach(t -> System.out.println(msg.send(t)));
                }

//                if (Task.class.isAssignableFrom(pluginClass)) {
//                    Task task = (Task) pluginClass.getDeclaredConstructor().newInstance();
//                    Arrays.stream(tasks).forEach(t -> System.out.println(task.handler(t)));
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static List<Class<?>> loadPluginClasses() {
        List<Class<?>> pluginClasses = new ArrayList<>();

        try (InputStream inputStream = MainApplication.class.getResourceAsStream("/plugins.properties");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Class<?> pluginClass = Class.forName(line);
                pluginClasses.add(pluginClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pluginClasses;
    }
}
