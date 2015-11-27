import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author handason
 */
public class Manager {

    public static void menu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("-----Operation for manager menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Show the sales record of a salesperson within a period");
        System.out.println("2. Show the total sales value of each manufacturer");
        System.out.println("3. Show the N most popular part");
        System.out.println("4. Return to main menu");
        System.out.print("Enter your Choice: ");
        int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.print("Enter The Salesperson ID: ");
                String salesID = in.readLine();
                System.out.print("Type in the starting date [dd/mm/yyyy]: ");
                String startdate = in.readLine();
                System.out.print("Type in the ending date [dd/mm/yyyy]: ");
                String enddate = in.readLine();
                System.out.println("Transaction Record:");
                System.out.println("| ID | Part ID | Part Name | Manufacturer | Price | Date |");
                PreparedStatement pstmt = Main.conn.prepareStatement("SELECT T.tID, P.pID, P.pName, M.mName, P.pPrice, T.tDate " +
                                        "FROM transaction T, part P, manufacturer M, salesperson S " +
                                        "WHERE T.sID = S.sID AND T.pID = P.pID AND M.mID = P.mID AND S.sID = ? AND T.tDate >= (TO_DATE(?, 'DD/MM/YYYY')) AND T.tDate <= (TO_DATE(?, 'DD/MM/YYYY')) " +
                                        "ORDER BY t.tDate DESC");
                pstmt.setString(1, salesID);
                pstmt.setString(2, startdate);
                pstmt.setString(3, enddate);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("| " + rs.getInt(1) + " | " + rs.getInt(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getInt(5) + " | " + rs.getString(6).substring(0,10) + " | ");
                }
                System.out.println("End of Query");
                rs.close();
                pstmt.close();
            } else if (choice == 2) {
                System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
                PreparedStatement pstmt = Main.conn.prepareStatement("SELECT  M.mID, M.mName,SUM(P.pPrice) AS Totalsalesvalue "
                        + "FROM  manufacturer M, part P, transaction T "
                        + "WHERE M.mID=P.mID AND P.pID=T.pID "
                        + "GROUP BY M.mID, M.mName "
                        + "ORDER BY Totalsalesvalue DESC");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("| " + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getInt(3) + " | ");
                }
                System.out.println("End of Query");
                rs.close();
                pstmt.close();
            } else if (choice == 3) {
                System.out.println("Type in the number of parts: ");
                int noofparts = scanner.nextInt();
                System.out.println("| Part ID | Part Name | No. of Transaction |");
                PreparedStatement pstmt = Main.conn.prepareStatement("SELECT P.pID, P.pName, COUNT(*) AS nooftransactions "
                        + "FROM part P, transaction T "
                        + "WHERE P.pID=T.pID "
                        + "GROUP BY P.pID, P.pName "
                        + "ORDER BY nooftransactions DESC ");
                ResultSet rs = pstmt.executeQuery();

                for (int i = 0; i < noofparts && rs.next(); i++) {
                    System.out.println("| " + rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getInt(3) + " | ");
                }

                System.out.println("End of Query");
                System.out.print("\n");
                rs.close();
                pstmt.close();
            } else if (choice == 4) {
                System.out.print("\n");
            } else {
                System.out.print("Command not found\nPress Enter to Continue...");
                System.in.read();
            }
        }
    }

