import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by terence on 11/20/15.
 */
public class Sales {

    private static void search_part() throws Exception
    {
        int search_by = 0; //1=by part name; 2=by manufacturer name
        String keyword;
        int order_by = 0; //1=ascending; 2=descending

        Scanner scanner = new Scanner(System.in);
        boolean i = true;

        while(i) {
            System.out.print(
                "Choose the Search criterion\n" +
                "1. Part Name\n" +
                "2. Manufacturer Name \n" +
                "Enter Your Choice: ");

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    search_by = 1;
                    i = false;
                    break;
                case "2":
                    search_by = 2;
                    i = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }

        System.out.print("Type in the Search Keyword: ");
        keyword = scanner.next();

        i = true;

        while(i) {
            System.out.print(
                "Choose ordering:\n" +
                "1. By price, ascending order\n" +
                "2. By price, descending order\n" +
                "Enter Your Choice: ");

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    order_by = 1;
                    i = false;
                    break;
                case "2":
                    order_by = 2;
                    i = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }


        //QUERY

        String search_field = "";
        if(search_by == 1)
            search_field = search_field.concat("p.pname");
        else
            search_field = search_field.concat("m.mname");

        String order = "";
        if(order_by == 2)
            order = order.concat("DESC");

        PreparedStatement pstmt = Main.conn.prepareStatement(
            "SELECT p.pid, p.pname, m.mname, c.cname, p.pAvailableQuantity, m.mWarrantyperiod, p.pprice " +
            "FROM part p, manufacturer m, category c " +
            "WHERE p.mid = m.mid AND p.cid = c.cid AND "+search_field+" LIKE ? " +
            "ORDER BY p.pprice "+order);

        keyword = "%" + keyword + "%";
        pstmt.setString(1,keyword);

        //PRINT QUERY
        ResultSet rs = pstmt.executeQuery();
        System.out.printf("| %-3s | %-20s | %-20s | %-20s | %-10s | %-10s | %-5s |\n","ID","Name","Manufacturer","Category","Quantity","Warranty","Price");
        while (rs.next()) {
            System.out.printf("| %3d | %-20s | %-20s | %-20s | %10d | %10d | %5d |\n",
                    rs.getInt(1),rs.getString(2),
                    rs.getString(3),rs.getString(4),
                    rs.getInt(5),rs.getInt(6),rs.getInt(7));
        }
        System.out.println("End of Query");
    }

    private static void sell_part() throws Exception
    {
        PreparedStatement pstmt = Main.conn.prepareStatement(
            "UPDATE part " +
            "SET pAvailableQuantity=pAvailableQuantity-1 " +
            "WHERE pid=?");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter The Part ID: ");
        int pid = scanner.nextInt();

        pstmt.setInt(1,pid);

        try
        {
            if(pstmt.executeUpdate() != 0)
                System.out.println("update successful!");
            else
                System.out.println("Part not found!");
        } catch (SQLException e)
        {
            System.out.println("Out of Stock!");
        }

    }

    public static void menu() throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        boolean i = true;

        while(i) {
            System.out.print(
                "\n----Operation for salesperson menu----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Search for parts\n" +
                "2. Sell a part\n" +
                "3. Return to the main menu\n" +
                "Enter Your Choice: ");

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    search_part();
                    break;
                case "2":
                    sell_part();
                    break;
                case "3":
                    i = false;
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }
}
