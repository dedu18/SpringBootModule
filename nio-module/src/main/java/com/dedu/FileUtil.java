package com.dedu;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件工具测试
 */
public class FileUtil {

    public static void main(String[] args) throws IOException {
        // 文件存放路径
        String filePath = "e://test.txt";
        // 创建文件流
        FileInputStream fis = createFileInputStream(filePath);
        // 传统IO
        readFileByIO(fis);
        // NIO
        readFileByNIO(fis);
    }

    /**
     * 根据文件路径创建文件流
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    private static FileInputStream createFileInputStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(new File(filePath));
    }

    /**
     * 使用NIO读取文件数据
     * @param fis
     * @throws IOException
     */
    private static void readFileByNIO(FileInputStream fis) throws IOException {
        // nio读取文件使用channel
        FileChannel fileChannel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fileChannel.read(buffer);
        // 转换为读模式
        buffer.flip();
        // 申请读取的数据大小
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes, 0, bytes.length);
        System.out.println(new String(bytes));
    }

    /**
     * 使用传统IO读取文件数据
     * @param fis
     * @throws IOException
     */
    private static void readFileByIO(FileInputStream fis) throws IOException {
        // 传统io读取文件使用stream
        byte[] buffer = new byte[1024];
        fis.read(buffer);
        System.out.println(new String(buffer));
        fis.close();
    }
}
