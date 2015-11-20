import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            "SELECT p.pid, p.pname, m.mname, c.cname, p.pAvailableQuantity, m.mWarrantyPeriod, p.pprice" +
            "FROM part p, manufacturer m, category c" +
            "WHERE p.mid=m.mid AND p.cid=c.cid AND ("+search_field+" LIKE ?)" +
            "ORDER BY p.pprice"+ order);

        keyword = "%" + keyword + "%";
        pstmt.setString(1,keyword);

        //PRINT QUERY
        ResultSet rs = pstmt.executeQuery();
        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
        while (rs.next()) {
            System.out.print("| ");
            for(int j=1; j<=7;j++)
            {
               System.out.print(rs.getString(j) + " | ");
            }
            System.out.println();
        }
        System.out.println("End of Query");
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
