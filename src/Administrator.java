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

        System.out.println("Press enter to continue...");
        Scanner end = new Scanner(System.in);
        end.nextLine();

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

        System.out.println("Press enter to continue...");
        Scanner end = new Scanner(System.in);
        end.nextLine();

    }

    public static void load_data() throws Exception {
//        BufferedReader category_inFile = new BufferedReader(new FileReader(new File("category.txt")));
//        Statement stmt = Main.conn.createStatement();

        System.out.print("Type in the Source Data Folder Path: ");

        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        System.out.print("Processing...");

        Scanner cScanner = new Scanner(new File("./"+path+"/category.txt")).useDelimiter("\t|\n");
        Scanner mScanner = new Scanner(new File("./"+path+"/manufacturer.txt")).useDelimiter("\t|\n");
        Scanner pScanner = new Scanner(new File("./"+path+"/part.txt")).useDelimiter("\t|\n");
        Scanner sScanner = new Scanner(new File("./"+path+"/salesperson.txt")).useDelimiter("\t|\n");
        Scanner tScanner = new Scanner(new File("./"+path+"/transaction.txt")).useDelimiter("\t|\n");

        PreparedStatement pstmt = Main.conn.prepareStatement("INSERT INTO category VALUES (?,?)");

        while (cScanner.hasNextLine()){
            pstmt.setInt(1, cScanner.nextInt());
            pstmt.setString(2, cScanner.next());
            pstmt.executeUpdate();
            if (cScanner.hasNextLine())
                cScanner.nextLine();
        }

        pstmt = Main.conn.prepareStatement("INSERT INTO manufacturer VALUES (?,?,?,?,?)");

        while (mScanner.hasNextLine()){
            pstmt.setInt(1, mScanner.nextInt());
            pstmt.setString(2, mScanner.next());
            pstmt.setString(3, mScanner.next());
            pstmt.setInt(4, mScanner.nextInt());
            pstmt.setInt(5, mScanner.nextInt());
            pstmt.executeUpdate();
            if (mScanner.hasNextLine())
                mScanner.nextLine();
        }

        pstmt = Main.conn.prepareStatement("INSERT INTO part VALUES (?,?,?,?,?,?)");

        while (pScanner.hasNextLine()){
            pstmt.setInt(1, pScanner.nextInt());
            pstmt.setString(2, pScanner.next());
            pstmt.setInt(3, pScanner.nextInt());
            pstmt.setInt(4, pScanner.nextInt());
            pstmt.setInt(5, pScanner.nextInt());
            pstmt.setInt(6, pScanner.nextInt());
            pstmt.executeUpdate();
            if (pScanner.hasNextLine())
                pScanner.nextLine();
        }

        pstmt = Main.conn.prepareStatement("INSERT INTO salesperson VALUES (?,?,?,?)");

        while (sScanner.hasNextLine()){
            pstmt.setInt(1, sScanner.nextInt());
            pstmt.setString(2, sScanner.next());
            pstmt.setString(3, sScanner.next());
            pstmt.setInt(4, sScanner.nextInt());
            pstmt.executeUpdate();
            if (sScanner.hasNextLine())
                sScanner.nextLine();
        }

        pstmt = Main.conn.prepareStatement("INSERT INTO transaction VALUES (?,?,?,(TO_DATE(?, 'DD/MM/YYYY')))");

        while (tScanner.hasNextLine()){
            pstmt.setInt(1, tScanner.nextInt());
            pstmt.setInt(2, tScanner.nextInt());
            pstmt.setInt(3, tScanner.nextInt());
            pstmt.setString(4, tScanner.next());
            pstmt.executeUpdate();
            if (tScanner.hasNextLine())
                tScanner.nextLine();
        }

        pstmt.close();

        System.out.print("Done! Data is inputted to the database!\n");

        System.out.println("Press enter to continue...");
        Scanner end = new Scanner(System.in);
        end.nextLine();

//        String Buf;
//        while ((Buf = category_inFile.readLine()) != null){
//            String[] result;
//            result = Buf.split(" ");
//            int cID=Integer.parseInt(result[0]);
//            String cName=result[1];
//            pstmt.setInt(1, cID);
//            pstmt.setString(2, cName);
//            pstmt.executeUpdate();
//        }
//        stmt.close();
    }

    public static void count_record() throws Exception{
        int count=0;

        System.out.print("Number of records in each table:\n");

        Statement stmt = Main.conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM category");
        rs.next();
        System.out.println("category: "+rs.getInt(1));

        rs = stmt.executeQuery("SELECT COUNT(*) FROM manufacturer");
        rs.next();
        System.out.print("manufacturer: "+rs.getInt(1)+"\n");

        rs = stmt.executeQuery("SELECT COUNT(*) FROM part");
        rs.next();
        System.out.print("part: "+rs.getInt(1)+"\n");

        rs = stmt.executeQuery("SELECT COUNT(*) FROM salesperson");
        rs.next();
        System.out.print("salesperson: "+rs.getInt(1)+"\n");

        rs = stmt.executeQuery("SELECT COUNT(*) FROM transaction");
        rs.next();
        System.out.print("transaction: "+rs.getInt(1)+"\n");

        System.out.println("Press enter to continue...");
        Scanner end = new Scanner(System.in);
        end.nextLine();

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
                    count_record();
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
