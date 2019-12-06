package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BeginSignSocket extends Thread {
    public String classnum;
    public BeginSignSocket(String classnum){
        this.classnum = classnum;
    }

    public void fun() throws IOException{
        System.out.println("运行了BeginSignSocket--fun");
        java.net.Socket s = new Socket("wise-ant.picp.io",37190);
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("51");
        String return_code =read_file.readLine();  //读一个返回码
        System.out.println("服务器返回的："+return_code);

        writer_file.println(this.classnum);
        System.out.println("服务器返回num："+classnum);


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
