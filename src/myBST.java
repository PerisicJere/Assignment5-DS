import java.util.Date;
import java.util.Stack;
/**
 * A Binary Search Tree (BST) for storing and searching sale records.
 */
public class myBST {
    public Node root;

    private class Node {
        SaleRecord data;
        Node left;
        Node right;
        int leftChildCount;
        int rightChildCount;
        /**
         * Constructs a new Node with sale record data.
         *
         * @param data The sale record data to store in the node.
         */
        public Node(SaleRecord data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.leftChildCount = 0;
            this.rightChildCount = 0;
        }
    }
    /**
     * Inserts a sale record into the BST.
     *
     * @param data The sale record to insert.
     */
    public void insert(SaleRecord data) {
        root = insert(root, data);
    }

    private Node insert(Node node, SaleRecord data) {
        if (node == null) {
            return new Node(data);
        }

        if (data.getDate().compareTo(node.data.getDate()) < 0) {
            node.leftChildCount++;
            node.left = insert(node.left, data);
        } else {
            node.rightChildCount++;
            node.right = insert(node.right, data);
        }

        return node;
    }
    /**
     * Calculates the number of cars sold iteratively for a specific car make and date.
     *
     * @param carMake The car make to search for.
     * @param date    The target date to filter sales records.
     * @return The count of cars sold that match the criteria.
     */
    public int calculateCarsSoldIterative(String carMake, Date date) {
        return calculateCarsSold(root, carMake, date);
    }

    private int calculateCarsSold(Node node, String carMake, Date date) {
        int count = 0;
        Stack<Node> stack = new Stack<>();

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            int carMakeComparison = carMake.compareTo(node.data.getCarMake());
            int dateComparison = date.compareTo(node.data.getDate());

            if (carMakeComparison == 0 && dateComparison <= 0) {
                count++;
            }

            node = node.right;
        }

        return count;
    }
    /**
     * Calculates the number of cars sold recursively for a specific car make and start date.
     *
     * @param carMake   The car make to search for.
     * @param startDate The start date to filter sales records.
     * @return The count of cars sold that match the criteria.
     */
    public int calculateCarsSoldRecursively(String carMake, Date startDate) {
        return calculateCarsSoldRecursivelyHelper(root, startDate, carMake);
    }

    private int calculateCarsSoldRecursivelyHelper(Node node, Date startDate, String carMake) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        if (node.data.getDate().compareTo(startDate) >= 0 && node.data.getCarMake().equals(carMake)) {
            count++;
        }

        if (node.data.getDate().compareTo(startDate) >= 0) {
            count += calculateCarsSoldRecursivelyHelper(node.left, startDate, carMake);
        }

        count += calculateCarsSoldRecursivelyHelper(node.right, startDate, carMake);

        return count;
    }

}




