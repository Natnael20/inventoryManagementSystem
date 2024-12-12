/**
 * 
 */
package inventoryManagementSystem;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * An Inventory Management System (IMS) is a software 
 * application that helps businesses track and manage 
 * their inventory. It can be used to track a variety
 *  of items, including products, raw materials, and 
 *  finished goods.
 *  1. Add new item  
 *  2. Update item quantity  
 *	3. Search item by name  
 *  4. Print inventory list (sorted by item name)  
 *  5. Print inventory list (sorted by quantity)  
 *	q. End program  
 * @author XIX
 */
public class Main {
	
	//global variable
	private static Scanner scanner = new Scanner(System.in);
	
	//non-numeric constants
	public static final String ITEM_NAME = "> Enter the name of the item: ";
	public static final String ITEM_QUANTITY = "> Enter the quantity of the item: ";
	public static final String ITEM_PRICE = "> Enter the price of the item: ";
	public static final String UPDATED_QUANTITY = "> Enter the update quantity of the Item: ";
	public static final String NON_NUMERICAL = "Invalid input, non-numerical is disallowed!";
	public static final String ITEM_ALREADY_EXISTS = "Item with this name already exists. Please enter a unique name.";
	public static final String POSITIVE_QUANTITY = "Quantity must be a positive number.";
	public static final String ITEM_NOT_FOUND = "The specified item does not exist in the inventory.";
	public static final String EMPTY_INVENTORY = "No items in the inventory to display.";
	public static final String INVALID_OPTION = "Invalid menu option. Please try again.";
	public static final String EXIT_PROGRAM = "Exiting the program..........";
	
	//numeric constants
	public static final int MAX_VALUE = 10;
	
	
	public static void main(String[] args) {
		//local variables
		String[][] name = new String[MAX_VALUE][MAX_VALUE];
		int[][] quantity = new int[MAX_VALUE][MAX_VALUE];
		double[][] price = new double[MAX_VALUE][MAX_VALUE];
		int[] saveData = new int[MAX_VALUE];
		
		boolean programOn = true;
		

		while(programOn) {
			menu();
			String option = scanner.nextLine();
			switch(option) {
				case "1" :
					addItem(name,quantity,price,saveData);
					break;
				case "2":
		            updateItemQuantity(name, quantity, saveData);
		            break;
		        case "3":
		            searchItemByName(name, quantity, price, saveData);
		            break;
		        case "4":
		            printInventorySortedByName(name, quantity, price, saveData);
		            break;
		        case "5":
		            printInventorySortedByQuantity(name, quantity, price, saveData);
		            break;
		        case "q" :
		        	System.out.println(EXIT_PROGRAM);
		        	break;
				default :
					System.out.println(INVALID_OPTION);
					break;
			}
		}
		
	}
	
	/**
	 * a simple menu that helps the user to choose from
	 */
	public static void menu() {
		System.out.println("----------------------------------");
		System.out.println("# Inventory Management System");
		System.out.println("----------------------------------");
		System.out.println("1. Add new item");
		System.out.println("2. Update item quantity");
		System.out.println("3. Search item by name");
		System.out.println("4. Print inventory list (sorted by item name)");
		System.out.println("5. Print inventory list (sorted by quantity) ");
		System.out.println("q. End program");
		System.out.print("> Enter your option: ");		
	}
	
	/**
	 * the method will function as adding item for the inventory¨
	 * Requirement/validation
	 * 1. Item Name:
	 * Must not be empty or null.
	 * Must be unique (no duplicate names allowed).
	 * 2. Quantity:
	 * Must be a positive integer.
	 * Reject non-numeric or negative values.
	 * @param name, name parameter is the names of the item
	 * @param quantity, item parameter is the quantity of the item
	 * @param price, price parameter is the price of the item
	 * @param saveData, savedata parameter is used to save data in respective 2d array
	 */
	public static void addItem(String[][] name, int[][] quantity, double[][] price, int[] saveData) {
	    System.out.print("> Enter the name of the item: ");
	    String itemName = scanner.nextLine();

	    // Validation: Item name must not be empty
	    while (itemName.isEmpty()) {
	        System.out.println("Item name cannot be empty, try again!");
	        System.out.print("> Enter the name of the item: ");
	        itemName = scanner.nextLine().trim();  // Re-reading input
	    }

	    // Validation: Name must be unique
	    for (int i = 0; i < saveData[0]; i++) {
	        if (name[0][i] != null && name[0][i].equalsIgnoreCase(itemName)) {
	            System.out.println(ITEM_ALREADY_EXISTS);
	            return;  // Exit if duplicate found
	        }
	    }

	    // Handle quantity input
	    int itemQuantity = 0;
	    while (true) {
	        System.out.print(ITEM_QUANTITY);
	        try {
	        	itemQuantity = scanner.nextInt();
	            if (itemQuantity < 0) {
	                System.out.println(POSITIVE_QUANTITY);
	                continue;  // Keep asking for valid input
	            }
	            break;  // Exit loop if valid input
	        } catch (InputMismatchException e) {
	            System.out.println(NON_NUMERICAL);
	            scanner.nextLine(); // Clear the buffer after invalid input
	        }
	    }

	    // Handle price input
	    double itemPrice = 0.0;
	    while (true) {
	        System.out.print(ITEM_PRICE);
	        try {
	            itemPrice = scanner.nextDouble();
	            break;  // Exit loop if valid input
	        } catch (InputMismatchException e) {
	            System.out.println(NON_NUMERICAL);
	            scanner.nextLine();
	        }
	    }
	    
	    // Clear the buffer
	    scanner.nextLine(); 


	    // Save the data
	    int indexSave = saveData[0]++;
	    name[0][indexSave] = itemName;
	    quantity[0][indexSave] = itemQuantity;
	    price[0][indexSave] = itemPrice;

	    // Confirmation message
	    System.out.println("Item Name: " + itemName + "\nItem quantity: " + itemQuantity + "\nItem price: " + itemPrice);
	}
	/**
	 * The method will update the quantity of the item
	 * 1. Allow the user to update the quantity of an existing item.
	 * 2. The user should provide the item name and the new quantity to be added or removed.
	 * 3. Update the quantity for the specified item in the inventory.
	 * Validation:
	 * 1. Item Name:
	 * Check if the item name exists in the inventory.
	 * If not found, display an error message: "Item not found in the inventory!".
	 * 2. Quantity Update:
	 * Ensure the resulting quantity (after addition or subtraction) is not negative.
	 * @param name, name parameter is the names of the item
	 * @param quantity, item parameter is the quantity of the item
	 * @param saveData, savedata parameter is used to save data in respective 2d array
	 */
	public static void updateItemQuantity(String[][] name, int[][] quantity, int[] saveData) {
		//find the name of the item
		System.out.print(ITEM_NAME);
		String itemName = scanner.nextLine();
		
		//Step 2: validation: Check if the item name exists in the inventory.
		boolean itemFound = false;
		for(int i=0; i < saveData[0]; i++) {
			if(name[0][i].equalsIgnoreCase(itemName)) {
				itemFound = true;
				
				System.out.print(UPDATED_QUANTITY);
				int updateQuantity = scanner.nextInt();
				
				//validation
				//prompt the user to enter positive number
				while(updateQuantity < 0) {
					//error message
					System.out.println(POSITIVE_QUANTITY);
					System.out.print(UPDATED_QUANTITY);
					updateQuantity = scanner.nextInt();
				}
				
				// Clear the buffer
			    scanner.nextLine(); 
				//updating the quantity of the item
			    quantity[0][i] = updateQuantity;

	            break; //break the loop
			}
		}
		//if not found, error message
		if (!itemFound) {
	        System.out.println(ITEM_NOT_FOUND);
	        return; //exit 
	    }
	}
	/**
	 * Allow the user to search for an item by its name.
	 * Display the details of the item if found (e.g., name, quantity, price).
	 * If the item is not found, display an appropriate message.
	 * Validation
	 * 1. Item Name:
	 * Check if the name exists in the inventory.
	 * If not found, display: "Item not found!".
	 * @param name, name parameter is the names of the item
	 * @param quantity, item parameter is the quantity of the item
	 * @param price, price parameter is the price of the item
	 * @param saveData, savedata parameter is used to save data in respective 2d array
	 */
	public static void searchItemByName(String[][] name, int[][] quantity, double[][] price, int[] saveData) {
		//prompt the user to enter the name of the item
		System.out.print(ITEM_NAME);
		String itemName = scanner.nextLine();
		
		//validation
		//if the item is found: Display the details of the item if found (e.g., name, quantity, price).
		boolean itemFound = false;
		for(int i=0; i < saveData[0]; i++) {
			if(name[0][i].equalsIgnoreCase(itemName)) {
				itemFound = true;
				
				System.out.printf("%-15s %-15s %-15s%n", "Name", "Quantity", "Price");
				System.out.printf("%-15s %-15s %-15s%n", 
						name[0][i], //name of the item
						quantity[0][i], //quantity of the item
						price[0][i] //price of the item
						);
			}
		}
		//item is not found, error message
		if(!itemFound) {
			System.out.println(ITEM_NOT_FOUND);
			return; //exit
		}
	}
	/**
	 * Display all items in the inventory sorted alphabetically by their names.
	 * Show details like name, quantity, and price for each item.
	 * validation: 
	 * 1. Inventory Check:
	 * Ensure there is at least one item in the inventory before printing.
	 * If empty, display: "No items in the inventory!".
	 * @param name, name parameter is the names of the item
	 * @param quantity, item parameter is the quantity of the item
	 * @param price, price parameter is the price of the item
	 * @param saveData, savedata parameter is used to save data in respective 2d array
	 */
	
	public static void printInventorySortedByName(String[][] name, int[][] quantity, double[][] price, int[] saveData) {
		//Validation: Ensure there is at least one item.
		if (saveData[0] == 0) {
			System.out.println(EMPTY_INVENTORY);
			return; //exit the program
		}

		//sorted quantity
		bubbleSortByName(name, quantity, price, saveData[0]);
		
		//creating a table and printing out the name, quantity and the price of the item
		for(int i=0; i < saveData[0]; i++) {
			System.out.printf("%-15s %-15s %-15s%n", "Name", "Quantity", "Price");
			for(int j=0; j < saveData[i]; j++) {
				System.out.printf("%-15s %-15s %-15s%n",
						name[i][j], //name of the item
						quantity[i][j], //quantity of the item
						price[i][j] //price of the item
						);
			}
		}
	}

	/**
	 * Display all items sorted by their quantity in descending order.
	 * Show details like name, quantity, and price for each item.
	 * validation: 
	 * 1. Inventory Check:
	 * Ensure there is at least one item in the inventory before printing.
	 * If empty, display: "No items in the inventory!".
	 * @param name, name parameter is the names of the item
	 * @param quantity, item parameter is the quantity of the item
	 * @param price, price parameter is the price of the item
	 * @param saveData, savedata parameter is used to save data in respective 2d array
	 */
	public static void printInventorySortedByQuantity(String[][] name, int[][] quantity, double[][] price, int[] saveData) {
		//Validation: Ensure there is at least one item.
		if (saveData[0] == 0) {
	        System.out.println(EMPTY_INVENTORY);
	        return; //exit the program
	    }
		
		//sorted quantity
		bubbleSortByQuantity(name, quantity, price, saveData[0]);
		
		//creating a table and printing out the name, quantity and the price of the item
		for(int i=0; i < saveData[0]; i++) {
			System.out.printf("%-15s %-15s %-15s%n", "Name", "Quantity", "Price");
			for(int j=0; j < saveData[i]; j++) {
				System.out.printf("%-15s %-15s %-15s%n",
						name[i][j], //name of the item
						quantity[i][j], //quantity of the item
						price[i][j] //price of the item
						);
			}
		}
	}
	
	public static void bubbleSortByName(String[][] name, int[][] quantity, double[][] price, int numItems) {
	    boolean switchSpots;
	    for (int i = 0; i < numItems - 1; i++) {
	    	switchSpots = false;
	        for (int j = 0; j < numItems - 1 - i; j++) {
	            if (name[0][j].compareToIgnoreCase(name[0][j + 1]) > 0) {
	                // Swap names
	                String tempName = name[0][j];
	                name[0][j] = name[0][j + 1];
	                name[0][j + 1] = tempName;

	                // Swap quantities
	                int tempQuantity = quantity[0][j];
	                quantity[0][j] = quantity[0][j + 1];
	                quantity[0][j + 1] = tempQuantity;

	                // Swap prices
	                double tempPrice = price[0][j];
	                price[0][j] = price[0][j + 1];
	                price[0][j + 1] = tempPrice;

	                switchSpots = true;
	            }
	        }
	        // If no two elements were swapped in inner loop, break
	        if (!switchSpots) break;
	    }
	}
	
	public static void bubbleSortByQuantity(String[][] name, int[][] quantity, double[][] price, int numItems) {
	    boolean switchSpots;
	    for (int i = 0; i < numItems - 1; i++) {
	    	switchSpots = false;
	        for (int j = 0; j < numItems - 1 - i; j++) {
	            // Compare based on quantity instead of name
	            if (quantity[0][j] > quantity[0][j + 1]) {
	                // Swap quantities
	                int tempQuantity = quantity[0][j];
	                quantity[0][j] = quantity[0][j + 1];
	                quantity[0][j + 1] = tempQuantity;

	                // Swap names
	                String tempName = name[0][j];
	                name[0][j] = name[0][j + 1];
	                name[0][j + 1] = tempName;

	                // Swap prices
	                double tempPrice = price[0][j];
	                price[0][j] = price[0][j + 1];
	                price[0][j + 1] = tempPrice;

	                switchSpots = true;
	            }
	        }
	        // If no two elements were swapped in inner loop, break
	        if (!switchSpots) break;
	    }
	}
}
