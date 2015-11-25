import java.io.File;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by terence on 11/16/15.
 */
public class Main {

    static Connection conn = null;

    public static void main(String args[]) throws Exception {

        //connect to DB
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", "d103", "123456789");

        boolean i = true;
        while (i) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("\n-----Main menu-----\n" +
                    "What kinds of operation would you like to perform?\n" +
                    "1. Operation for administrator\n" +
                    "2. Operation for salesperson\n" +
                    "3. Operation for manager\n" +
                    "4. Exit this program\n" +
                    "Enter your Choice: ");

            int choice = scanner.nextInt();

            System.out.print("\n");

            switch (choice) {
                case 1:
                    Administrator.menu();
                    break;
                case 2:
                    Sales.menu();
                    break;
                case 3:
                    Manager.menu();
                    break;
                case 4:
                    i = false;
                    break;
                default:
                    System.out.print("Command not found\nPress Enter to Continue...");
                    System.in.read();
                    i = false;
                    break;
            }

        }
        conn.close();
    }
}

