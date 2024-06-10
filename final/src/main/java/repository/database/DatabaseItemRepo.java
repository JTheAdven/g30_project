package repository.database;

import java.util.stream.Stream;
import domain.Item;
import domain.domainException.IncorrectItemInformationException;
import repository.ItemRepo;

public class DatabaseItemRepo implements ItemRepo {

    @Override
    public Item get(int itemID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Item create(String itemName, double itemPrice, int itemAmount, String itemDesc)
            throws IncorrectItemInformationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public boolean update(Item item, String itemName, double itemPrice, int itemAmount, String itemDesc)
            throws IncorrectItemInformationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean remove(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Stream<Item> stream() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }
    
}
