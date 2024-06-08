package service;

import domain.*;
import repository.*;

public class buyItem {
    private CustomerRepo customerRepo;
    private ItemRepo itemRepo;

    public buyItem(CustomerRepo customerRepo, ItemRepo itemRepo) {
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
    }

    public Item getItemID(int itemID) {
        if(itemID < 0) return null;
        return itemRepo.get(itemID);
    }
}
