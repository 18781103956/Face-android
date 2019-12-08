import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class testCilent {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket cs=null;
        // 字节读写
        InputStream read_byte=null;
        OutputStream writer_byte=null;
        // cs 初始化
        // ipconfig --- WIN10 查看本机IP
        // ifconfig --- Linux 查看本机IP
        int port=37190;//wise-ant.picp.io 37190
        InetAddress serverIP=InetAddress.getByName("wise-ant.picp.io");
        cs=new Socket(serverIP,port);

        read_byte=cs.getInputStream();
        writer_byte=cs.getOutputStream();


        // 接收图片
        String path1="C:\\Users\\m1309\\Pictures\\图片\\1.jpg";
        String path2="C:\\Users\\m1309\\Desktop\\temp.jpg";
        FileOutputStream image_byte=new FileOutputStream(path2);
        byte[] buff=new byte[600000];
        int len=0;
        while((len=read_byte.read(buff))!=-1){
            image_byte.write(buff,0,len);
        }
        Thread.sleep(2000);
        image_byte.close();

        // 发送文本信息
        String str="上传成功";
        writer_byte.write(str.getBytes());
        System.out.println(str);

        // 关闭所有相关调用
        read_byte.close();
        writer_byte.close();
        cs.close();
    }
}
