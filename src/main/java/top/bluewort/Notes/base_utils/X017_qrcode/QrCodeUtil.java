package top.bluewort.Notes.base_utils.X017_qrcode;

import com.swetake.util.Qrcode;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 二维码生成工具类
 */
public class QrCodeUtil {
    public static void main(String[] args) {
        //text2Local("http://p.tb.cn/5BTlVB","D://love.png");
        MultipartFile multipartFile = text2MultiFile("http://p.tb.cn/5BTlVB");
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println(multipartFile.getSize());
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getName());
    }
    /**
     * 二维码保存到本地
     * @param qrData
     * @param path
     * @return
     */
    private static boolean text2Local(String qrData, String path){
        try {
            Qrcode qrcode = new Qrcode();
            //纠错等级（分为L、M、H三个等级）
            qrcode.setQrcodeErrorCorrect('M');
            //N代表数字，A代表a-Z，B代表其它字符
            qrcode.setQrcodeEncodeMode('B');
            //版本
            qrcode.setQrcodeVersion(7);

            //设置一下二维码的像素
            int width = 67 + 12 * (7 - 1);
            int height = 67 + 12 * (7 - 1);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //绘图
            Graphics2D gs = bufferedImage.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.setColor(Color.BLACK);
            //清除下画板内容
            gs.clearRect(0, 0, width, height);

            //设置下偏移量,如果不加偏移量，有时会导致出错。
            int pixoff = 2;

            byte[] d = qrData.getBytes("utf-8");
            if (d.length > 0 && d.length < 120) {
                boolean[][] s = qrcode.calQrcode(d);
                for (int i = 0; i < s.length; i++) {
                    for (int j = 0; j < s.length; j++) {
                        if (s[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            }
            gs.dispose();
            bufferedImage.flush();
            ImageIO.write(bufferedImage, "png", new File(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 二维码转输出流
     */
    private static ByteArrayOutputStream text2Stream(String qrData){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Qrcode qrcode = new Qrcode();
            //纠错等级（分为L、M、H三个等级）
            qrcode.setQrcodeErrorCorrect('M');
            //N代表数字，A代表a-Z，B代表其它字符
            qrcode.setQrcodeEncodeMode('B');
            //版本
            qrcode.setQrcodeVersion(7);

            //设置一下二维码的像素
            int width = 67 + 12 * (7 - 1);
            int height = 67 + 12 * (7 - 1);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //绘图
            Graphics2D gs = bufferedImage.createGraphics();
            gs.setBackground(Color.WHITE);
            gs.setColor(Color.BLACK);
            //清除下画板内容
            gs.clearRect(0, 0, width, height);

            //设置下偏移量,如果不加偏移量，有时会导致出错。
            int pixoff = 2;

            byte[] d = qrData.getBytes("utf-8");
            if (d.length > 0 && d.length < 120) {
                boolean[][] s = qrcode.calQrcode(d);
                for (int i = 0; i < s.length; i++) {
                    for (int j = 0; j < s.length; j++) {
                        if (s[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            }
            gs.dispose();
            bufferedImage.flush();
            ImageIO.write(bufferedImage, "png", out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 二维码转文件
     */
    public static MultipartFile text2MultiFile(String qrData){
        try {
            //1. 文件名称
            String fileName = System.currentTimeMillis()+""+((int)(Math.random()*9000)+1000)+".png";
            byte[] b = new byte[1024 * 4];
            int n = 0;
            String binary = null;
            MultipartFile multipartFile = null;
            ByteArrayOutputStream out = text2Stream(qrData);
            InputStream in = new ByteArrayInputStream(out.toByteArray());
            int length;
            while ((length = in.read(b)) > 0) {
                out.write(b, 0, n);
            }
            //转成字符数组
            byte[] bytes = out.toByteArray();
            //创建文件类型
            multipartFile = new MockMultipartFile(fileName, fileName, "image/png", bytes);
            //调用工具类上传阿里云服务器
            in.close();
            return multipartFile;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
