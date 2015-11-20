/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci3170;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
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
        System.out.println("1. What kinds of operation would you like to perform?");
        System.out.println("2. Show the sales record of a salesperson within a period");
        System.out.println("3. Show the total sales value of eacu manufacturer");
        System.out.println("4. Return to main menu");
        System.out.print("Enter your Choice: ");
        int choice = Integer.parseInt(in.readLine());
        while (choice != 4) {
            if (choice == 1) {
                System.out.print("Enter The Salesperson ID: ");
                int salesID = Integer.parseInt(in.readLine());
                System.out.println("Type in the starting date [dd/mm/yyyy]: ");
                String startdate = in.readLine();
                System.out.println("Type in the ending date [dd/mm/yyyy]: ");
                String enddate = in.readLine();
                System.out.println("Transaction Record:");
                System.out.println("| ID | Part ID | Part Name | Manufacturer | Price | Date |");
                ResultSet rs = stmt.executeQuery("SELECT T.tID, P.pID, P.pName, M.mName, P.pPrice, T.tDate\n"
                        + "FROM transaction T, part P, manufacturer M, salesperson S\n"
                        + "WHERE T,sID=S.sID AND T.pID=P.pID AND S.sID=" + salesID + "AND T.tdate>=" + startdate + "AND T.tdate<=" + enddate
                        + "ORDER BY t.tDate DESC");
                while (rs.next()) {
                    System.out.println("| " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5) + " | " + rs.getString(6) + " | ");
                }
                System.out.println("End of Query");
            } else if (choice == 2) {
                System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
                ResultSet rs = stmt.executeQuery("SELECT  M.mID, M.mName,SUM(P.pPrice) AS Totalsalesvalue\n"
                        + "FROM  manufacturer M, part P\n"
                        + "WHERE M.mID=P.mID\n"
                        + "GROUP BY M.mID\n"
                        + "ORDER BY Totalsalesvalue DESC");
                while (rs.next()) {
                    System.out.println("| " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | ");
                }
                System.out.println("End of Query");
            } else if (choice == 3) {
                System.out.println("Type in the number of parts: ");
                int noofparts = Integer.parseInt(in.readLine());
                System.out.println("| Part ID | Part Name | No. of Transaction |");
                
                System.out.println("End of Query");
            }
            System.out.println("-----Operation for manager menu-----");
            System.out.println("1. What kinds of operation would you like to perform?");
            System.out.println("2. Show the sales record of a salesperson within a period");
            System.out.println("3. Show the total sales value of eacu manufacturer");
            System.out.println("4. Return to main menu");
            System.out.print("Enter your Choice: ");
            choice = Integer.parseInt(in.readLine());
        }
    }
}