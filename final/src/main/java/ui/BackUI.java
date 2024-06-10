package ui;

import java.util.Scanner;

import domain.domainException.*;
import repository.*;
import repository.database.*;
import repository.file.*;
import repository.memory.InMemoryCustomerRepo;
import service.*;

public class BackUI {
    private final Scanner sc = new Scanner(System.in);
    private CustomerRepo customerRepo = null;
    private ItemRepo itemRepo = null;
    private BuyItem buyItem = null;
    private AdjustCustomer service;
    private boolean isAdmin = false;
    private int id;

    public BackUI(boolean useDatabase) {
        if (useDatabase) {
            try {
                service = new AdjustCustomer(new DatabaseCustomerRepo(), id);
                buyItem = new BuyItem(service, new DatabaseItemRepo());
            } catch (IncorrectCustomerInformationException e) {
                e.printStackTrace();
            } catch (IncorrectItemInformationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                service = new AdjustCustomer(new FileCustomerRepo(), id);
                buyItem = new BuyItem(service, new FileItemRepo());
            } catch (IncorrectCustomerInformationException e) {
                e.printStackTrace();
            } catch (IncorrectItemInformationException e) {
                e.printStackTrace();
            }
        }
    }
    public BackUI(CustomerRepo customerRepo, ItemRepo itemRepo, int id) {
        try {
            setInMemoryCustomerID(id);
            this.customerRepo = customerRepo;
            this.itemRepo = itemRepo;
            this.id = id;
            service = new AdjustCustomer(this.customerRepo, this.id);
            buyItem = new BuyItem(service, this.itemRepo);
        } catch (IncorrectCustomerInformationException e) {
            e.printStackTrace();
        } catch (IncorrectItemInformationException e) {
            e.printStackTrace();
        }
    }

    public void loginUI() {
        System.out.println("\n\n\n\n\n");
        System.out.println("""
                ========== Log-in ==========
                Your ID: """ + " " + id + """
                
                Your name: """ + " " + service.checkCustomerID(id).getCustomerName());
        var cons = System.console();
        while (true) {
            System.out.print("Password: ");
            String password = null;
            while(password == null || password.isBlank()) {
                if (cons != null) {
                    password = new String (cons.readPassword());
                    if(!password.isBlank() && password != null) break;
                    System.out.println("Please fill the password.");
                    System.out.print("Password: ");
                }
                if (cons == null) {
                    password = sc.nextLine();
                    if(!password.isBlank() && password != null) break;
                    System.out.println("Please fill the password.");
                    System.out.print("Password: ");
                }
            }
            try {
                if (service.checkPassword(id, password)) {
                    if (service.getCustomerID() == 3) { isAdmin = true; }
                    menuUI();
                    break;
                } else {
                    System.out.println("*Password is incorrect*");
                    continue;
                }
            } catch (IncorrectCustomerInformationException e) {
                e.printStackTrace();
            }
        }
    }
    public void menuUI() {
        if(!isAdmin) {
            int choice = 0;
            while(choice < 1 || choice > 3) {
                System.out.println("\n\n\n\n\n============ Menu ============");
                System.out.println("1. Show List Item");
                System.out.println("2. Buy Item");
                System.out.println("3. Exit");
                System.out.println("==============================");
                System.out.print("Enter the number of the menu [1-3]: ");
                choice = sc.nextInt();
                sc.nextLine();
                if(choice >= 1 && choice <= 3) {
                    switch (choice) {
                        case 1 -> showListItemUI();
                        case 2 -> buyUI();
                        case 3 -> System.out.print("Thank you for using our service.");
                    }
                    break;
                }
                System.out.println("Invalid choice. Please choose again.");
                continue;
            }
        }
        if(isAdmin) {
            int choice = 0;
            while(choice < 1 || choice > 5) {
                System.out.println("\n\n\n\n\n============ Menu ============");
                System.out.println("1. Add Item");
                System.out.println("2. Delete Item");
                System.out.println("3. Update Item");
                System.out.println("4. Show List Item");
                System.out.println("5. Exit");
                System.out.println("==============================");
                System.out.print("Enter the number of the menu [1-5]: ");
                choice = sc.nextInt();
                sc.nextLine();
                if(choice >= 1 && choice <= 5) {
                    switch (choice) {
                        case 1 -> addItemUI();
                        case 2 -> deleteItemUI();
                        case 3 -> updateItemUI();
                        case 4 -> showListItemUI();
                        case 5 -> System.out.print("Thank you for using our service.");
                    }
                    break;
                }
                System.out.println("Invalid choice. Please choose again.");
                continue;
            }
        }
    }
    public void addItemUI() {
        if (isAdmin) {
            while (true) {
                System.out.println("\n\n\n\n\n============ Item List ============");
                try {
                    buyItem.listItem();
                } catch (IncorrectItemInformationException e) {
                    e.printStackTrace();
                }
                System.out.println("==================================");
                String itemName = null;
                double itemPrice = -1;
                int quantity = -1;
                String itemDesc = null;
                while (itemName == null || itemName.isBlank()) {
                    System.out.print("Enter the name of the item you want to add [Example: Egg]: ");
                    itemName = sc.nextLine();
                    sc.nextLine();
                    if (itemName == null || itemName.isBlank()) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    break;
                }
                while (itemPrice < 0) {
                    System.out.print("Enter the price for this item [Example: 2.99]: ");
                    itemPrice = sc.nextDouble();
                    sc.nextLine();
                    if (itemPrice < 0) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    break;
                }
                while (quantity < 0) {
                    System.out.print("Enter the quantity for this item [Example: 50]: ");
                    quantity = sc.nextInt();
                    sc.nextLine();
                    if (quantity < 0) {
                        System.out.println("Invalid input.");
                        continue;
                    }
                    break;
                }
                while (itemDesc == null || itemDesc.isBlank()) {
                    System.out.print("Please fill in the description of your item [Example: It\'s delicious!]: ");
                    itemDesc = sc.nextLine();
                    sc.nextLine();
                    if (itemDesc == null || itemDesc.isBlank()) {
                        if (quantity < 0) {
                            System.out.println("Invalid input.");
                            continue;
                        }
                        break;
                    }
                }
                try {
                    buyItem.addItem(itemName, itemPrice, quantity, itemDesc);
                    System.out.println("Add Completed!");
                    System.out.println("\n\n\n\n\n============ Item List ============");
                    try {
                        buyItem.listItem();
                    } catch (IncorrectItemInformationException e) {
                        e.printStackTrace();
                    }
                    System.out.println("==================================");
                    // ยัดลงฐานข้อมูล
                } catch (IncorrectItemInformationException e) {
                    e.printStackTrace();
                }
                // ยัดลงฐานข้อมูล
                System.out.print("Do you want to add more item? (Y/other character): ");
                String choice = sc.nextLine();
                sc.nextLine();
                if (choice.equalsIgnoreCase("Y")) {
                    continue;
                } else {
                    break;
                }
            }
            menuUI();
        } else {
            System.out.println("Sorry! You can\'t access this feature.");
            menuUI();
        }
    }
    public void updateItemUI() {
        if (isAdmin) {
            while (true) {
                System.out.println("\n\n\n\n\n============ Item List ============");
                try {
                    buyItem.listItem();
                } catch (IncorrectItemInformationException e) {
                    e.printStackTrace();
                }
                System.out.println("==================================");
                int itemID = -1;
                String itemName = null;
                double itemPrice = -2;
                int itemAmount = -2;
                String itemDesc = null;
                while (itemID <= 0 || buyItem.checkItemID(itemID) == null) {
                    System.out.print("Enter the item ID you wish to update: ");
                    itemID = sc.nextInt();
                    sc.nextLine();
                    if (itemID <= 0 || buyItem.checkItemID(itemID) == null) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if(itemID > 0) { break; }
                }
                while (itemName == null || itemName.isBlank()) {
                    System.out.print("Enter the item name you wish to update [If not, please type \"N\"]: ");
                    itemName = sc.nextLine();
                    sc.nextLine();
                    if (itemName == null || itemName.isBlank()) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if (itemName.equalsIgnoreCase("N")) {
                        itemName = buyItem.checkItemID(itemID).getItemName();
                        break;
                    }
                    if (itemName != null && !itemName.isBlank() && !itemName.equalsIgnoreCase("N")) {
                        break;
                    }
                }
                while ((itemPrice < 0 && itemPrice > -1) || itemPrice < -1) {
                    System.out.print("Enter the item price you wish to update [If not, please type \"-1\"]: ");
                    itemPrice = sc.nextDouble();
                    sc.nextLine();
                    if ((itemPrice < 0 && itemPrice > -1) || itemPrice < -1) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if (itemPrice == -1) {
                        itemPrice = buyItem.checkItemID(itemID).getItemPrice();
                        break;
                    }
                    if (itemPrice >= 0) {
                        break;
                    }
                }
                while (itemAmount < -1) {
                    System.out.print("Enter the amount of item you wish to update [If not, please type \"-1\"]: ");
                    itemAmount = sc.nextInt();
                    sc.nextLine();
                    if (itemAmount < -1) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if (itemAmount == -1) {
                        itemAmount = buyItem.checkItemID(itemID).getItemAmount();
                        break;
                    }
                    if (itemAmount > -1) {
                        break;
                    }
                }
                while (itemDesc == null || itemDesc.isEmpty()) {
                    System.out
                            .print("Enter the description of the item you wish to update [If not, please type \"N\"]: ");
                    itemDesc = sc.nextLine();
                    sc.nextLine();
                    if (itemDesc == null || itemDesc.isBlank()) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if (itemDesc.equalsIgnoreCase("N")) {
                        itemDesc = buyItem.checkItemID(itemID).getItemDesc();
                        break;
                    }
                    if (itemDesc != null && !itemDesc.isBlank() && !itemDesc.equalsIgnoreCase("N")) {
                        break;
                    }
                }
                System.out.print("===================" + "\n"
                        +"Item\n"
                        + "ID: " + itemID + "\n"
                        + "Name: " + itemName + "\n"
                        + "Price: " + itemPrice + "\n"
                        + "Amount: " + itemAmount + "\n"
                        + "Description: " + itemDesc + "\n"
                        + "Is this what you wish to update? (Y/other character): ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("Y")) {
                    try {
                        buyItem.updateItem(itemID, itemName, itemPrice, itemAmount, itemDesc);
                        System.out.println("Update Completed!");
                        System.out.println("\n\n\n\n\n============ Item List ============");
                        try {
                            buyItem.listItem();
                        } catch (IncorrectItemInformationException e) {
                            e.printStackTrace();
                        }
                        System.out.println("==================================");
                    } catch (IncorrectItemInformationException e) {
                        e.printStackTrace();
                    }
                    System.out.print("Do you wish to update other items?\n"
                            + "(Y/other character): ");
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("Y")) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
            menuUI();
        } else {
            System.out.println("Sorry! You can\'t access this feature.");
            menuUI();
        }
    }
    public void deleteItemUI() {
        if (isAdmin) {
            while (true) {
                System.out.println("\n\n\n\n\n============ Item List ============");
                try {
                    buyItem.listItem();
                } catch (IncorrectItemInformationException e) {
                    e.printStackTrace();
                }
                System.out.println("==================================");
                int itemID = -2;
                while (itemID < -1) {
                    System.out.print("Delete Item [Enter item ID] (To cancel, please type \"-1\"): ");
                    itemID = sc.nextInt();
                    sc.nextLine();
                    if (itemID < -1) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                    if (itemID == -1) {
                        System.out.println("You canceled the deletion of the item.");
                        break;
                    }
                    if (itemID > 0) {
                        String choice = null;
                        while (choice == null || choice.isBlank()
                        || !choice.equalsIgnoreCase("Y") || !choice.equalsIgnoreCase("N")) {
                            System.out.print("Are you sure? [Y/N]: ");
                            choice = sc.nextLine();
                            sc.nextLine();
                            if (choice.equalsIgnoreCase("N")) {
                                System.out.println("You canceled the deletion of the item.");
                                break;
                            } else if (choice.equalsIgnoreCase("Y")) {
                                buyItem.deleteItem(itemID);
                                System.out.println("Delete Completed!");
                                System.out.println("\n\n\n\n\n============ Item List ============");
                                try {
                                    buyItem.listItem();
                                } catch (IncorrectItemInformationException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("==================================");
                                break;
                            }
                            continue;
                        }
                    }
                }
                String choice2;
                System.out.print("Do you want to continue deleting item? [Y/other character]: ");
                choice2 = sc.nextLine();
                sc.nextLine();
                if (choice2.equalsIgnoreCase("Y")) {
                    continue;
                } else {
                    break;
                }
            }
            menuUI();
        } else {
            System.out.println("Sorry! You can\'t access this feature.");
            menuUI();
        }
    }
    public void showListItemUI() {
        System.out.println("\n\n\n\n\n============ Item List ============");
        try {
            buyItem.listItem();
        } catch (IncorrectItemInformationException e) {
            e.printStackTrace();
        }
        System.out.println("==================================");
        String back = null;
        while(back == null || back.isBlank() || !back.equalsIgnoreCase("Back")) {
            System.out.print("Please type \"Back\" to go back to Main Menu: ");
            back = sc.nextLine();
            if(back.equalsIgnoreCase("Back")) {
                menuUI();
                break;
            }
            System.out.println("Invalid Input!");
            continue;
        }
    }
    public void buyUI() {
        if(!isAdmin) {
            while(true) {
                System.out.println("\n\n\n\n\n=============== Item ===============");
                try {
                    buyItem.listItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("====================================");
                int itemID = 0;
                while (buyItem.checkItemID(itemID) == null) {
                    System.out.print("What item do you want to buy?\n"
                    + "Insert here [Item ID]: ");
                    itemID = sc.nextInt();
                    sc.nextLine();
                    if(buyItem.checkItemID(itemID) != null) {
                        break;
                    }
                    if(buyItem.checkItemID(itemID) == null) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                }
                int itemAmount = -1;
                while (itemAmount < 0) {
                    System.out.print("How many amount of item do you want buy?\n"
                    + "Insert here [Amount]: ");
                    itemAmount = sc.nextInt();
                    sc.nextLine();
                    if(itemAmount > 0) {
                        break;
                    }
                    if(itemAmount <= 0) {
                        System.out.println("Invalid Input!");
                        continue;
                    }
                }
                System.out.println("Processing...");
                try {
                    double cost = buyItem.buy(itemID, itemAmount);
                    while (true) {
                        if(cost == -1) {
                            System.out.println("Sorry! " + buyItem.checkItemID(itemID).getItemName() +
                            " is out of stock!");
                            System.out.println("Your money: " + customerRepo.get(id).getCustomerMoney());
                            break;
                        }
                        if(cost > 0) {
                            System.out.println("You purchased \"" + buyItem.checkItemID(itemID).getItemName() 
                            + "\" for \"" + itemAmount + "\" amount(s), which cost \"" + cost + "\".");
                            System.out.println("Your money: " + customerRepo.get(id).getCustomerMoney());
                            break;
                        }
                    }
                } catch (IncorrectItemInformationException | IncorrectCustomerInformationException e) {
                    e.printStackTrace();
                }
                String choice2;
                System.out.print("Do you want to buy anything else? [Y/other character]: ");
                choice2 = sc.nextLine();
                if (choice2.equalsIgnoreCase("Y")) {
                    continue;
                } else {
                    System.out.println("Thank you for your patronage! :)");
                    break;
                }
            }
            menuUI();
        } else {
            System.out.println("Sorry! You can\'t access this feature.");
            menuUI();
        }
    }
    public int setInMemoryCustomerID(int id) {
        customerRepo = new InMemoryCustomerRepo();
        if(customerRepo.get(id) == null) return -1;
        return id;
    }
    public int setFileCustomerID(int id) {
        customerRepo = new FileCustomerRepo();
        if(customerRepo.get(id) == null) return -1;
        return id;
    }
}
