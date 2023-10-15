import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class program5 {
    public static void main(String[] args) {
        myBST tree = new myBST();  // Create a Binary Search Tree

        // Load data from CSV file into the tree
        long startTreeBuild = System.currentTimeMillis();
        insertFromCSV(tree, "car_sales_data.csv");  // Replace "sales_data.csv" with your actual CSV file path
        long endTreeBuild = System.currentTimeMillis();

        System.out.println((double)(endTreeBuild-startTreeBuild)/1000+" seconds to build the binary search trees");
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a car make and a date (yyyy-MM-dd):");
            System.out.print("Car Make: ");
            String carMake = scanner.next();
            System.out.print("Date (yyyy-MM-dd): ");
            String dateString = scanner.next();

            // Parse the user-entered date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate;
            try {
                targetDate = sdf.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                continue;
            }

            // Calculate the number of cars sold on and after the specified date
            long start = System.currentTimeMillis();
            int count = tree.calculateCarsSoldOnAndAfterDate(carMake, targetDate);
            long end = System.currentTimeMillis();
            System.out.println((double)(end-start)/1000+" seconds to calculate using children count fields");
            System.out.println("Number of cars sold by " + carMake + " on and after " + dateString + ": " + count);

            int recursiveCount = tree.calculateCarsSoldOnAndAfterDateRecursive(carMake,targetDate);
            System.out.println("Recursive number of cars sold by " + carMake + " on and after " + dateString + ": " + recursiveCount);
            System.out.println((double) (end-start)/1000+ " seconds to calculate using recursive method");

            System.out.print("Do you want to analyze another date and car make? (yes/no): ");
            String response = scanner.next().toLowerCase();
            if (!response.equals("yes")) {
                break;
            }
        }

        scanner.close();
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
