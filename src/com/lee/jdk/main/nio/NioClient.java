package com.lee.jdk.main.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class NioClient {

    private SocketChannel channel;

    private static final int buffer_size = 1024;

    private static final CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    private static final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public NioClient cennetServer(Integer port) throws Exception {
        if (port == null) return null;
        channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
        return this;
    }

    public void sendData(String message) throws Exception {
        if (channel == null) throw new RuntimeException("channel has not been initialed");
        int size = buffer_size;
        if (message != null) {
            size = message.length();
        }
        CharBuffer charBuffer = CharBuffer.allocate(size);
        charBuffer.put(message.toCharArray());
        charBuffer.flip();
        ByteBuffer buffer = encoder.encode(charBuffer);
        channel.write(buffer);
        buffer.clear();
        charBuffer.clear();

        CharBuffer charBufferBack;
        ByteBuffer bufferBack = ByteBuffer.allocate(16);
        int len = 0;
        for (;(len = channel.read(bufferBack)) > 0;) {
            bufferBack.flip();
            charBufferBack = decoder.decode(bufferBack);
            System.out.println(charBufferBack.toString());
        }
    }

    public void shutdownChannel() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
