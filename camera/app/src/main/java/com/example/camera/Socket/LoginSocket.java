package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class LoginSocket extends Thread{



    public String num;
    public String password;
    public String result;
    public LoginSocket(String num, String password){
        this.num = num;
        System.out.println(this.num);
        this.password = password;
        System.out.println(this.password);
    }



    public String getResult() {
        return result;
    }

    public void fun()throws IOException{
        System.out.println("运行了Login--fun");
        java.net.Socket s = new Socket("wise-ant.picp.io",37190);
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);
        writer_file.println("10");
        String temp_str=read_file.readLine();
        System.out.println("服务器返回的："+temp_str);
        writer_file.println(this.num);
        writer_file.println(this.password);
        String recode_str = read_file.readLine();
        System.out.println("服务器返回的登录结果："+recode_str);
        this.result = recode_str;
        read_file.close();
        writer_file.close();
        s.close();
    }

    @Override
    public void run() {
        super.run();
        try {
            fun();
            Thread.sleep(200);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}
