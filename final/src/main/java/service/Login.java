package service;

import domain.Customer;
import domain.domainException.IncorrectCustomerInformationException;
import repository.CustomerRepo;

public class Login {
    private CustomerRepo customerRepo;

    public Login(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    // Create
    public Customer addCustomer(String customerName, double customerMoney, String customerPassword) throws IncorrectCustomerInformationException {
        if(customerName == null || customerName.isBlank() || customerMoney < 0) return null;
        return customerRepo.create(customerName, customerMoney, customerPassword);
    }
    // Update
    public boolean updateCustomer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        Customer customer = customerRepo.get(customerID);
        if(customer == null || customerName == null || customerName.isBlank() || customerMoney < 0) return false;
        customerRepo.update(customer, customerName, customerMoney);
        return true;
    }
    // Remove
    public boolean deleteCustomer(int customerID) {
        Customer customer = customerRepo.get(customerID);
        if(customer == null) return false;
        customerRepo.remove(customer);
        return true;
    }
    // Password : 1234
    public boolean checkPassword(String password) {
        return password.equals("1234") || password.equals("1111") || password.equals("password");
    }
}
