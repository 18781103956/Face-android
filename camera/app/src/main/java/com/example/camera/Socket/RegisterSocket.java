package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RegisterSocket extends Thread{

    //客户端
    private static Socket cs = null;
    //文本读取
    private static BufferedReader read_file = null;
    //文本写入
    private static PrintWriter writer_file=null;
    //字节读取
    private static InputStream read_byte = null;
    //字节写入
    private static OutputStream writer_byte = null;
    //端口号
    private static int port = 37190;

    List<String> list = new ArrayList<>();  ///列表
    private String path; ///路径

    public RegisterSocket(String num, String password, String name,String sex,String phone, String school, String academy, String id, String path){
        list.add(num);
        System.out.println("--------------------+"+num);
        list.add(password);
        list.add(name);
        list.add(sex);
        list.add(phone);
        list.add(school);
        list.add(academy);
        list.add(id);
        this.path = path;
        list.add("/home/ant/文档/人脸图片/"+ list.get(0)+".jpg");
    }


    public void Connect() throws IOException {
        cs = new Socket("wise-ant.picp.io",port);
        //文本读取
        read_file = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        //文本写入
        writer_file = new PrintWriter(cs.getOutputStream(),true);
        //字节读取
        read_byte = cs.getInputStream();
        //字节写入
        writer_byte = cs.getOutputStream();
    }

    public void ClossConnect() throws IOException {
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();

        cs.close();
    }


    public void SendOperationNum(String num){
        //1.发送一个操作码
        writer_file.println(num);
    }

    public String State() throws IOException {
        //2.收到服务器的一个状态码
        String status_num = read_file.readLine();
        System.out.println("服务器所返回的状态码："+status_num);
        return status_num;
    }

    public void SendString(String str){
        //3.发送一个字符串
        writer_file.println(str);
    }

    public String ReceiveString() throws IOException {
        //4.收到服务器的一个字符串
        String server_str=read_file.readLine();
        System.out.println("服务器所发送的信息："+server_str);
        return server_str;
    }

    public void SendImage() throws IOException {
        //5.发给服务器一个图片
        FileInputStream image_byte = new FileInputStream(path);
        byte[] buff = new byte[500240];
        int len=0;
        while ((len = image_byte.read(buff)) != -1)
        {
            writer_byte.write(buff,0,len);
        }
        cs.shutdownOutput();//通知服务端，数据发送完毕
        image_byte.close();
    }

    public void takeSocket() throws IOException {
        //6.收取服务器的一张图片
        String path_1="/home/ant/桌面/Zzz.png";
        FileOutputStream image_byte_1 = new FileOutputStream(path_1);
        byte[] buff_1 = new byte[10240];
        int len_1=0;
        //往字节流里写图片数据
        while((len_1=read_byte.read(buff_1))!=-1)
        {
            image_byte_1.write(buff_1,0,len_1);
        }
        image_byte_1.close();
        System.out.println("图片已收到，存放地址："+path_1);
    }

    //============================================================================
    //获取列表
    public static List get_list() throws IOException {
        List<String> tempList=new ArrayList<>();
        read_file=new BufferedReader(new InputStreamReader(cs.getInputStream()));
        tempList = Collections.singletonList(read_file.readLine());
        String tempStr=tempList.get(0);
        tempStr=tempStr.substring(1,tempStr.length()-1);
        //System.out.println(tempStr);
        tempList= Arrays.asList(tempStr.split(", "));
        //System.out.println("列表："+tempList.get(0).getClass().toString());
        //read_file.close();
        return tempList;
    }

    //发送列表
    public void send_list() throws IOException {
        writer_file=new PrintWriter(cs.getOutputStream(),true);
        System.out.println(list.get(0));
        writer_file.println(list);
        //writer_file.close();
    }

    //=============================================================================

    @Override
    public void run() {
        super.run();
        try {
            Connect();
            SendOperationNum("00");
            System.out.println(State());
            send_list();
            //System.out.println(list);
            SendImage();
            System.out.println(State());
            ClossConnect();
            Thread.sleep(200);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
