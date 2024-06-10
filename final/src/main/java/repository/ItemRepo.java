package repository;

import java.util.stream.Stream;

import domain.Item;
import domain.domainException.IncorrectItemInformationException;

public interface ItemRepo {
    public Item get(int itemID);
    public Item create(String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException;
    public boolean update(Item item, String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException;
    public boolean remove(Item item);
    public Stream<Item> stream();
}