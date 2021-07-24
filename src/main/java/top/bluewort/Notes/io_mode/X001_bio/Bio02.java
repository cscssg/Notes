package top.bluewort.Notes.io_mode.X001_bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 伪异步 线程池bio
 *
 */
public class Bio02 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket so = null;
        try {
            so = new ServerSocket(10000);
            System.out.println("socker servver 启动成功。。。。。。。");
            while (true){
                Socket socket = so.accept();
                System.out.println("socket client 链接成功");
                pool.execute(new Runnable() {
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
                });
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
