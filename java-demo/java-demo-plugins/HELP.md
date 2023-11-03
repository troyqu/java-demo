# java-demo-plugins

项目主要展示通过动态加载插件来实现对于应用程序的扩展，项目按照以下方式运行。

* 在plugins-api中定义和声明接口规范
* 在*-plugins项目中实现各自插件厂商对于接口的实现
* 在主程序中通过依赖引入各个插件，从而完成对于接口的不同实现的调用

这样做的好处是，在我们程序运行上线之后，如果需要其他的扩展和实现，我们不需要去调整我们的主程序，只需要开发一个新的插件实现就可以。

项目结构

```shell
➜  java-demo git:(main) ✗ tree -d java-demo-plugins -L 1  
java-demo-plugins
├── acs-plugins
├── aws-plugins
├── build
├── plugins-api
└── src

```

主程序java-demo-plugins/src目录下有三个主程序实现方式，分别是：

```shell
➜  demo git:(main) ✗ pwd
/Users/qujianfei/gitProject/java-demo/java-demo/java-demo-plugins/src/main/java/com/troyqu/demo
➜  demo git:(main) ✗ tree -L 1
.
├── MainApplication.java
├── MainApplication2.java
└── MainApplicationSPI.java

0 directories, 3 files
➜  demo git:(main) ✗ 

```

分别对应三种动态加载插件的实现方式

* MainApplication.java：通过读取plugins.properties中配置的实现类来完成对于插件的动态加载，缺点就是后期如果扩展了新的插件，还需要更新plugins.properties文件。


* MainApplicationSPI.java：相当于MainApplication.java使用SPI机制实现的方式，优点是使用SPI机制，实现方便但是缺点也是需要收到META-INF/services元数据配置影响，如果扩展了新的插件，需要更新配置


* MainApplication2.java：在MainApplication的基础上，摒弃了对于配置文件的依赖，通过读取项目中依赖的插件jar命名规范，来动态加载插件jar中的实现，从而做到后期扩展新插件，不用修改任何代码和配置就能完成对于新插件的支持。前提就是对于插件的命名一定要满足规范。

```java
file.getName().endsWith("-plugins-1.0-SNAPSHOT.jar")
```

以上三种方式个有优缺点，可以结合实际情况来选择。前两个情况需要调整一些配置来适配新插件，但是改动也不多，第三种方式一点都不用改动，但是需要约定插件规范。
