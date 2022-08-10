package com.troyqu.javademo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open(); // 创建NIO ServerSocketChannel
        serverSocket.socket().bind(new InetSocketAddress(9000));
        serverSocket.configureBlocking(false); // 设置ServerSocketChannel为非阻塞
        Selector selector = Selector.open(); // 打开Selector处理Channel，即创建epoll
        serverSocket.register(selector, SelectionKey.OP_ACCEPT); // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        System.out.println("服务启动成功");
        while (true) {
            selector.select(); // 阻塞等待需要处理的事件发生
            Set<SelectionKey> selectionKeys = selector.selectedKeys(); //获取selector中注册的全部事件的 SelectionKey 实例
            Iterator<SelectionKey> iterator = selectionKeys.iterator(); // 遍历SelectionKey对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) { // 如果是OP_ACCEPT事件，则进行连接获取和事件注册
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ); // 这里只注册了读事件，如果需要给客户端发送数据可以注册写事件
                    System.out.println("客户端连接成功");
                } else if (key.isReadable()) { // 如果是OP_READ事件，则进行读取和打印
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    // 如果有数据，把数据打印出来
                    if (len > 0) {
                        System.out.println("接收到消息：" + new String(byteBuffer.array()));
                    } else if (len == -1) {
                        System.out.println("客户端断开连接");
                        socketChannel.close(); // 如果客户端断开连接，关闭Socket
                    }
                }
                iterator.remove(); //从事件集合里删除本次处理的key，防止下次select重复处理
            }
        }
    }
}
