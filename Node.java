/**
 * node class representing each node in a binary search tree
 */
public class Node {
    public Appliance value; // the appliance value stored in the node
    public Node left, right; // pointers to the left and right child nodes

    /**
     * constructor to create a new node with the given appliance value
     * @param value The appliance value to be stored in the node
     */
    public Node(Appliance value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
