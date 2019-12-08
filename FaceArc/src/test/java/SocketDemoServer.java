import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketDemoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=null;
        Socket cs=null;

        // 文本读写
        BufferedReader read_file=null;
        PrintWriter writer_file=null;

        // 字节读写
        InputStream read_byte=null;
        OutputStream writer_byte=null;

        // 端口号
        int port=5000;

        // ss,cs 初始化
        ss=new ServerSocket(port);
        System.out.println("服务器已开启，等待连接...");
        cs=ss.accept();

        // 读写初始化
        read_file=new BufferedReader(new InputStreamReader(cs.getInputStream()));
        writer_file=new PrintWriter(cs.getOutputStream(),true);
        read_byte=cs.getInputStream();
        writer_byte=cs.getOutputStream();

        // 传输字符串
        String str="你好啊，客户端！！！";
        System.out.println("服务端所发送内容："+str);
        writer_file.println(str);


        // 发送列表
        List<String> tempList=new ArrayList<>();
        tempList.add("one");
        tempList.add("two");
        tempList.add("three");
        writer_file.println(tempList);


        // 发送一张图片
        String path1="C:/Users/m1309/Pictures/th.jpg";
        String path2="C:\\Users\\m1309\\Desktop\\temp.jpg";
        FileInputStream image_byte=new FileInputStream(path1);
        byte[] buff=new byte[600000];
        int len=0;
        while((len=image_byte.read(buff))!=-1){
            writer_byte.write(buff,0,len);
        }
        cs.shutdownOutput();
        image_byte.close();



        // 关闭所有相关调用
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();
        cs.close();
        ss.close();
    }
}
