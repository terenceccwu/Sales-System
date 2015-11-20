/**
 * Created by Nakamura on 20/11/2015.
 */

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Administrator {

    public static void create_table() throws Exception{

        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", "d103", "123456789");
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("CREATE TABLE category(" +
                "cID INTEGER(1) PRIMARY KEY," +
                "cName VARCHAR2(20));");

        stmt.executeUpdate("CREATE TABLE manufacturer(" +
                "mID INTEGER(2) PRIMARY KEY," +
                "mName VARCHAR2(20)," +
                "mAddress VARCHAR2(50)," +
                "mPhoneNumber(8)," +
                "mWarrantyPeriod(1));");

        stmt.executeUpdate("CREATE TABLE part(" +
                "pID INTEGER(3) PRIMARY KEY," +
                "pName VARCHAR2(20)," +
                "pPrice INTEGER(5)," +
                "mID INTEGER(2) FOREIGN KEY REFERENCES manufacturer(mID)," +
                "cID INTEGER(1) FOREIGN KEY REFERENCES category(cID)," +
                "pAvailableQuantity INTEGER(2));");

        stmt.executeUpdate("CREATE TABLE salesperson(" +
                "sID INTEGER(2) PRIMARY KEY," +
                "sName VARCHAR2(20)," +
                "sAddress VARCHAR2(50)," +
                "sPhoneNumber INTEGER(8));");

        stmt.executeUpdate("CREATE TABLE transaction(" +
                "tID INTEGER(4) PRIMARY KEY," +
                "pID INTEGER(3) FOREIGN KEY REFERENCES part(pID)," +
                "sID INTEGER(2) FOREIGN KEY REFERENCES salesperson(sID)," +
                "tDate DATE NOT NULL);");

        stmt.close();

    }

    public  static void delete_table() throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", "d103", "123456789");
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("DROP TABLE category");
        stmt.executeUpdate("DROP TABLE manufacturer");
        stmt.executeUpdate("DROP TABLE part");
        stmt.executeUpdate("DROP TABLE salesperson");
        stmt.executeUpdate("DROP TABLE transaction");

        stmt.close();

    }

    public static void menu() throws Exception{

        boolean i = true;

        while (i) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("-----Operation for administrator menu-----\n" +
                    "What kinds of operation would you like to perform?\n" +
                    "1. Create all tables\n" +
                    "2. Delete all tables\n" +
                    "3. Load from datafile\n" +
                    "4. Show number of records in each table\n" +
                    "5. Return to the main menu\n" +
                    "Enter your Choice: ");

            String choice = scanner.next();

            switch (choice) {
                case "1":
                    create_table();
                    break;
                case "2":
                    delete_table();
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    i = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }

        }
    }

}
