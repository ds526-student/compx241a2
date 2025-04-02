public class ApplianceBST {

    Node root;

    public ApplianceBST() {
        root = null;
    }
    
    private static class Node {
        Appliance appliance;
        Node left, right;

        Node(Appliance appliance) {
            this.appliance = appliance;
            this.left = null;
            this.right = null;
        }
    }
    
    public void insert(Appliance a) {
        root = insertIntoSubtree(root, a);
    }

    public void remove(Appliance a) {
        root = removeFromSubtree(root, a);
    }

    public void search(Appliance a) {
        Node result = searchInSubtree(root, a);
        if (result != null) {
            System.out.println("Appliance found: " + result.appliance);
        } else {
            System.out.println("Appliance not found.");
        }
    }

    public void print() {
        printR(root);
    }

    public int getHeight() {
        return getHeightOfSubtree(root);
    }

    private Node insertIntoSubtree(Node node, Appliance a) {
        if (node == null) {
            return new Node(a);
        }

        if (a.compareTo(node.appliance) < 0) {
            node.left = insertIntoSubtree(node.left, a);
        } else if (a.compareTo(node.appliance) > 0) {
            node.right = insertIntoSubtree(node.right, a);
        } else {
            // Duplicate value; do nothing or handle as needed
        }

        return node;
    }

    private Node removeFromSubtree(Node node, Appliance a) {
        if (node == null) {
            return null;
        }

        if (a.compareTo(node.appliance) < 0) {
            node.left = removeFromSubtree(node.left, a);
        } else if (a.compareTo(node.appliance) > 0) {
            node.right = removeFromSubtree(node.right, a);
        } else {
            // Node to be removed found
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // Node with two children: find the in-order successor
                Node successor = getMinimum(node.right);
                node.appliance = successor.appliance;
                node.right = removeFromSubtree(node.right, successor.appliance);
            }
        }

        return node;
    }

    private int getHeightOfSubtree(Node cRoot) {
        if (cRoot == null) return 0;

        int leftHeight = getHeightOfSubtree(cRoot.left);
        int rightHeight = getHeightOfSubtree(cRoot.right);

        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }
    
    private Node getMinimum(Node cRoot) {
        while (cRoot.left != null) {
            cRoot = cRoot.left;
        }
        return cRoot;
    }

    private Node getMaximum(Node cRoot) {
        while (cRoot.right != null) {
            cRoot = cRoot.right;
        }
        return cRoot;
    }

    private void printR(Node node) {
        if (node != null) {
            printR(node.left);
            System.out.println(node.appliance);
            printR(node.right);
        }
    }

    private Node searchInSubtree(Node node, Appliance a) {
        if (node == null || node.appliance.equals(a)) {
            return node;
        }

        if (a.compareTo(node.appliance) < 0) {
            return searchInSubtree(node.left, a);
        } else {
            return searchInSubtree(node.right, a);
        }

}
