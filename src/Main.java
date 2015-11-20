import java.io.File;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by terence on 11/16/15.
 */
public class Main {

    static Connection conn;

    public static void main(String args[]) throws Exception {

        //connect to DB
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", "d103", "123456789");

        Administrator.menu(); //debugging

        //Query
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM fuser ORDER BY userid");
        pstmt.setString(1,"%Terence%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }


        Sales.menu();
        //close
        rs.close();
        pstmt.close();
        conn.close();
    }
}

