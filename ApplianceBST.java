public class ApplianceBST {

    Node root; // the root of the BSTzz

    /**
     * constructor for the ApplianceBST class
     * initializes the root to null
     */
    public ApplianceBST() {
        root = null;
    }

    /**
     * insert appliance into BST
     * @param a the appliance to add
     */
    public void insert(Appliance a) {
        root = insertIntoSubtree(root, a);
    }

    /**
     * remove appliance from BST
     * @param a the appliance to remove
     */
    public void remove(Appliance a) {
        root = removeFromSubtree(root, a);
    }

    /**
     * search for an appliance in the BST
     * @param a the appliance to search for
     * @return true or false
     */
    public boolean search(Appliance a) {
        return searchInSubtree(root, a);
    }

    /**
     * print the BST
     */
    public void print() {
        StrBSTPrinter.printNode(root);
        printR(root);
    }

    /**
     * get the height of the BST
     * @return the height of the BST
     */
    public int getHeight() {
        return getHeightOfSubtree(root);
    }

    /**
     * print all items in a given category
     * @param category the category to search for
     */
    public void printCategory(String category) {
        System.out.println("Printing all items in category " + category);
        printCat(root, category);
    }

    /**
     * print all items in a given category within a price range
     * @param category the category to serach for
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     */
    public void printCategoryWithPricecRange(String category, float minPrice, float maxPrice) {
        System.out.println("Printing all items in category " + category + " within the price range $" + String.format("%,.2f", minPrice) + " -> $" + String.format("%,.2f", maxPrice));
        printCatWithPriceRange(root, category, minPrice, maxPrice);
    }

    /**
     * print all items in a given category above a certain price
     * @param category the category to search for
     * @param minPrice the minimum price
     */
    public void printCategoryAbovePrice(String category, float minPrice) {
        System.out.println("Printing all items in category " + category + " above the price $" + String.format("%,.2f", minPrice));
        printCatAbovePrice(root, category, minPrice);
    }

    /**
     * print all items in a given category below a certain price
     * @param category the category to search for
     * @param maxPrice the maximum price
     */
    public void printCategoryBelowPrice(String category, float maxPrice) {
        System.out.println("Printing all items in category " + category + " below the price $" + String.format("%,.2f", maxPrice));
        printCatBelowPrice(root, category, maxPrice);
    }

    /**
     * get the minimum appliance in the BST
     * @return the minimum appliance
     */
    public Appliance getMinimum() {
        Node minNode = getMinimum(root);
        if (minNode != null) {
            return minNode.value;
        } else {
            return null;
        }
    }

    /**
     * get the maximum appliance in the BST
     * @return the maximum appliance
     */
    public Appliance getMaximum() {
        Node maxNode = getMaximum(root);
        if (maxNode != null) {
            return maxNode.value;
        } else {
            return null;
        }
    }

    /**
     * insert an appliance into the BST
     * @param cRoot the current root of the subtree
     * @param a the appliance to add
     * @return the new root of the subtree
     */
    private Node insertIntoSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) { // if the tree is empty, create a new node. Else insert into the left or right subtree
            return new Node(a);
        } else if (a.compareTo(cRoot.value) < 0) {
            cRoot.left = insertIntoSubtree(cRoot.left, a);
        } else if (a.compareTo(cRoot.value) > 0) {
            cRoot.right = insertIntoSubtree(cRoot.right, a);
        }

        int balance = getBalanceFactor(cRoot);

        if (balance > 1) { // left heavy, check left subtree to rotate
            if (a.compareTo(cRoot.left.value) < 0) {
                return RotateRight(cRoot);
            } else {
                return RotateLeftRight(cRoot);
            }
        } else if (balance < -1) { // right heavy, check right subtree to rotate
            if (a.compareTo(cRoot.right.value) > 0) {
                return RotateLeft(cRoot);
            } else {
                return RotateRightLeft(cRoot);
            }
        }

        return cRoot;
    }
    
    /**
     * search for an appliance in the BST
     * @param cRoot the current root of the subtree
     * @param a the appliance to search for
     * @return true or false
     */
    private boolean searchInSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) return false; // if the tree is empty, or the end is reached, return false
        else if (a.compareTo(cRoot.value) == 0) return true; // if the appliance is found, return true
        else if (a.compareTo(cRoot.value) < 0) 
            return searchInSubtree(cRoot.left, a);
        else
            return searchInSubtree(cRoot.right, a);
    }

    /**
     * remove an appliance from the BST
     * @param cRoot the current root of the subtree
     * @param a the appliance to remove
     * @return the new root of the subtree
     */
    private Node removeFromSubtree(Node cRoot, Appliance a) {
        if (cRoot == null) return null; // if the tree is empty, return null

        if (a.compareTo(cRoot.value) < 0) { // depending on if the appliance is less than or greater than the current node, go left or right
            cRoot.left = removeFromSubtree(cRoot.left, a);
        } else if (a.compareTo(cRoot.value) > 0) {
            cRoot.right = removeFromSubtree(cRoot.right, a);
        } else { // if the appliance is found, remove it
            if (cRoot.left == null) return cRoot.right;
            else if (cRoot.right == null) return cRoot.left;
            else {
                Node minNode = getMinimum(cRoot.right);
                cRoot.value = minNode.value; 
                cRoot.right = removeFromSubtree(cRoot.right, minNode.value); 
            }
        }

        int balance = getBalanceFactor(cRoot);

        if (balance > 1) { // left heavy, check left subtree to rotate
            if (getBalanceFactor(cRoot.left) > 0) {
                return RotateRight(cRoot);
            } else {
                return RotateLeftRight(cRoot);
            }
        } else if (balance < -1) { // right heavy, check right subtree to rotate
            if (getBalanceFactor(cRoot.right) < 0) { 
                return RotateLeft(cRoot);
            } else {
                return RotateRightLeft(cRoot);
            }
        }

        return cRoot;
    }

    /**
     * get the height of the subtree
     * @param cRoot the current root of the subtree
     * @return the height of the subtree
     */
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

    /**
     * get the minimum appliance in the subtree
     * @param cRoot the current root of the subtree
     * @return the minimum appliance
     */
    private Node getMinimum(Node cRoot) {
        if (cRoot == null) return null;
        while (cRoot.left != null) {
            cRoot = cRoot.left;
        }

        return cRoot;
    }

    /**
     * get the maximum appliance in the subtree
     * @param cRoot the current root of the subtree
     * @return the maximum appliance
     */
    private Node getMaximum(Node cRoot) {
        if (cRoot == null) return null;
        while (cRoot.right != null) {
            cRoot = cRoot.right;
        }

        return cRoot;
    }

    /**
     * print the BST in order
     * @param cRoot the current root of the subtree
     */
    private void printR(Node cRoot) {
        if (cRoot == null) return;

        printR(cRoot.left);
        System.out.println(cRoot.value.toString());
        printR(cRoot.right);

    }

    /**
     * get the balance factor of the subtree
     * @param cRoot the current root of the subtree
     * @return the balance factor of the subtree
     */
    private int getBalanceFactor(Node cRoot) {
        if (cRoot == null) return 0;
        else
        {
            return getHeightOfSubtree(cRoot.left) - getHeightOfSubtree(cRoot.right);
        }
    }

    /**
     * rotate the subtree to the left
     * @param parent the parent node of the subtree
     * @return the new root of the subtree
     */
    private Node RotateLeft(Node parent) {
        Node child = parent.right;
        parent.right = child.left;
        child.left = parent;
        return child;
    }

    /**
     * rotate the subtree to the right
     * @param parent the parent node of the subtree
     * @return the new root of the subtree
     */
    private Node RotateRight(Node parent) {
        Node child = parent.left;
        parent.left = child.right;
        child.right = parent;
        return child;
    }

    /**
     * rotate the subtree to the left and then to the right
     * @param parent the parent node of the subtree
     * @return the new root of the subtree
     */
    private Node RotateLeftRight(Node parent) {
        parent.left = RotateLeft(parent.left);
        return RotateRight(parent);
    }

    /**
     * rotate the subtree to the right and then to the left
     * @param parent the parent node of the subtree
     * @return the new root of the subtree
     */
    private Node RotateRightLeft(Node parent) {
        parent.right = RotateRight(parent.right);
        return RotateLeft(parent);
    }

    /**
     * print all items in a given category
     * @param cRoot the current root of the subtree
     * @param c the category to search for
     */
    private void printCat(Node cRoot, String c) {
        if (cRoot == null) return; // if the tree is empty, return

        int compare = cRoot.value.getCategory().compareTo(c);

        if (compare == 0) { // if the category is found, print the appliance
            printCat(cRoot.left, c);
            System.out.println(cRoot.value.toString());
            printCat(cRoot.right, c);
        }
        else if (compare > 0) { // if the category is less than the current node, go left. Else go right
            printCat(cRoot.left, c);

        } else {
            printCat(cRoot.right, c);
        }
    }

    /**
     * print all items in a given category within a price range
     * @param cRoot the current root of the subtree
     * @param c the category to search for
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     */
    private void printCatWithPriceRange(Node cRoot, String c, float minPrice, float maxPrice) {
        if (cRoot == null) return; // if the tree is empty, return
        
        int compare = cRoot.value.getCategory().compareTo(c);

        if (compare == 0) { // if the category is found, print the appliance if the price is within the range
            printCatWithPriceRange(cRoot.left, c, minPrice, maxPrice);
            if (cRoot.value.getPrice() > minPrice && cRoot.value.getPrice() < maxPrice) {
                System.out.println(cRoot.value.toString());
            }
            printCatWithPriceRange(cRoot.right, c, minPrice, maxPrice);
        }
        else if (compare > 0) { // if the category is less than the current node, go left. Else go right
            printCatWithPriceRange(cRoot.left, c, minPrice, maxPrice);

        } else {
            printCatWithPriceRange(cRoot.right, c, minPrice, maxPrice);
        }
    }

    /**
     * print all items in a given category above a certain price
     * @param cRoot the current root of the subtree
     * @param c the category to search for
     * @param minPrice the minimum price
     */
    private void printCatAbovePrice(Node cRoot, String c, float minPrice){
        if (cRoot == null) return; // if the tree is empty, return
        
        int compare = cRoot.value.getCategory().compareTo(c);

        if (compare == 0) { // if the category is found, print the appliance if the price is above the minimum price
            printCatAbovePrice(cRoot.left, c, minPrice);
            if (cRoot.value.getPrice() > minPrice)
            {
                System.out.println(cRoot.value.toString());
            }
            printCatAbovePrice(cRoot.right, c, minPrice);
        }
        else if (compare > 0) { // if the category is less than the current node, go left. Else go right
            printCatAbovePrice(cRoot.left, c, minPrice);

        } else {
            printCatAbovePrice(cRoot.right, c, minPrice);
        }
    }

    /**
     * print all items in a given category below a certain price
     * @param cRoot the current root of the subtree
     * @param c the category to search for
     * @param maxPrice the maximum price
     */
    private void printCatBelowPrice(Node cRoot, String c, float maxPrice){
        if (cRoot == null) return; // if the tree is empty, return
        
        int compare = cRoot.value.getCategory().compareTo(c);

        if (compare == 0) { // if the category is found, print the appliance if the price is below the maximum price
            printCatBelowPrice(cRoot.left, c, maxPrice);
            if (cRoot.value.getPrice() < maxPrice)
            {
                System.out.println(cRoot.value.toString());
            }
            printCatBelowPrice(cRoot.right, c, maxPrice);
        }
        else if (compare > 0) { // if the category is less than the current node, go left. Else go right
            printCatBelowPrice(cRoot.left, c, maxPrice);

        } else {
            printCatBelowPrice(cRoot.right, c, maxPrice);
        }
    }
}
