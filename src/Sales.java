import java.util.Scanner;

/**
 * Created by terence on 11/20/15.
 */
public class Sales {

    private static void search_part()
    {
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
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }

    public static void menu()
    {
        Scanner scanner = new Scanner(System.in);
        boolean i = true;

        while(i) {
            System.out.print(
                    "----Operation for salesperson menu----\n" +
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
