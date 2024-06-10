package service;

import domain.*;
import domain.domainException.*;
import repository.*;

// Item setttings & buying process
public class BuyItem {
    private ItemRepo itemRepo;
    private Customer customer = null;
    private AdjustCustomer check;
    private int id;

    public BuyItem(AdjustCustomer check, ItemRepo itemRepo) throws IncorrectCustomerInformationException, IncorrectItemInformationException {
        if(check == null) throw new IncorrectCustomerInformationException("check must not be null!");
        if(itemRepo == null) throw new IncorrectItemInformationException("itemRepo must not be null!");
        this.check = check;
        this.itemRepo = itemRepo;
        id = check.getCustomerID();
        customer = check.checkCustomerID(id);
    }
    // Getter
    public double getItemPrice(int itemID) throws IncorrectItemInformationException {
        if(itemID < 0) throw new IncorrectItemInformationException("itemID must be a positive number!");
        return itemRepo.get(itemID).getItemPrice();
    }
    public int getItemAmount(int itemID) throws IncorrectItemInformationException {
        if(itemID < 0) throw new IncorrectItemInformationException("itemID must be a positive number!");
        return itemRepo.get(itemID).getItemAmount();
    }
    public String getItemDesc(int itemID) throws IncorrectItemInformationException {
        if(itemID < 0) throw new IncorrectItemInformationException("itemID must be a positive number!");
        return "----------" + "\n" +
        "Name: " + itemRepo.get(itemID).getItemName() + "\n"
        + "Price: " + itemRepo.get(itemID).getItemPrice() + "\n"
        + "\"" + itemRepo.get(itemID).getItemDesc() + "\"" + "\n"
        + "----------";
    }
    // Checker
    public Customer checkCustomerID (int customerID) {
        if(check.checkCustomerID(customerID) == null) return null;
        return customer;
    }
    public Item checkItemID(int itemID) {
        Item checkExistItem = itemRepo.stream()
            .filter(i -> i.getItemID() == itemID)
            .findFirst()
            .orElse(null);
        if(itemID <= 0 || checkExistItem == null) return null;
        return itemRepo.get(itemID);
    }
    // Create
    public Item addItem(String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        if(itemName == null || itemName.isBlank() || itemPrice < 0
        || itemAmount < 0 || itemDesc == null || itemDesc.isBlank()) return null;
        return itemRepo.create(itemName, itemPrice, itemAmount, itemDesc);
    }
    // Update
    public boolean updateCustomer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        return check.updateCustomer(customerID, customerName, customerMoney);
    }
    public boolean updateItem(int itemID, String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        Item id = checkItemID(itemID);
        if(id == null || itemName == null || itemName.isBlank()
        || itemPrice < 0 || itemAmount < 0 || itemDesc == null
        || itemDesc.isBlank()) return false;
        itemRepo.update(id, itemName, itemPrice, itemAmount, itemDesc);
        return true;
    }
    // Remove
    public boolean deleteItem(int itemID) {
        Item item = checkItemID(itemID);
        if(item == null) return false;
        itemRepo.remove(item);
        return true;
    }
    // Buy
    public double buy(int itemID, int amount) throws IncorrectItemInformationException, IncorrectCustomerInformationException {
        Item iid = checkItemID(itemID);
        Customer cid = checkCustomerID(customer.getCustomerID());
        if(iid == null) throw new IncorrectItemInformationException("itemID must not be null!");
        if(cid == null) throw new IncorrectItemInformationException("customerID must not be null!");
        double cmoney = customer.getCustomerMoney();
        double iprice = getItemPrice(itemID);
        int iamount = getItemAmount(itemID);
        if(iamount < 0) throw new IncorrectItemInformationException("itemAmount must be a positive integer!");
        if(amount > 0 && amount <= iamount) {
            double cost = amount * iprice;
            if(cmoney >= cost) {
                cmoney -= cost;
                iamount -= amount;
                updateCustomer(customer.getCustomerID(), cid.getCustomerName(), cmoney);
                itemRepo.update(iid, iid.getItemName(), iprice, iamount, iid.getItemDesc());
                return cost;
            }
            if(cmoney < cost) return -1;
        }
        return -1;
    }

    public void listItem() throws IncorrectItemInformationException {
        if(itemRepo.stream() == null) throw new IncorrectItemInformationException("itemRepository must not be null!");
        itemRepo.stream()
            .forEach(System.out::println);
    }
}
