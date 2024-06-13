package repository.database;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;
import domain.Item;
import domain.domainException.IncorrectItemInformationException;
import repository.ItemRepo;

public class DatabaseItemRepo implements ItemRepo {
    private List<Item> items;
    private int nextID;

    public DatabaseItemRepo() throws IncorrectItemInformationException {
        String sql = "SELECT * FROM ITEMS";
        try (Connection connection = DatabaseConnect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            ) {
            items = new LinkedList<>();
            int count = 0;
            while(results.next()) {
                count++;
                int itemID = results.getInt(1);
                String itemName = results.getString(2);
                double itemPrice = results.getDouble(3);
                int itemAmount = results.getInt(4);
                String itemDesc = results.getString(5);
                items.add(new Item(itemID, itemName, itemPrice, itemAmount, itemDesc));
            }
            if (count == 0) {
                nextID = 1;
            }
            else {
                Item last = items.get(count-1);
                int number = last.getItemID();
                nextID = number + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            String sql = "INSERT INTO ITEMS(itemID, itemName, itemPrice, itemAmount, itemDesc) VALUES (?, ?, ?, ?, ?)";
            try (Connection connection = DatabaseConnect.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, itemName);
                preparedStatement.setDouble(3, itemPrice);
                preparedStatement.setInt(4, itemAmount);
                preparedStatement.setString(5, itemDesc);
                int result = preparedStatement.executeUpdate();
                if(result == 0) return null;
            }
            catch (SQLException e) { e.printStackTrace(); }
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
        String sql = "UPDATE ITEMS SET itemName = ?, itemPrice = ?, itemAmount = ?, itemDesc = ? WHERE itemID = ?";
        try (Connection connection = DatabaseConnect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(5, item.getItemID());
            preparedStatement.setString(1, itemName);
            preparedStatement.setDouble(2, itemPrice);
            preparedStatement.setInt(3, itemAmount);
            preparedStatement.setString(4, itemDesc);
            int result = preparedStatement.executeUpdate();
            if(result == 0) return false;
        }
        catch (SQLException e) { e.printStackTrace(); }
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
            String sql = "DELETE FROM ITEMS WHERE itemID = ?";
            try (Connection connection = DatabaseConnect.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
                preparedStatement.setInt(1, item.getItemID());
                int result = preparedStatement.executeUpdate();
                if(result == 0) return false;
            }
            catch (SQLException e) { e.printStackTrace(); }
            return true;
        }
        return false;
    }

    @Override
    public Stream<Item> stream() {
        return items.stream();
    }
}
