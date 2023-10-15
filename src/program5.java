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
        insertFromCSV(tree, "small_sample.csv");  // Replace "sales_data.csv" with your actual CSV file path

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
            int count = tree.countCarsSoldOnOrAfterDate(carMake, targetDate);
            System.out.println("Number of cars sold by " + carMake + " on and after " + dateString + ": " + count);

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
    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    private static int calculateCarSales(String[] carMakes, String[] saleDates, String targetCarMake, Date targetDate) {
        int count = 0;
        for (int i = 0; i < carMakes.length; i++) {
            if (carMakes[i].equalsIgnoreCase(targetCarMake)) {
                Date saleDate = parseDate(saleDates[i]);
                if (saleDate != null && !saleDate.before(targetDate)) {
                    count++;
                }
            }
        }
        return count;
    }

}
