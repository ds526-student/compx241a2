/**
 * node class representing each node in a binary search tree
 */
public class Node {
    public Appliance value;
    public Node left, right;

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
