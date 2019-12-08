import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class testServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=null;
        Socket cs=null;

        // 字节读写
        InputStream read_byte=null;
        OutputStream writer_byte=null;

        // 端口号
        int port=5000;

        // ss,cs 初始化
        ss=new ServerSocket(port);
        System.out.println("服务器已开启，等待连接...");
        cs=ss.accept();
        System.out.println("检测到客户端，准备数据接收...");

        // 读写初始化
        read_byte=cs.getInputStream();
        writer_byte=cs.getOutputStream();

        // 发送一张图片
        String path1="C:\\Users\\m1309\\Pictures\\图片\\1.jpg";
        String path2="C:\\Users\\m1309\\Desktop\\temp.jpg";

        File image_file=new File(path1);
        if(!image_file.exists()){
            image_file.createNewFile();
        }
        byte[] buff=new byte[600000];
        int len=0;
        FileOutputStream image_byte=new FileOutputStream(image_file);
        while((len=read_byte.read(buff))!=-1){
            image_byte.write(buff,0,len);
        }
        image_byte.flush();

        cs.shutdownOutput();
        image_byte.close();

        // 收取文本信息
        len=0;
        len=read_byte.read(buff);
        String str=new String(buff,0,len);
        System.out.println(str);






        // 关闭所有相关调用
        read_byte.close();
        writer_byte.close();
        cs.close();
        ss.close();
    }
}
