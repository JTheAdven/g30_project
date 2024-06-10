package repository.memory;

import java.util.*;
import java.util.stream.Stream;
import domain.Item;
import domain.domainException.IncorrectItemInformationException;
import repository.ItemRepo;

public class InMemoryItemRepo implements ItemRepo {
    private List<Item> items;
    private int nextID = 1;

    public InMemoryItemRepo() { this.items = new LinkedList<>(); }

    @Override
    public Item get(int itemID) {
        return items.stream()
        .filter(i -> i.getItemID() == itemID)
        .findFirst()
        .orElse(null);
    }

    @Override
    public Item create(String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        Item checkExistingItem = items.stream()
        .filter(i -> i.getItemName() == itemName)
        .findAny()
        .orElse(null);
        if(checkExistingItem == null) {
            int id = nextID;
            Item i = new Item(id, itemName, itemPrice, itemAmount, itemDesc);
            items.add(i);
            nextID++;
            return i;
        }
        return null;
    }

    @Override
    public boolean update(Item item, String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        if(itemName == null || itemName.isBlank() || itemPrice < 0
        || itemAmount < 0 || itemDesc == null || itemDesc.isBlank()) return false;
        item.setItemName(itemName);
        item.setItemPrice(itemPrice);
        item.setItemAmount(itemAmount);
        item.setItemDesc(itemDesc);
        return true;
    }

    @Override
    public boolean remove(Item item) {
        Item checkExistingItem = items.stream()
        .filter(i -> i.equals(item))
        .findAny()
        .orElse(null);
        if(checkExistingItem != null) {
            return items.remove(item);
        }
        return false;
    }
    
    @Override
    public Stream<Item> stream() {
        return items.stream();
    }
}
