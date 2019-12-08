import sun.applet.resources.MsgAppletViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {

        while(true){
            // socket、MySQL初始化
            SocketClass sctemp=new SocketClass();
            sctemp.init();
            MySQLClass MStemp=new MySQLClass();
            MStemp.initMySQL();



            // 一系列相关操作
            String takeNum=sctemp.get_file();
            System.out.println(takeNum);
            if(takeNum.equals("00")){
                sctemp.send_file("200-00");
                List<String> tempList=new ArrayList<>(sctemp.get_List());
                String str="C:/Users/m1309/Documents/FaceAreImage/"+(String)tempList.get(0)+".jpg";
                System.out.println(str);
                tempList.add(str);
                sctemp.get_image((String) tempList.get(8));
                MStemp.reg(tempList);
            }




            // 关闭socket、MySQL
            sctemp.close();
            MStemp.closeMySQL();
        }

    }
}
