import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SocketDemoCilent {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket cs=null;

        // 文本读写
        BufferedReader read_file=null;
        PrintWriter writer_file=null;

        // 字节读写
        InputStream read_byte=null;
        OutputStream writer_byte=null;


        // cs 初始化
        // ipconfig --- WIN10 查看本机IP
        // ifconfig --- Linux 查看本机IP
        int port=37190;//wise-ant.picp.io 37190
        InetAddress serverIP=InetAddress.getByName("wise-ant.picp.io");
        cs=new Socket(serverIP,port);


        // 读写初始化
        read_file=new BufferedReader(new InputStreamReader(cs.getInputStream()));
        writer_file=new PrintWriter(cs.getOutputStream(),true);
        read_byte=cs.getInputStream();
        writer_byte=cs.getOutputStream();


        // 接收字符串
        String str=read_file.readLine();
        System.out.println(str);

        // 接收列表
        List<String> tempList=new ArrayList<>();
        tempList=Collections.singletonList(read_file.readLine());
        String tempstr=tempList.get(0);
        tempstr=tempstr.substring(1,tempstr.length()-1);
        tempList= Arrays.asList(tempstr.split(", "));
        for(int i=0;i<tempList.size();++i){
            System.out.println(tempList.get(i));
        }

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
        System.out.println("图片已收到");




        // 关闭所有相关调用
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();
        cs.close();
    }
}
