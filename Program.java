
import java.util.List;

public class Program {
    public static void main(String[] args) {
        ApplianceBST aBST = new ApplianceBST();
        Appliance a3 = new Appliance("Stander Freezest", "Fridge", 10430.20f);
        Appliance a1 = new Appliance("Heat pump", "Aircon", 1030.20f);
        Appliance a4 = new Appliance("Candle", "Oven", 100.20f);
        Appliance a2 = new Appliance("Stand Freezer", "Fridge", 1000.20f);
        Appliance a5 = new Appliance("Gas Stove", "Oven", 10200.20f);
        Appliance a6 = new Appliance("Blender", "Kitchen", 1000.20f);
        Appliance a7 = new Appliance("Microwave", "Kitchen", 1000.20f); 
        Appliance a8 = new Appliance("Dishwasher", "Kitchen", 1000.20f);

        List<Appliance> appliances = List.of(a1, a2, a3, a4, a5, a6, a7, a8);

        System.out.println("Running BST operations on appliances: ");

        System.out.println(a1.getCategory());
        System.out.println(a1.getName());
        System.out.println(a1.getPrice());
        System.out.println(a1.toString());

        aBST.remove(a1);
        aBST.search(a1);
        aBST.getHeight();
        aBST.getMinimum();
        aBST.getMaximum();
        aBST.print();

        System.out.println("-----------------");
        System.out.println("Inserting appliances into the BST: ");

        for (Appliance appliance : appliances) {
            aBST.insert(appliance);
        }

        aBST.print();

        System.out.println("-----------------");
        System.out.println("Running BST operations on appliances: ");

        aBST.remove(a1);
        aBST.search(a1);
        aBST.getHeight();
        aBST.getMinimum();
        aBST.getMaximum();
        aBST.print();
    }


    

    


    
}
