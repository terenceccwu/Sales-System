import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author handason
 */
public class Manager {

    public static void menu() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@db12.cse.cuhk.edu.hk:1521:db12", "d108", "sfcrpchv");
        Statement stmt = conn.createStatement();

        System.out.println("-----Operation for manager menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Show the sales record of a salesperson within a period");
        System.out.println("2. Show the total sales value of each manufacturer");
        System.out.println("3. Show the N most popular part");
        System.out.println("4. Return to main menu");
        System.out.print("Enter your Choice: ");
        int choice = Integer.parseInt(in.readLine());
        if (choice == 1 |choice == 2 |choice == 3) {
            if (choice == 1) {
                System.out.print("Enter The Salesperson ID: ");
                int salesID = Integer.parseInt(in.readLine());
                System.out.println("Type in the starting date [dd/mm/yyyy]: ");
                String startdate = in.readLine();
                System.out.println("Type in the ending date [dd/mm/yyyy]: ");
                String enddate = in.readLine();
                System.out.println("Transaction Record:");
                System.out.println("| ID | Part ID | Part Name | Manufacturer | Price | Date |");
                ResultSet rs = stmt.executeQuery("SELECT T.tID, P.pID, P.pName, M.mName, P.pPrice, T.tDate "
                        + "FROM transaction T, part P, manufacturer M, salesperson S "
                        + "WHERE T,sID=S.sID AND T.pID=P.pID AND S.sID=" + salesID + " AND T.tdate>=" + startdate + " AND T.tdate<=" + enddate
                        + " ORDER BY t.tDate DESC");
                while (rs.next()) {
                    System.out.println("| " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5) + " | " + rs.getString(6) + " | ");
                }
                System.out.println("End of Query");
            } else if (choice == 2) {
                System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
                ResultSet rs = stmt.executeQuery("SELECT  M.mID, M.mName,SUM(P.pPrice) AS Totalsalesvalue "
                        + "FROM  manufacturer M, part P, transaction T "
                        + "WHERE M.mID=P.mID AND P.pID=T.pID "
                        + "GROUP BY M.mID, M.mName "
                        + "ORDER BY Totalsalesvalue DESC");
                while (rs.next()) {
                    System.out.println("| " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | ");
                }
                System.out.println("End of Query");
            } else if (choice == 3) {
                System.out.println("Type in the number of parts: ");
                int noofparts = Integer.parseInt(in.readLine());
                System.out.println("| Part ID | Part Name | No. of Transaction |");
                ResultSet rs = stmt.executeQuery("SELECT P.pID, P.pName, COUNT(*) AS nooftransactions "
                        + "FROM part P, transaction T "
                        + "WHERE P.pID=T.pID "
                        + "GROUP BY P.pID, P.pName "
                        + "ORDER BY nooftransactions DESC ");
                int i;
                for (i = 0; i < noofparts; i++) {
                    System.out.println("| " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | ");
                    rs.next();
                }
                System.out.println("End of Query");
                System.out.print("\n");
            }
        }
        else if (choice == 4) {
            System.out.print("\n");
        }
        else {
            System.out.print("Command not found\nPress Enter to Continue...");
            System.in.read();
        }
    }
}
