package repository.database;

import java.util.stream.Stream;
import domain.Customer;
import domain.domainException.IncorrectCustomerInformationException;
import repository.*;

public class DatabaseCustomerRepo implements CustomerRepo {

    @Override
    public Customer get(int customerID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Customer create(String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public boolean update(Customer customer, String customerName, double customerMoney)
            throws IncorrectCustomerInformationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean remove(Customer customer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Stream<Customer> stream() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }
    
}
