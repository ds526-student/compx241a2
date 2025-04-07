//create a "menu" allowing the user to select what they want to do with inputs 1->X

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplianceLookup{
    public static void main(String[] args) {
        ArrayList<ApplianceBST> appliances = new ArrayList<ApplianceBST>();
        ApplianceBST aBST = new ApplianceBST();
        String path = "appliances.csv";
        String line;
        boolean isFirstLine = true;

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            while ((line = br.readLine()) != null) { 
                if (isFirstLine) {
                    isFirstLine = false;
                }
                else{
                    String[] values = line.split("\n");
                    for (String value : values) {
                        String[] newValue = value.split(",");
                        Appliance a = new Appliance(newValue[0], newValue[1], Float.parseFloat(newValue[2]));
                        aBST.insert(a);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        aBST.print();

        Scanner scan = new Scanner(System.in);
        String response;

        System.out.println("\n---------------------------------------\n");
        System.out.println("Would you like to search for a specific appliance? (y/n)");
        response = scan.nextLine();
        if (response.toLowerCase().equals("y")){
            Appliance a = createAppliance();

            System.out.println(a.toString()); 
            boolean found = aBST.search(a);
            System.out.println("Searching appliance: " + a.getName() + " = " + found);
        }
        
        System.out.println("\n---------------------------------------\n");
        System.out.println("Would you like add a new appliance? (y/n)");
        response = scan.nextLine();
        if (response.toLowerCase().equals("y")){
            Appliance a = createAppliance();

            aBST.insert(a);
            aBST.print();
            System.out.println("Appliance " + a.getName() + " has been added to the Binary Search Tree");
        }
        
        System.out.println("\n---------------------------------------\n");
        System.out.println("Would you like remove an appliance? (y/n)");
        response = scan.nextLine();
        if (response.toLowerCase().equals("y")){
            Appliance a = createAppliance();

            if (aBST.search(a) == false){
                System.out.println("Appliance " + a.getName() + " does not exist within the Binary Search Tree");
            }
            else{
                aBST.remove(a);
                aBST.print();
                System.out.println("Appliance " + a.getName() + " has been removed from the Binary Search Tree");
            }
        }

        
        System.out.println("\n---------------------------------------\n");
        System.out.println("Would you like to print all items of a given category? (y/n)");
        response = scan.nextLine();
        if (response.toLowerCase().equals("y")){
            System.out.println("What category would you like to print?");
            response = scan.nextLine();

            aBST.printCategory(response);
        }

        
        scan.close();
    }
    

    private static Appliance createAppliance(){

        Scanner scan = new Scanner(System.in);
        String category;
        String name;
        float price;

        System.out.println("What category is this item?");
        category = scan.nextLine();
        System.out.println("What is the name of the product?");
        name = scan.nextLine();
        System.out.println("How much is it?");
        price = scan.nextFloat();
        scan.close();
        return new Appliance(name, category, price);
    }
}