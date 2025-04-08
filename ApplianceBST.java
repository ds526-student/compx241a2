
import java.util.ArrayList;

//problems!!!!!!
//search for category continues to search after it finds all relevvant items (BIG MAYBE AS THE BST ORDER MAY FUCK THIS)
//search for category doesn't print in price descending order, could use bubble sort or attempt to use the appliance compare to method



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
        System.out.println("Printing all items in category " + category);
        printCat(root, category);
    }

    public void printCategoryWithPricecRange(String category, float minPrice, float maxPrice) {
        System.out.println("Printing all items in category " + category + " within the price range $" + String.format("%,.2f", minPrice) + " -> $" + String.format("%,.2f", maxPrice));
        printCatWithPriceRange(root, category, minPrice, maxPrice);
    }

    public void printCategoryAbovePrice(String category, float minPrice) {
        System.out.println("Printing all items in category " + category + " above the price $" + String.format("%,.2f", minPrice));
        printCatAbovePrice(root, category, minPrice);
    }

    public void printCategoryBelowPrice(String category, float maxPrice) {
        System.out.println("Printing all items in category " + category + " below the price $" + String.format("%,.2f", maxPrice));
        printCatBelowPrice(root, category, maxPrice);
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
        else if (a.compareTo(cRoot.value) == 0) return true;
        else if (a.compareTo(cRoot.value) < 0) 
            return searchInSubtree(cRoot.left, a);
        else
            return searchInSubtree(cRoot.right, a);
    }

    private Node removeFromSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) return null;

        if (a.compareTo(cRoot.value) < 0) { // Fixed incorrect comparison
            cRoot.left = removeFromSubtree(cRoot.left, a);
        } else if (a.compareTo(cRoot.value) > 0) {
            cRoot.right = removeFromSubtree(cRoot.right, a);
        } else {
            if (cRoot.left == null) return cRoot.right;
            else if (cRoot.right == null) return cRoot.left;
            else {
                Node minNode = getMinimum(cRoot.right);
                cRoot.value = minNode.value; // Replace value with the minimum from the right subtree
                cRoot.right = removeFromSubtree(cRoot.right, minNode.value); // Remove the duplicate node
            }
        }

        int balance = getBalanceFactor(cRoot);
        if (balance > 1) {
            if (getBalanceFactor(cRoot.left) >= 0) { // Adjusted to check balance of left child
                return RotateRight(cRoot);
            } else {
                return RotateLeftRight(cRoot);
            }
        } else if (balance < -1) {
            if (getBalanceFactor(cRoot.right) <= 0) { // Adjusted to check balance of right child
                return RotateLeft(cRoot);
            } else {
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

    private void printCat(Node cRoot, String c) {
        if (cRoot == null) return;

        int compare = cRoot.value.getCategory().compareTo(c);
        ArrayList<Appliance> appliances = new ArrayList<>();

        if (compare == 0) {
            printCat(cRoot.left, c);
            printCat(cRoot.right, c);
            appliances.add(cRoot.value);
            bubbleSort(appliances.toArray(new Appliance[0]));
            for (Appliance appliance : appliances) {
                System.out.println(appliance.toString());
            }
        }
        else if (compare > 0) {
            printCat(cRoot.left, c);

        } else {
            printCat(cRoot.right, c);
        }
    }

    private void printCatWithPriceRange(Node cRoot, String c, float minPrice, float maxPrice) {
        if (cRoot == null) return;
        
        int compare = cRoot.value.getCategory().compareTo(c);
        ArrayList<Appliance> appliances = new ArrayList<>();

        if (compare == 0) {
            printCatWithPriceRange(cRoot.left, c, minPrice, maxPrice);
            printCatWithPriceRange(cRoot.right, c, minPrice, maxPrice);
            if (cRoot.value.getPrice() > minPrice && cRoot.value.getPrice() < maxPrice) {
                appliances.add(cRoot.value);
                bubbleSort(appliances.toArray(new Appliance[0]));
                for (Appliance appliance : appliances) {
                    System.out.println(appliance.toString());
                }
            }
        }
        else if (compare > 0) {
            printCatWithPriceRange(cRoot.left, c, minPrice, maxPrice);

        } else {
            printCatWithPriceRange(cRoot.right, c, minPrice, maxPrice);
        }
    }

    private void printCatAbovePrice(Node cRoot, String c, float minPrice){
        if (cRoot == null) return;
        
        int compare = cRoot.value.getCategory().compareTo(c);
        ArrayList<Appliance> appliances = new ArrayList<>();

        if (compare == 0) {
            printCatAbovePrice(cRoot.left, c, minPrice);
            printCatAbovePrice(cRoot.right, c, minPrice);
            if (cRoot.value.getPrice() > minPrice)
            {
                appliances.add(cRoot.value);
                bubbleSort(appliances.toArray(new Appliance[0]));
                for (Appliance appliance : appliances) {
                    System.out.println(appliance.toString());
                }
            }
        }
        else if (compare > 0) {
            printCatAbovePrice(cRoot.left, c, minPrice);

        } else {
            printCatAbovePrice(cRoot.right, c, minPrice);
        }
    }

    private void printCatBelowPrice(Node cRoot, String c, float maxPrice){
        if (cRoot == null) return;
        
        int compare = cRoot.value.getCategory().compareTo(c);
        ArrayList<Appliance> appliances = new ArrayList<>();

        if (compare == 0) {
            printCatBelowPrice(cRoot.left, c, maxPrice);
            printCatBelowPrice(cRoot.right, c, maxPrice);
            if (cRoot.value.getPrice() < maxPrice)
            {
                appliances.add(cRoot.value);
                bubbleSort(appliances.toArray(new Appliance[0]));
                for (Appliance appliance : appliances) {
                    System.out.println(appliance.toString());
                }
            }
        }
        else if (compare > 0) {
            printCatBelowPrice(cRoot.left, c, maxPrice);

        } else {
            printCatBelowPrice(cRoot.right, c, maxPrice);
        }
    }

    private void bubbleSort(Appliance[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (arr[j].getPrice() < arr[j + 1].getPrice()) {
                    Appliance temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
