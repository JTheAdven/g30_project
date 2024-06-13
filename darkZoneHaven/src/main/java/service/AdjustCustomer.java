package service;

import java.util.Map;
import domain.Customer;
import domain.domainException.IncorrectCustomerInformationException;
import repository.CustomerRepo;

// Customer Settings
public class AdjustCustomer {
    private CustomerRepo customerRepo;
    private Customer customer = null;

    public AdjustCustomer(CustomerRepo customerRepo, int customerID) throws IncorrectCustomerInformationException {
        if(customerID < 0) throw new IncorrectCustomerInformationException(
            "Something went wrong! Please try again or input another ID."
        );
        this.customerRepo = customerRepo;
        customer = this.customerRepo.get(customerID);
    }

    public int getCustomerID() {
        return customer.getCustomerID();
    }
    public Customer checkCustomerID (int customerID) {
        Customer checkID = customerRepo.stream()
                                    .filter(c -> c.getCustomerID() == customerID)
                                    .findFirst()
                                    .orElse(null);
        if(customerID < 0 || checkID == null) return null;
        return customerRepo.get(customerID);
    }

    public Customer addCustomer(String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if(customerName == null || customerName.isBlank() || customerMoney < 0) return null;
        return customerRepo.create(customerName, customerMoney);
    }
    public boolean updateCustomer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        Customer id = checkCustomerID(customerID);
        if(id == null || customerName == null
        || customerName.isBlank() || customerMoney < 0) return false;
        customerRepo.update(id, customerName, customerMoney);
        return true;
    }
    public boolean deleteCustomer(int customerID) {
        Customer customer = checkCustomerID(customerID);
        if(customer == null) return false;
        customerRepo.remove(customer);
        return true;
    }
    public boolean checkPassword(int customerID, String password) throws IncorrectCustomerInformationException {
        Customer checkExist = customerRepo.stream()
                                .filter(c -> c.getCustomerID() == customerID)
                                .findFirst()
                                .orElse(null);
        if(password.isBlank() || password == null || customerID < 0) return false;
        if(customer.getCustomerID() == 3 && checkExist != null) {
            return password.equals("admin"); // (Customer | Password) (Admin | admin)
        }
        if(checkExist != null) {
            return password.equals("1234"); // (Customer | Password) (Jake | 1234)
        }
        return false;
    }
}
