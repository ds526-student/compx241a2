public class Appliance {
    private String category;
    private float price;
    private String name;

    public Appliance(String name, String catergory, float price) {
        this.price = price;
        this.name = name;
        this.category = catergory;
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
        return String.format("%-10s | %-20s | %10s", category, name, "$" + String.format("%,.2f", price));
    }

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
