import java.io.File;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by terence on 11/16/15.
 */
public class Main {

    public static void main(String args[]) throws Exception {
        //get DB login credentials
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter DB username: ");
        String username = scanner.next();
        System.out.print("Enter DB password: ");
        String password = scanner.next();

        //connect to DB
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", username, password);

        //Query
        Statement stmt = conn.createStatement();


        String query = new Scanner(new File("test.sql")).useDelimiter(";").next();


        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }

        System.out.println("hihihi");
        Admin.create_table();

        //close
        rs.close();
        stmt.close();
        conn.close();
    }
}

