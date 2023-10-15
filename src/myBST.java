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
        int count = 0;
        Node currentNode = root;

        while (currentNode != null) {
            int carMakeComparison = carMake.compareToIgnoreCase(currentNode.data.getCarMake());

            if (carMakeComparison == 0 && !currentNode.data.getDate().before(targetDate)) {
                count++;
            }

            if (carMakeComparison <= 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return count;
    }
}
