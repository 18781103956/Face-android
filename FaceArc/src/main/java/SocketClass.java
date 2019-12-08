import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SocketClass {
    // socket
    public ServerSocket ss=null;
    public Socket cs=null;
    // 文本读写
    public BufferedReader read_file=null;
    public PrintWriter writer_file=null;
    // 字节读写
    public InputStream read_byte=null;
    public OutputStream writer_byte=null;
    // 端口号
    public int port=5000;
    // 类中变量的初始化
    public void init() throws IOException {
        // ss,cs 初始化
        ss=new ServerSocket(port);
        System.out.println("服务器已开启，等待连接...");
        cs=ss.accept();

        // 读写初始化
        read_file=new BufferedReader(new InputStreamReader(cs.getInputStream()));
        writer_file=new PrintWriter(cs.getOutputStream(),true);
        read_byte=cs.getInputStream();
        writer_byte=cs.getOutputStream();
    }
    // 类中变量的关闭
    public void close() throws IOException {
        // 关闭所有相关调用
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();
        cs.close();
        ss.close();
    }
    // 发送文本
    public void send_file(String str) throws IOException {
        System.out.println("服务端所发送内容："+str);
        writer_file.println(str);
    }
    // 接收文本
    public String get_file() throws IOException {
        String str=read_file.readLine();
        System.out.println("收到文本："+str);
        return str;
    }
    // 发送列表
    public void send_List(List tempList) throws IOException {
        writer_file.println(tempList);
        System.out.println("已发送以下列表：");
        for(int i=0;i<tempList.size();++i){
            System.out.println(tempList.get(i));
        }
    }
    // 接收列表
    public List get_List() throws IOException {
        List<String> tempList=new ArrayList<>();
        tempList= Collections.singletonList(read_file.readLine());
        String tempstr=tempList.get(0);
        tempstr=tempstr.substring(1,tempstr.length()-1);
        tempList= Arrays.asList(tempstr.split(", "));
        System.out.println("已接收以下列表：");
        for(int i=0;i<tempList.size();++i){
            System.out.println(tempList.get(i));
        }
        return tempList;
    }
    // 发送图片
    public void send_image(String path) throws IOException {
        FileInputStream image_byte=new FileInputStream(path);
        byte[] buff=new byte[600000];
        int len=0;
        while((len=image_byte.read(buff))!=-1){
            writer_byte.write(buff,0,len);
        }
        cs.shutdownOutput();
        image_byte.close();
        System.out.println("该地址图片已发送: "+path);
    }
    // 接收图片
    public void get_image(String path) throws IOException, InterruptedException {
        FileOutputStream image_byte=new FileOutputStream(path);
        byte[] buff=new byte[600000];
        int len=0;
        while((len=read_byte.read(buff))!=-1){
            image_byte.write(buff,0,len);
        }
        Thread.sleep(2000);
        image_byte.close();
        System.out.println("图片已收到: "+path);
    }

}
