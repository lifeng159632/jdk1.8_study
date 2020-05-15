package com.lee.jdk.main.nio;

public class Bootstrap {

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        try {
            nioServer.initSocket(10000).listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
