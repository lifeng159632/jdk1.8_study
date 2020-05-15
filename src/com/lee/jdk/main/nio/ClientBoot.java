package com.lee.jdk.main.nio;


import java.util.Scanner;

public class ClientBoot {

    public static void main(String[] args) {
        try {
            NioClient client = new NioClient();
            client.cennetServer(10000);
            Scanner scanner = new Scanner(System.in);
            for (;scanner.hasNext();) {
                client.sendData(scanner.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
