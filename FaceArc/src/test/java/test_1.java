import java.sql.*;

public class test_1 {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    // static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // static final String DB_URL = "jdbc:mysql:/ /localhost:3306/test";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sys?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;

        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);

        // 打开链接
        System.out.println("连接数据库...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        // 执行查询
        System.out.println("实例化Statement对象...");
        stmt = conn.createStatement();


        String sql;
        sql = "SELECT id, name, url FROM websites";
        ResultSet rs = stmt.executeQuery(sql);

        // 展开结果集数据库
        while(rs.next()){
            // 通过字段检索
            int id  = rs.getInt("id");
            String name = rs.getString("name");
            String url = rs.getString("url");

            // 输出数据
            System.out.print("ID: " + id);
            System.out.print(", 站点名称: " + name);
            System.out.print(", 站点 URL: " + url);
            System.out.print("\n");
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();

        System.out.println("Goodbye!");
    }
}
