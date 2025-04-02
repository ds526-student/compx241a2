public class Appliance {
    private String category;
    private float price;
    private String name;

    public Appliance(String category, float price, String name) {
        this.category = category;
        this.price = price;
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return (int) price;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name + "\t|\t" + category + "\t|\t$" + String.format("%.2f", price);
    }

    public int compareTo(Appliance other) {
        // Compare by category first
        int categoryComparison = this.category.compareTo(other.category);
        if (categoryComparison != 0) {
            return categoryComparison;
        }

        // If categories are the same, compare by price
        if (this.price < other.price) {
            return -1;
        } else if (this.price > other.price) {
            return 1;
        }   

        // If both category and price are the same, compare by name
        return this.name.compareTo(other.name);
    }
}
