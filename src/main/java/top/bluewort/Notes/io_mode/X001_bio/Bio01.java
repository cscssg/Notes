package top.bluewort.Notes.io_mode.X001_bio;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO
 *
 * 伪异步的 io实现
 */
public class Bio01 {
    public static void main(String[] args){
        ServerSocket so = null;
        try {
            so = new ServerSocket(10000);
            System.out.println("socker servver 启动成功。。。。。。。");
            while (true){
                Socket socket = so.accept();
                System.out.println("socket client 链接成功");
                new Thread(){
                    @Override
                    public void run() {
                        BufferedReader in = null;
                        PrintWriter out = null;
                        try {
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String str;
                            while ((str=in.readLine())!=null){
                                System.out.println("i am read data::"+str);
                                out.print("the data is ok");
                                out.flush();
                            }
                            System.out.println("msg resend success");
                        }catch(IOException et){
                            et.printStackTrace();
                        }finally {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out.close();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                so.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
