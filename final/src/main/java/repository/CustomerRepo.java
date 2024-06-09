package repository;

import domain.*;
import domain.domainException.IncorrectCustomerInformationException;
import java.util.stream.Stream;

public interface CustomerRepo {
    public Customer get(int customerID);
    public Customer create(String customerName, double customerMoney, String customerPassword) throws IncorrectCustomerInformationException;
    public boolean update(Customer customer, String customerName, double customerMoney) throws IncorrectCustomerInformationException;
    public boolean remove(Customer customer);
    public String getPassword(int customerID);
    public Stream<Customer> stream();
}
