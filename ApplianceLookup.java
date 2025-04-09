import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * this class is used to create a BST of appliances based on a csv file
 * it allows the user to add, remove, search, and print appliances based on their category and price
 */
public class ApplianceLookup{
    public static void main(String[] args) {
        ApplianceBST aBST = new ApplianceBST(); // create a new BST
        String path = "appliances.csv"; // path to the csv file
        String line; // the current line being read from the file
        boolean isFirstLine = true; // used to skip the first line of the csv file

        // read the csv file and create a new appliance for each line
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

        Scanner scan = new Scanner(System.in); // create a new scanner to read user input
        String choice; // the user's response 
        boolean continueRunning = true; // used to control the while loop

        // the main menu loop, this continues until the user chooses to exit
        while (continueRunning)
        {
            // print the menu and get the user's choice
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

            // check the user's choice and call the appropriate method
            if (choice.equals("1")){ // search for an appliance
                Appliance a = createAppliance();

                System.out.println(a.toString()); 
                boolean found = aBST.search(a);
                System.out.println("Searching appliance: " + a.getName() + " = " + found);
            }
            else if (choice.equals("2")){ // add a new appliance
                Appliance a = createAppliance();

                if (aBST.search(a)){ // check if the appliance already exists, else add it to the BST
                    System.out.println("The item " + a.getName() + " already exists within the Binary Search Tree");
                }
                else {
                    aBST.insert(a);
                    aBST.print();
                    System.out.println("Appliance " + a.getName() + " has been added to the Binary Search Tree");
                }
            }
            else if (choice.equals("3")){ // remove an appliance
                Appliance a = createAppliance();

                if (aBST.search(a) == false){ // check if the appliance exists, else remove it from the BST
                    System.out.println("Appliance " + a.getName() + " does not exist within the Binary Search Tree");
                }
                else{
                    aBST.remove(a);
                    aBST.print();
                    System.out.println("Appliance " + a.getName() + " has been removed from the Binary Search Tree");
                }
            }
            else if (choice.equals("4")){ // search for all items in a given category
                System.out.println("What category would you like to print?");
                choice = scan.nextLine();

                aBST.printCategory(choice);
            }
            else if (choice.equals("5")){ // search a category within a price range
                // get the users choice for what type of range they would like to use
                System.out.println("Please enter a number based on how you would like to search the category");
                System.out.println("1. Minimum Price only");
                System.out.println("2. Maximum Price only");
                System.out.println("3. Both a Minimum and Maximum Price");
                choice = scan.nextLine();
                float minPrice = 0.0f; // minimum price
                float maxPrice = 0.0f; // maximum price

                System.out.println("What category would you like to print?");
                String category = scan.nextLine();

                // check the user's choice and call the appropriate method
                if (choice.equals("1")){ // minimum price only
                    System.out.println("Please enter your minimum price");
                    minPrice = floatError();

                    aBST.printCategoryAbovePrice(category, minPrice);
                }
                else if (choice.equals("2")){ // maximum price only
                    System.out.println("Please enter your maximum price");
                    maxPrice = floatError();

                    aBST.printCategoryBelowPrice(category, maxPrice);
                }
                else if (choice.equals("3")){ // both a minimum and maximum price
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
            else if (choice.equals("6")){ // close the program
                continueRunning = false;
            }
            else { // invalid input
                System.out.println("Invalid input please try again.");
            }
            System.out.println("Press Enter to continue: ");
            scan.nextLine();
        }
        
        scan.close();
    }
    
    /**
     * creates a new appliance based on user input
     * @return Appliance object
     */
    private static Appliance createAppliance(){

        Scanner scan = new Scanner(System.in); // create a new scanner to read user input
        String category; // the category of the appliance
        String name; // the name of the appliance
        float price; // the price of the appliance

        // create a new appliance based on user input
        System.out.println("What category is this item?");
        category = scan.nextLine();
        System.out.println("What is the name of the product?");
        name = scan.nextLine();
        System.out.println("How much is the Appliance?");
        price = floatError();

        return new Appliance(name, category, price);
    }

    /**
     * checks for valid input for a float value
     * @return float value
     */
    private static float floatError(){
        Scanner scan = new Scanner(System.in); // create a new scanner to read user input
        boolean validInput = false; // boolean to check if the input is valid
        float price = 0.0f; // the price of the appliance
        while (!validInput){ // loop until the user enters a valid input
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