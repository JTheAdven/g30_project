package repository.file;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import domain.Item;
import domain.domainException.IncorrectItemInformationException;
import repository.ItemRepo;

public class FileItemRepo implements ItemRepo {
    private String filename = "E:\\Study folder\\Year 1\\Term 2\\INT103\\project\\final\\src\\main\\resources\\itemFile.txt";
    private List<Item> items;
    private int nextID;

    public FileItemRepo() throws IncorrectItemInformationException {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(filename);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
            ) {
                items = (List<Item>) ois.readObject();
                nextID = ois.readInt();
            }
            catch (IOException io) { io.printStackTrace(); }
            catch (ClassNotFoundException cnf) { cnf.printStackTrace(); }
        } else {
            this.items = new LinkedList<>();
            nextID = 1;
        }
    }

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
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(items);
                oos.writeInt(nextID);
            }
            catch (IOException io) { io.printStackTrace(); }
            return i;
        }
        return null;
    }

    @Override
    public boolean update(Item item, String itemName, double itemPrice, int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        if(item == null || itemName == null || itemName.isBlank() || itemPrice < 0
        || itemAmount < 0 || itemDesc == null || itemDesc.isBlank()) return false;
        item.setItemName(itemName);
        item.setItemPrice(itemPrice);
        item.setItemAmount(itemAmount);
        item.setItemDesc(itemDesc);
        try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(items);
                oos.writeInt(nextID);
            }
        catch (IOException io) { io.printStackTrace(); }
        return true;
    }

    @Override
    public boolean remove(Item item) {
        Item checkExistingItem = items.stream()
        .filter(i -> i.equals(item))
        .findAny()
        .orElse(null);
        if(checkExistingItem != null) {
            items.remove(item);
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(items);
                oos.writeInt(nextID);
            }
            catch (IOException io) { io.printStackTrace(); }
            return true;
        }
        return false;
    }
    
    @Override
    public Stream<Item> stream() {
        return items.stream();
    }
}