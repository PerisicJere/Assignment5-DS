import java.util.Date;

public class myBST {
    public Node root;

    private class Node {
        SaleRecord data;
        Node left;
        Node right;
        int leftChildCount;
        int rightChildCount;

        public Node(SaleRecord data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.leftChildCount = 0;
            this.rightChildCount = 0;
        }
    }

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
    public int countCarsSoldOnOrAfterDate(String carMake, Date targetDate) {
        return countCarsSoldOnOrAfterDate(root, carMake, targetDate);
    }

    private int countCarsSoldOnOrAfterDate(Node node, String carMake, Date targetDate) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        // Check if the current node's data matches the criteria
        if (node.data.getCarMake().equalsIgnoreCase(carMake) && !node.data.getDate().before(targetDate)) {
            count++;
        }

        // Recursively search the left and right subtrees
        count += countCarsSoldOnOrAfterDate(node.left, carMake, targetDate);
        count += countCarsSoldOnOrAfterDate(node.right, carMake, targetDate);

        return count;
    }



}
