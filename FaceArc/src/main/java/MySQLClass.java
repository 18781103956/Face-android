import java.sql.*;
import java.util.List;

public class MySQLClass {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    // static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql:/ /localhost:3306/test";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/facearc?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";
    public static Connection conn = null;
    public static Statement stmt = null;
    public static PreparedStatement psql;
    public static ResultSet res;

    // 初始化数据库
    public void initMySQL() throws ClassNotFoundException, SQLException {
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);

        // 打开链接
        System.out.println("连接数据库...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        // 执行查询
        System.out.println("实例化Statement对象...");
        stmt = conn.createStatement();
    }

    // 关闭数据库
    public void closeMySQL() throws ClassNotFoundException, SQLException {
        stmt.close();
        conn.close();
    }

    // 以下为关于数据库的基本操作-----------------------------------------------
    // 1 注册账号
    public void reg(List tempList) throws SQLException {
        String sql;
        sql = "INSERT INTO 注册表单 VALUES (";
        for(int i=0;i<tempList.size();++i){
            sql=sql+"'"+tempList.get(i)+"'";
            if(i!=tempList.size()-1)
                sql=sql+",";
        }
        sql=sql+");";
        System.out.println(sql);

        psql=conn.prepareStatement(sql);
        psql.executeUpdate();


        psql.close();
    }

}
