import java.util.Date;
import java.util.Stack;

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

    public int calculateCarsSoldOnAndAfterDate(String carMake, Date date) {
        return calculateCarsSoldOnAndAfterDate(root, carMake, date);
    }

    private int calculateCarsSoldOnAndAfterDate(Node node, String carMake, Date date) {
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

    public int calculateCarsSoldOnAndAfterDateRecursive(String carMake, Date date) {
        return calculateCarsSoldOnAndAfterDateRecursive(root, carMake, date);
    }

    private int calculateCarsSoldOnAndAfterDateRecursive(Node node, String carMake, Date date) {
        if (node == null) {
            return 0;
        }

        int carMakeComparison = carMake.compareTo(node.data.getCarMake());
        int dateComparison = date.compareTo(node.data.getDate());

        int count = 0;

        if (dateComparison <= 0) {
            if (carMakeComparison == 0) {
                count++;
            }
            count += calculateCarsSoldOnAndAfterDateRecursive(node.left, carMake, date);
        }

        if (carMakeComparison <= 0) {
            count += calculateCarsSoldOnAndAfterDateRecursive(node.left, carMake, date);
        }

        if (carMakeComparison >= 0) {
            count += calculateCarsSoldOnAndAfterDateRecursive(node.right, carMake, date);
        }

        return count;
    }

}
