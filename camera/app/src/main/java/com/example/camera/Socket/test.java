package com.example.camera.Socket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;


public class test extends  Thread{
    private String filepath;

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void func(){

            Socket socket=null;

            try{
                InetAddress serverIP=InetAddress.getByName("278835rp29.qicp.vip");//wise-ant.picp.io
                int port=15570;//37190;
                socket=new Socket(serverIP,port);
                System.out.println("这是图片地址："+filepath);
                FileInputStream fis = new FileInputStream(filepath);
                FileOutputStream out = (FileOutputStream) socket.getOutputStream();
                byte[] buf = new byte[10240];
                    int len = 0;
                    while ((len = fis.read(buf)) != -1)
                    {
                        out.write(buf,0,len);
                    }
                    socket.shutdownOutput();
                    //String str=in.readLine();
                    //System.out.println(str);
                    InputStream in = socket.getInputStream();
                    byte[] bufIn = new byte[10240];
                    int num = in.read(bufIn);

                    fis.close();
                    out.close();
                    in.close();
                    socket.close();
                    //Thread.sleep(2000);
                }
                catch(IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }


    @Override
    public void run() {
        super.run();
        try {
            func();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

