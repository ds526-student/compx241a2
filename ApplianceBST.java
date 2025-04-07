public class ApplianceBST {

    Node root;

    public ApplianceBST() {
        root = null;
    }

    public void insert(Appliance a) {
        root = insertIntoSubtree(root, a);
    }

    public void remove(Appliance a) {
        root = removeFromSubtree(root, a);
    }

    public boolean search(Appliance a) {
        return searchInSubtree(root, a);
    }

    public void print() {
        StrBSTPrinter.printNode(root);
        printR(root);
    }

    public int getHeight() {
        return getHeightOfSubtree(root);
    }

    public void printCategory(String category) {
        printCat(category);
    }

    public Appliance getMinimum() {
        Node minNode = getMinimum(root);
        if (minNode != null) {
            return minNode.value;
        } else {
            return null;
        }
    }

    public Appliance getMaximum() {
        Node maxNode = getMaximum(root);
        if (maxNode != null) {
            return maxNode.value;
        } else {
            return null;
        }
    }

    private Node insertIntoSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) {
            return new Node(a);
        } else if (a.compareTo(cRoot.value) < 0) {
            cRoot.left = insertIntoSubtree(cRoot.left, a);
        } else if (a.compareTo(cRoot.value) > 0) {
            cRoot.right = insertIntoSubtree(cRoot.right, a);
        }

        int balance = getBalanceFactor(cRoot);

        // Perform rotations if the node is unbalanced
        if (balance > 1) {
            if (a.compareTo(cRoot.left.value) < 0) {
                return RotateRight(cRoot);
            } else if (a.compareTo(cRoot.left.value) > 0) {
                return RotateLeftRight(cRoot);
            }
        } else if (balance < -1) {
            if (a.compareTo(cRoot.right.value) > 0) {
                return RotateLeft(cRoot);
            } else if (a.compareTo(cRoot.right.value) < 0) {
                return RotateRightLeft(cRoot);
            }
        }

        return cRoot;
    }

    private boolean searchInSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) return false;
        else if(cRoot.value == a) return true;
        else if(a.compareTo(cRoot.value) < 0) return searchInSubtree(cRoot.left, a);
        else if(a.compareTo(cRoot.value) > 0) return searchInSubtree(cRoot.right, a);
        return false;
    }

    private Node removeFromSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) return null;

        if (a.compareTo(a) < 0) {
            cRoot.left = removeFromSubtree(cRoot.left, a);
        } else if (a.compareTo(cRoot.value) > 0) {
            cRoot.right = removeFromSubtree(cRoot.right, a);
        } else {
            if (cRoot.left == null) return cRoot.right;
            else if (cRoot.right == null) return cRoot.left;
            else {
                Node minNode = getMinimum(cRoot.right);
                cRoot.value = minNode.value;
                cRoot.right = removeFromSubtree(cRoot.right, minNode.value);
            }
        }

        int balance = getBalanceFactor(cRoot);
        if (balance > 1){
            if (a.compareTo(cRoot.left.value) < 0) {
                return RotateRight(cRoot);
            }
            else if (a.compareTo(cRoot.left.value) > 0) {
                return RotateLeftRight(cRoot);
            }
        }
        else if (balance < -1){
            if (a.compareTo(cRoot.right.value) < 0) {
                return RotateLeft(cRoot);
            }
            else if (a.compareTo(cRoot.right.value) > 0) {
                return RotateRightLeft(cRoot);
            }
        }

        return cRoot;
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
        if (cRoot == null) return null;
        while (cRoot.left != null) {
            cRoot = cRoot.left;
        }

        return cRoot;
    }

    private Node getMaximum(Node cRoot) {
        if (cRoot == null) return null;
        while (cRoot.right != null) {
            cRoot = cRoot.right;
        }

        return cRoot;
    }

    private void printR(Node cRoot) {
        if (cRoot == null) return;

        printR(cRoot.left);
        System.out.println(cRoot.value.toString());
        printR(cRoot.right);

    }

    private int getBalanceFactor(Node cRoot) {
        if (cRoot == null) return 0;
        else
        {
            return getHeightOfSubtree(cRoot.left) - getHeightOfSubtree(cRoot.right);
        }
    }

    private Node RotateLeft(Node parent) {
        Node child = parent.right;
        parent.right = child.left;
        child.left = parent;
        return child;
    }

    private Node RotateRight(Node parent) {
        Node child = parent.left;
        parent.left = child.right;
        child.right = parent;
        return child;
    }

    private Node RotateLeftRight(Node parent) {
        parent.left = RotateLeft(parent.left);
        return RotateRight(parent);
    }

    private Node RotateRightLeft(Node parent) {
        parent.right = RotateRight(parent.right);
        return RotateLeft(parent);
    }

    private void printCat(String c) {
        // These functions shouldn’t iterate over the whole tree and filter the results, but instead only search the relevant branches of the tree. Add the following functions to ApplianceBST.java:
        if (c.equals(root.value.getCategory())) {
            System.out.println(root.value.toString());
        }
    }
}
