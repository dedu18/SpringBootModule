package com.dedu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class SocketUtil {

    public static void main(String[] args) throws IOException {
        // 创建服务端Socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 绑定地址
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 创建Selector
        Selector selector = Selector.open();
        // 定义感兴趣的Key
        int key = SelectionKey.OP_ACCEPT | SelectionKey.OP_CONNECT;
        // 将Channel注册到Selector
        serverSocketChannel.register(selector, key);
        // 有事件发生
        while (selector.select() > 0) {
            // 依次处理事件
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                // 根据事件类型处理
                if (selectionKey.isAcceptable()) {
                    // 新连接创建
                }
            }
        }
    }
}
