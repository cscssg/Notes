package top.bluewort.Notes.io_mode.X002_nio;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道问题
 */
public class Nio01 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\1.txt");
        FileChannel inChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\2.txt");
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(2048);

        while (true){
            allocate.clear();
            int read = inChannel.read(allocate);
            if(read ==-1){
                break;
            }
            //指针拨动到起始位置
            allocate.flip();
            outChannel.write(allocate);
        }
    }
}
