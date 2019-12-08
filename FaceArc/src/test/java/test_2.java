import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test_2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySQLClass tempMS=new MySQLClass();
        tempMS.initMySQL();

        List<String> tempList=new ArrayList<>();
        tempList.add("6016203284");
        tempList.add("123456");
        tempList.add("小明");
        tempList.add("男");
        tempList.add("13097558301");
        tempList.add("天津大学仁爱学院");
        tempList.add("计算机系");
        tempList.add("6016203284");
        tempList.add("C:/Users/m1309/Documents/FaceAreImage/6016203284.jpg");

        tempMS.reg(tempList);

        tempMS.closeMySQL();
    }
}
