package com.troyqu.demo;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 完全通过反射机制实现动态加载插件，在这里的实现我们不依赖于外部的任何plugins.properties文件，后面如果扩展了其他的*-plugins项目，只需要添加依赖到项目中，就可以直接使用
 */
public class MainApplication2 {
    public static void main(String[] args) {

        String[] tasks = new String[]{"ecs", "vm", "oss", "s3", "cloudwatch"};
        List<Message> plugins = loadPlugins();

        for (Message plugin : plugins) {
            Arrays.stream(tasks).forEach(t -> System.out.println(plugin.send(t)));
        }
    }

    private static List<Message> loadPlugins() {
        List<Message> plugins = new ArrayList<>();

        try {
            // 获取当前类加载器
            ClassLoader classLoader = MainApplication2.class.getClassLoader();

            // 使用URLClassLoader以便加载插件项目中的类
            URLClassLoader urlClassLoader = new URLClassLoader(((URLClassLoader) classLoader).getURLs(), classLoader);


            // 扫描当前类路径下的所有类
            URL[] urls = ((URLClassLoader) urlClassLoader).getURLs();
            for (URL url : urls) {
                File file = new File(url.toURI());
                if (file.getName().endsWith("-plugins-1.0-SNAPSHOT.jar")) {

                    // 获取Jar文件路径
                    String jarFilePath = url.getFile();
                    JarFile jarFile = new JarFile(jarFilePath);

                    // 遍历Jar文件中的所有类
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace("/", ".").replace(".class", "");

                            // 加载类并检查是否实现了插件接口
                            Class<?> pluginClass = urlClassLoader.loadClass(className);
                            if (Message.class.isAssignableFrom(pluginClass)) {
                                Message plugin = (Message) pluginClass.getDeclaredConstructor().newInstance();
                                plugins.add(plugin);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugins;
    }
}
