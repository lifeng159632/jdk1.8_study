package com.lee.jdk.main.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class NioServer {

    public static final int default_port = 12345;

    public static final int max_connect = Integer.MAX_VALUE;

    private Selector selector;

    private ServerSocketChannel socketChannel;

    private static final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();


    public NioServer initSocket(Integer port) throws Exception {
        SocketAddress address = new InetSocketAddress(port == null ? default_port : port);
        socketChannel = ServerSocketChannel.open().bind(address);
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return this;
    }

    public void listen() throws Exception {
        for (;;) {
            selector.select();
            Iterator<?> iterator = selector.selectedKeys().iterator();
            for (;iterator.hasNext();) {
                SelectionKey key = (SelectionKey) iterator.next();
                handle(key);
                iterator.remove();
            }
        }
    }

    public void handle(SelectionKey key) throws Exception {
        if (key.isAcceptable()) handleAccept(key);
        else if (key.isReadable()) handleRead(key);
    }

    public void handleAccept(SelectionKey key) throws Exception {
        SocketChannel channel = socketChannel.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }
    public void handleRead(SelectionKey key) throws Exception {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        CharBuffer charBuffer;
        ByteBuffer outbuffer;
        int len;
        for (;(len = channel.read(buffer)) > 0;) {
            buffer.flip();
            buffer.limit(len);
            charBuffer = decoder.decode(buffer);
            System.out.println(charBuffer.toString());
            outbuffer = ByteBuffer.wrap("已发送".getBytes());
            channel.write(outbuffer);
        }
    }

    public void shutdownServer() {
        try {
            selector.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
