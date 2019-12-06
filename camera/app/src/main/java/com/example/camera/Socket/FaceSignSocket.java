package com.example.camera.Socket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static com.example.camera.Socket.takeSocket.writer_byte;

public class FaceSignSocket extends Thread{
    public String path;
    public String id;
    public FaceSignSocket(String id,String path){
        this.id = id;
        this.path = path;
        Log.i("---->", "图片地址"+path);
    }

    public void fun()throws IOException {
        //1.连接诶服务器
        Socket s = new Socket("wise-ant.picp.io",37190);

        System.out.println("已连接到服务器端口，准备传送图片...");
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));  //文字写入管道
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true); //从管道读文字

        writer_file.println("40");  //发送一个操作吗
        String return_code =read_file.readLine();  //读一个返回码
        System.out.println("服务器返回的："+return_code);

        writer_file.println(this.id);
       FileInputStream fis = new FileInputStream(this.path);
       OutputStream out = s.getOutputStream();
       byte [] buf = new byte[500240];
       int len = 0;
       while((len = fis.read(buf)) != -1){
           out.write(buf,0,len);
       }

       s.shutdownOutput();
       fis.close();
       out.close();
       read_file.close();
       writer_file.close();
       s.close();
    }

    @Override
    public void run() {
        super.run();
        try{
            fun();
            Thread.sleep(200);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
