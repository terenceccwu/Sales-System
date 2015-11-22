/**
 * Created by Nakamura on 20/11/2015.
 */

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Administrator {

    public static void create_table() throws Exception{

        Statement stmt = Main.conn.createStatement();

        System.out.print("Processing...");

        stmt.executeUpdate("CREATE TABLE category(" +
                "cID NUMBER(1)," +
                "cName VARCHAR2(20)," +
                "PRIMARY KEY(cID))");

        stmt.executeUpdate("CREATE TABLE manufacturer(" +
                "mID NUMBER(2) PRIMARY KEY," +
                "mName VARCHAR2(20)," +
                "mAddress VARCHAR2(50)," +
                "mPhoneNumber NUMBER(8)," +
                "mWarrantyPeriod NUMBER(1))");

        stmt.executeUpdate("CREATE TABLE part(" +
                "pID NUMBER(3) PRIMARY KEY," +
                "pName VARCHAR2(20)," +
                "pPrice NUMBER(5)," +
                "mID NUMBER(2) REFERENCES manufacturer(mID)," +
                "cID NUMBER(1) REFERENCES category(cID)," +
                "pAvailableQuantity NUMBER(2)" +
                "CONSTRAINT out_of_stock CHECK (pAvailableQuantity >= 0))");

        stmt.executeUpdate("CREATE TABLE salesperson(" +
                "sID NUMBER(2) PRIMARY KEY," +
                "sName VARCHAR2(20)," +
                "sAddress VARCHAR2(50)," +
                "sPhoneNumber NUMBER(8))");

        stmt.executeUpdate("CREATE TABLE transaction(" +
                "tID NUMBER(4) PRIMARY KEY," +
                "pID NUMBER(3) REFERENCES part(pID)," +
                "sID NUMBER(2) REFERENCES salesperson(sID)," +
                "tDate DATE NOT NULL)");

        System.out.print("Done! Database is initialized!\n");

        stmt.close();

    }

    public static void delete_table() throws Exception {
        Statement stmt = Main.conn.createStatement();
        System.out.print("Processing...");
        stmt.executeUpdate("DROP TABLE transaction");
        stmt.executeUpdate("DROP TABLE part");
        stmt.executeUpdate("DROP TABLE category");
        stmt.executeUpdate("DROP TABLE manufacturer");
        stmt.executeUpdate("DROP TABLE salesperson");
        System.out.print("Done! Database is removed!\n");
        stmt.close();

    }

    public static void load_data() throws Exception {
        BufferedReader category_inFile = new BufferedReader(new FileReader(new File("category.txt")));
        Statement stmt = Main.conn.createStatement();

        PreparedStatement pstmt = Main.conn.prepareStatement("INSERT INTO category VALUES (?,?)");

        String Buf;

        while ((Buf = category_inFile.readLine()) != null){
            String[] result;
            result = Buf.split(" ");
            int cID=Integer.parseInt(result[0]);
            String cName=result[1];
            pstmt.setInt(1, cID);
            pstmt.setString(2, cName);
            pstmt.executeUpdate();
        }

        pstmt.close();
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

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    create_table();
                    break;
                case 2:
                    delete_table();
                    break;
                case 3:
                    load_data();
                    break;
                case 4:
                    break;
                case 5:
                    i = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }

        }
    }

}
