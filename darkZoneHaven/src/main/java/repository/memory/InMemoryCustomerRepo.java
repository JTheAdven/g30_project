package repository.memory;

import java.util.*;
import java.util.stream.Stream;
import domain.*;
import domain.domainException.*;
import repository.CustomerRepo;

public class InMemoryCustomerRepo implements CustomerRepo {
    private List<Customer> customers;
    private int nextID = 1;
    
    public InMemoryCustomerRepo(){ this.customers = new LinkedList<>(); }

    @Override
    public Customer get(int customerID) {
        return customers.stream()
        .filter(c -> c.getCustomerID() == customerID)
        .findFirst()
        .orElse(null);
    }

    @Override
    public Customer create(String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        Customer checkExistingCustomer = customers.stream()
        .filter(c -> c.getCustomerName().equals(customerName))
        .findFirst()
        .orElse(null); 
        if (checkExistingCustomer == null) {
            int id = nextID;
            Customer c = new Customer(id, customerName, customerMoney);
            customers.add(c);
            nextID++;
            return c;
        }
        return null;
    }

    @Override
    public boolean update(Customer customer, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if (customer == null || customerName == null || customerName.isBlank() || customerMoney < 0) return false;
        customer.setCustomerName(customerName);
        customer.setCustomerMoney(customerMoney);
        return true;
    }
    
    @Override
    public boolean remove(Customer customer) {
        Customer checkExistingCustomer = customers.stream()
        .filter(c -> c.equals(customer))
        .findAny()
        .orElse(null);
        if(checkExistingCustomer != null) {
            customers.remove(customer);
            return true;
        }
        return false;
    }

    @Override
    public Stream<Customer> stream() {
        return customers.stream();
    }
}

