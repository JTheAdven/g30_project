package service;

import domain.*;
import domain.domainException.*;
import repository.*;

public class BuyItem {
    private CustomerRepo customerRepo;
    private ItemRepo itemRepo;
    private Customer customer = null;

    public BuyItem(CustomerRepo customerRepo, ItemRepo itemRepo) {
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }
    // Getter
    public double getCustomerMoney(int customerID) throws IncorrectCustomerInformationException {
        if(customerID < 0) throw new IncorrectCustomerInformationException();
        return customerRepo.get(customerID).getCustomerMoney();
    }
    public double getItemPrice(int itemID) throws IncorrectItemInformationException {
        if(itemID < 0) throw new IncorrectItemInformationException();
        return itemRepo.get(itemID).getItemPrice();
    }
    public int getItemAmount(int itemID) throws IncorrectItemInformationException {
        if(itemID < 0) throw new IncorrectItemInformationException();
        return itemRepo.get(itemID).getItemAmount();
    }
    // Checker
    public Customer checkCustomerID (int customerID) {
        if(customerID < 0) return null;
        return customerRepo.get(customerID);
    }
    public Customer checkCustomerName (String customerName) {
        if(customerName == null || customerName.isBlank()) return null;
        return customerRepo.stream()
                .filter(c -> c.getCustomerName().equals(customerName))
                .findFirst()
                .orElse(null);
    }
    public Customer checkCustomerMoney (double customerMoney) {
        if(customerMoney < 0) return null;
        return customerRepo.stream()
                .filter(c -> c.getCustomerMoney() == customerMoney)
                .findFirst()
                .orElse(null);
    }
    public Item checkItemID(int itemID) {
        if(itemID < 0) return null;
        return itemRepo.get(itemID);
    }
    public Item checkItemName(String itemName) {
        if(itemName == null || itemName.isBlank()) return null;
        return itemRepo.stream()
                .filter(i -> i.getItemName().equals(itemName))
                .findFirst()
                .orElse(null);
    }
    public Item checkItemPrice(double itemPrice) {
        if(itemPrice < 0) return null;
        return itemRepo.stream()
                .filter(i -> i.getItemPrice() == itemPrice)
                .findFirst()
                .orElse(null);
    }
    public Item checkItemAmount(int itemAmount) {
        if(itemAmount < 0) return null;
        return itemRepo.stream()
                .filter(i -> i.getItemAmount() == itemAmount)
                .findFirst()
                .orElse(null);
    }
    public Item checkItemDesc(String itemDesc) {
        if(itemDesc == null || itemDesc.isBlank()) return null;
        return itemRepo.stream()
                .filter(i -> i.getItemDesc().equals(itemDesc))
                .findFirst()
                .orElse(null);
    }
    // Create
    public Customer addCustomer(String customerName, double customerMoney, String customerPassword) throws IncorrectCustomerInformationException {
        if(customerName == null || customerName.isBlank() || customerMoney < 0) return null;
        return customerRepo.create(customerName, customerMoney, customerPassword);
    }
    public Item addItem(String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        if(itemName == null || itemName.isBlank() || itemPrice < 0
        || itemAmount < 0 || itemDesc == null || itemDesc.isBlank()) return null;
        return itemRepo.create(itemName, itemPrice, itemAmount, itemDesc);
    }
    // Update
    public boolean updateCustomer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        Customer id = checkCustomerID(customerID);
        Customer name = checkCustomerName(customerName);
        Customer money = checkCustomerMoney(customerMoney);
        if(id == null || name == null || money == null) return false;
        customerRepo.update(id, customerName, customerMoney);
        return true;
    }
    public boolean updateItem(int itemID, String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        Item id = checkItemID(itemID);
        Item name = checkItemName(itemName);
        Item price = checkItemPrice(itemPrice);
        Item amount = checkItemAmount(itemAmount);
        Item desc = checkItemDesc(itemDesc);
        if(id == null || name == null || price == null || amount == null || desc == null) return false;
        itemRepo.update(id, itemName, itemPrice, itemAmount, itemDesc);
        return true;
    }
    // Remove
    public boolean deleteCustomer(int customerID) {
        Customer customer = checkCustomerID(customerID);
        if(customer == null) return false;
        customerRepo.remove(customer);
        return true;
    }
    public boolean deleteItem(int itemID) {
        Item item = checkItemID(itemID);
        if(item == null) return false;
        itemRepo.remove(item);
        return true;
    }
    // Buy
    public double buy(int customerID, int itemID, int amount) throws IncorrectItemInformationException, IncorrectCustomerInformationException {
        Item iid = checkItemID(itemID);
        Customer cid = checkCustomerID(customerID);
        if(iid == null) throw new IncorrectItemInformationException();
        if(cid == null) throw new IncorrectItemInformationException();
        double cmoney = getCustomerMoney(customerID);
        double iprice = getItemPrice(itemID);
        int iamount = getItemAmount(itemID);
        if(amount > 0) {
            double cost = amount * iprice;
            cmoney -= cost;
            iamount -= amount;
            customerRepo.update(cid, cid.getCustomerName(), cmoney);
            itemRepo.update(iid, iid.getItemName(), iprice, iamount, iid.getItemDesc());
            return cost;
        }
        if(amount == 0) return 0;
        return -1;
    }
}
