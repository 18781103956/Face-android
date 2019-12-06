package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class takeSocket<run> {

    //客户端
    public static Socket cs = null;
    //文本读取
    public static BufferedReader read_file = null;
    //文本写入
    public static PrintWriter writer_file=null;
    //字节读取
    public static InputStream read_byte = null;
    //字节写入
    public static OutputStream writer_byte = null;
    //端口号
    public static int port = 37100;


    public static void Connect() throws IOException {
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

    public static void ClossConnect() throws IOException {
        read_file.close();
        writer_file.close();
        read_byte.close();
        writer_byte.close();

        cs.close();
    }

    public static void SendOperationNum(String num){
        //1.发送一个操作码
        writer_file.println(num);
    }

    public static String State() throws IOException {
        //2.收到服务器的一个状态码
        String status_num = read_file.readLine();
        System.out.println("服务器所返回的状态码："+status_num);
        return status_num;
    }

    public static void SendString(String str){
        //3.发送一个字符串
        writer_file.println(str);
    }

    public static String ReceiveString() throws IOException {
        //4.收到服务器的一个字符串
        String server_str=read_file.readLine();
        System.out.println("服务器所发送的信息："+server_str);
        return server_str;
    }

    public static void SendImage() throws IOException {
        //5.发给服务器一个图片

        String path="/home/ant/桌面/4.png";
        FileInputStream image_byte = new FileInputStream(path);
        byte[] buff = new byte[10240];
        int len=0;
        while ((len = image_byte.read(buff)) != -1)
        {
            writer_byte.write(buff,0,len);
        }
        cs.shutdownOutput();//通知服务端，数据发送完毕
        image_byte.close();
        cs = new Socket("wise-ant.picp.io", 37190);
    }

    public static void takeSocket() throws IOException {
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

    /*public void run() throws IOException {

    }*/



}
