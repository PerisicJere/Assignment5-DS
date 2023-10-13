import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program5 {

    public static void main(String[] args) {
        myBST tree = new myBST();
        String csvFilePath = "small_sample.csv"; // Replace with your CSV file path

        insertFromCSV(tree, csvFilePath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Calculate the number of cars sold on and after a specific date");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleUserInputAndCalculate(tree);
                    break;
                case 2:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleUserInputAndCalculate(myBST tree) {
        Scanner scanner = new Scanner(System.in);
        String carMake = getUserInput("Enter Car Make: ", scanner);
        String dateStr = getUserInput("Enter Date (yyyy-MM-dd): ", scanner);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate = sdf.parse(dateStr);

            int carsSold = countCarsSoldOnAndAfterDate(tree.root, carMake, targetDate);
            System.out.println("Number of cars sold by " + carMake + " on and after " + dateStr + ": " + carsSold);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
        }
    }

    private static String getUserInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static int countCarsSoldOnAndAfterDate(myBST.Node node, String carMake, Date targetDate) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        if (node.data.getDate().compareTo(targetDate) >= 0 && node.data.getCarMake().equals(carMake)) {
            count++;
        }

        count += countCarsSoldOnAndAfterDate(node.left, carMake, targetDate);
        count += countCarsSoldOnAndAfterDate(node.right, carMake, targetDate);

        return count;
    }

    private static void insertFromCSV(myBST tree, String csvFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 9) {
                    continue;
                }
                String dateStr = parts[0];
                String salesperson = parts[1];
                String customerName = parts[2];
                String carMake = parts[3];
                String carModel = parts[4];
                int carYear = Integer.parseInt(parts[5]);
                double salePrice = Double.parseDouble(parts[6]);
                double commissionRate = Double.parseDouble(parts[7]);
                double commissionEarned = Double.parseDouble(parts[8]);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateStr);

                SaleRecord saleRecord = new SaleRecord(date, salesperson, customerName, carMake, carModel, carYear, salePrice, commissionRate, commissionEarned);
                tree.insert(saleRecord);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
