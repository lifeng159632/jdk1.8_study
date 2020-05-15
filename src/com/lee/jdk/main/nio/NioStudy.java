package com.lee.jdk.main.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.StandardOpenOption;

public class NioStudy {

    public static void readFile(String fileName) throws Exception {
        if (fileName == null || "".equals(fileName.trim())) {
            throw new RuntimeException("文件路径不能为空");
        }
        File file = new File(fileName);
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        if (file.exists()) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            CharBuffer charsetbuffer = CharBuffer.allocate(1024);

//            ReadableByteChannel channel = Channels.newChannel(new FileInputStream(file));
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            if (channel.isOpen()) {
                for (;channel.read(buffer) > 0;) {
                    buffer.flip();
                    decoder.decode(buffer, charsetbuffer, true);
                    charsetbuffer.flip();
                    for (;charsetbuffer.remaining() > 0;) {
                        System.out.print(charsetbuffer.get());
                    }

                }
//                channel.read(buffer);
                buffer.clear();
                fis.close();
                channel.close();
            }
        }
    }
    public static void readAndWriteFile(String fileName, String outFileName) throws Exception {
        if (fileName == null || "".equals(fileName.trim())) {
            throw new RuntimeException("文件路径不能为空");
        }
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        FileChannel channel = new RandomAccessFile(fileName, "rw").getChannel();
        FileChannel outChannel = new RandomAccessFile(outFileName, "rw").getChannel();
        if (channel.isOpen()) {
            for (;channel.read(buffer) > 0;) {
                buffer.flip();
                outChannel.write(buffer);
            }
            buffer.clear();
            channel.close();
        }
    }

    public static void main(String[] args) {
        try {
//            readFile("/Users/li/Downloads/1");
            readAndWriteFile("/Users/li/Downloads/1", "/Users/li/Downloads/2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
