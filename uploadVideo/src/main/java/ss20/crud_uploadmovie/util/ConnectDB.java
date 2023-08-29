package ss20.crud_uploadmovie.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/cinema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "274996";
    public static Connection openConnection()  {
        Connection con = null;
        try {
            // khai báo class cho driver
            Class.forName(DRIVER);
            // thực hiện mở kết nối
            con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return  con;
    }

    public  static void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
