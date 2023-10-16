import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Jere Perisic
 * @version October 13, 2023
 */
public class program5 {
    public static void main(String[] args) {
        myBST tree = new myBST();  // Create a Binary Search Tree
        String file = args[0];

        long timeToBuildTree = buildTreeFromCSV(tree, file);
        System.out.println(timeToBuildTree + " seconds to build the binary search tree");

        processUserInput(tree);
    }
    /**
     * Builds a Binary Search Tree from a CSV file.
     *
     * @param tree      The Binary Search Tree to build.
     * @param filePath  The path to the CSV file containing sales records.
     * @return The time taken to build the tree in seconds.
     */
    private static long buildTreeFromCSV(myBST tree, String filePath) {
        long startTime = System.currentTimeMillis();
        insertFromCSV(tree, filePath);
        long endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }
    /**
     * Processes user input for querying the Binary Search Tree.
     *
     * @param tree The Binary Search Tree to query.
     */
    private static void processUserInput(myBST tree) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter a car make and a date (yyyy-MM-dd):");
            System.out.print("Enter the car make (e.g., Nissan): ");
            String carMake = scanner.next();
            System.out.print("Enter the date (e.g., 2022-08-01): ");
            String dateString = scanner.next();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate;

            try {
                targetDate = sdf.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                continue;
            }

            processCarSales(tree, carMake, targetDate);
            System.out.println();
            System.out.print("Do you want to analyze another date and car make? (yes/no): ");
            String response = scanner.next().toLowerCase();

            if (!response.equals("yes")) {
                break;
            }
            System.out.println();
        }

        scanner.close();
    }
    /**
     * Processes car sales records based on user input.
     *
     * @param tree        The Binary Search Tree containing sales records.
     * @param carMake     The car make to search for.
     * @param targetDate  The target date to filter sales records.
     */
    private static void processCarSales(myBST tree, String carMake, Date targetDate) {
        long startTime1 = System.currentTimeMillis();
        int count = tree.calculateCarsSoldIterative(carMake, targetDate);
        long endTime1 = System.currentTimeMillis();

        double elapsedTimeInSeconds1 = (double)(endTime1 - startTime1) / 1000;
        System.out.println(elapsedTimeInSeconds1 + " seconds to calculate using children count fields");

        System.out.println(count + " sales records are available for " + carMake + " after the date " + targetDate);

        long startTime2 = System.currentTimeMillis();
        int recursiveCount = tree.calculateCarsSoldRecursively(carMake, targetDate);
        long endTime2 = System.currentTimeMillis();

        double elapsedTimeInSeconds2 = (double)(endTime2 - startTime2) / 1000;
        System.out.println(elapsedTimeInSeconds2 + " seconds to calculate using recursive method");

        System.out.println(recursiveCount + " sales records are available for " + carMake + " after the date " + targetDate);
    }



    /**
     * Inserts data from a CSV file into the Binary Search Tree.
     *
     * @param tree         The Binary Search Tree to insert data into.
     * @param csvFilePath  The path to the CSV file containing sales records.
     */    private static void insertFromCSV(myBST tree, String csvFilePath) {
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
