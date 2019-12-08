import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class cilent{

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

        String str;
/*-----------------------------------------------------------------------*/

        // 发送操作码
        writer_file.println("00");

        System.out.println("*****************888");
        // 接收反馈
        str=read_file.readLine();
        System.out.println("反馈码:"+str);

        List<String> tempList=new ArrayList<>();
        tempList.add("6016203284");
        tempList.add("123456");
        tempList.add("小明");
        tempList.add("男");
        tempList.add("13097558301");
        tempList.add("天津大学仁爱学院");
        tempList.add("计算机系");
        tempList.add("6016203284");
        writer_file.println(tempList);


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




/*----------------------------------------------------------------*/
        // 关闭所有相关调用
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();
        cs.close();
    }
}
