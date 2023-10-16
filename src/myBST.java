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

    public int calculateCarsSoldRecursively(String carMake, Date startDate) {
        return calculateCarsSoldRecursivelyHelperr(root, startDate, carMake);
    }

    private int calculateCarsSoldRecursivelyHelperr(Node node, Date startDate, String carMake) {
        if (node == null) {
            return 0;
        }

        int count = 0;

        if (node.data.getDate().compareTo(startDate) >= 0 && node.data.getCarMake().equals(carMake)) {
            count++;
        }

        if (node.data.getDate().compareTo(startDate) >= 0) {
            count += calculateCarsSoldRecursivelyHelperr(node.left, startDate, carMake);
        }

        count += calculateCarsSoldRecursivelyHelperr(node.right, startDate, carMake);

        return count;
    }

}




