// this class represents an appliance with a category, price, and name
public class Appliance {
    private String category;
    private float price;
    private String name;

    /**
     * constructor for the appliance class
     * @param name the name of the appliance
     * @param catergory the category of the appliance
     * @param price the price of the appliance
     */
    public Appliance(String name, String catergory, float price) {
        this.price = price;
        this.name = name;
        this.category = catergory;
    }

    /**
     * getter for the name of the appliance
     * @return the name of the appliance
     */
    public String getCategory() {
        return category;
    }

    /**
     * getter for the price of the appliance
     * @return the price of the appliance
     */
    public int getPrice() {
        return (int) price;
    }

    /**
     * getter for the name of the appliance
     * @return the name of the appliance
     */
    public String getName() {
        return name;
    }
    
    /**
     * toString method for the appliance class
     * @return a string representation of the appliance
     */
    @Override
    public String toString() {
        return String.format("%-18s | %-40s | %10s", category, name, "$" + String.format("%,.2f", price));
    }

    /**
     * a way to sort the appliances in the BST
     * @param other
     * @return
     */
    public int compareTo(Appliance other) {
        if (this.category.equals(other.category)) {
            if (this.price == other.price) {
                return this.name.compareTo(other.name);
            } else if (this.price < other.price) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return this.category.compareTo(other.category);
        }
    }
}
