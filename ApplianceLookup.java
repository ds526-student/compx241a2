//create a "menu" allowing the user to select what they want to do with inputs 1->X
//menu idea
// 1->X for everything but have min and/or max price in a seperate menu after 
//add error checking 

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
        String choice;
        boolean continueRunning = true;

        while (continueRunning)
        {
            System.out.println("\n---------------------------------------\n");
            System.out.println("Please enter a number based on what you would like to do?");
            System.out.println("1. Search for an Appliance");
            System.out.println("2. Add a new Appliance");
            System.out.println("3. Remove an Appliance");
            System.out.println("4. Search for all items in a given category");
            System.out.println("5. Search a category within a price range");
            System.out.println("6. Close the program");
            choice = scan.nextLine();
            System.out.println("\n---------------------------------------\n");


            if (choice.equals("1")){
                Appliance a = createAppliance();

                System.out.println(a.toString()); 
                boolean found = aBST.search(a);
                System.out.println("Searching appliance: " + a.getName() + " = " + found);
            }
            else if (choice.equals("2")){
                Appliance a = createAppliance();

                if (aBST.search(a)){
                    System.out.println("The item " + a.getName() + " already exists within the Binary Search Tree");
                }
                else {
                    aBST.insert(a);
                    aBST.print();
                    System.out.println("Appliance " + a.getName() + " has been added to the Binary Search Tree");
                }
            }
            else if (choice.equals("3")){
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
            else if (choice.equals("4")){
                System.out.println("What category would you like to print?");
                choice = scan.nextLine();

                aBST.printCategory(choice);
            }
            else if (choice.equals("5")){
                System.out.println("Please enter a number based on how you would like to search the category");
                System.out.println("1. Minimum Price only");
                System.out.println("2. Maximum Price only");
                System.out.println("3. Both a Minimum and Maximum Price");
                choice = scan.nextLine();
                float minPrice = 0.0f;
                float maxPrice = 0.0f;

                System.out.println("What category would you like to print?");
                String category = scan.nextLine();

                if (choice.equals("1")){
                    System.out.println("Please enter your minimum price");
                    minPrice = floatError();

                    aBST.printCategoryAbovePrice(category, minPrice);
                }
                else if (choice.equals("2")){
                    System.out.println("Please enter your maximum price");
                    maxPrice = floatError();

                    aBST.printCategoryBelowPrice(category, maxPrice);
                }
                else if (choice.equals("3")){
                    boolean validPrices = false;
                    
                    while (!validPrices) {
                        System.out.println("Please enter your minimum price");
                        minPrice = floatError();
        
                        System.out.println("Please enter your maximum price");
                        maxPrice = floatError();

                        if (minPrice < maxPrice) {
                            validPrices = true;
                        }
                        else {
                            System.out.println("Please ensure that your minimum price is set lower than your maximum price");
                        }
                    }

                    aBST.printCategoryWithPricecRange(category, minPrice, maxPrice);
                }
                else {
                    System.out.println("Invalid input please try again");
                }
            }
            else if (choice.equals("6")){
                continueRunning = false;
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            System.out.println("Press Enter to continue: ");
            scan.nextLine();
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
        System.out.println("How much is the Appliance?");
        price = floatError();

        return new Appliance(name, category, price);
    }

    private static float floatError(){
        Scanner scan = new Scanner(System.in);
        boolean validInput = false;
        float price = 0.0f;
        while (!validInput){
            try {
                price = Float.parseFloat(scan.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please try again.");
                } else {
                    validInput = true; 
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return price;
    }
}