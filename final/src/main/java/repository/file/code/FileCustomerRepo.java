package repository.file.code;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import domain.Customer;
import domain.domainException.IncorrectCustomerInformationException;
import repository.CustomerRepo;

public class FileCustomerRepo implements CustomerRepo {
    private String filename = "E:\\Study folder\\Year 1\\Term 2\\INT103\\project\\final\\src\\main\\java\\repository\\file\\customerFile.txt";
    private List<Customer> customers;
    private int nextID;
    private Map<Customer, String> password;
    
    public FileCustomerRepo(String filename) {
        File file = new File(filename);
        if(file.exists()){
            try (FileInputStream fis = new FileInputStream(filename);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
            ) {
                customers = (List<Customer>) ois.readObject();
                nextID = ois.readInt();
                password = (Map<Customer, String>) ois.readObject();
            }
            catch (IOException io) { io.printStackTrace(); }
            catch (ClassNotFoundException cnf) { cnf.printStackTrace(); }
        }
        else {
            this.customers = new LinkedList<>();
            nextID = 1;
            password = new HashMap<>();
        }
    }

    @Override
    public Customer get(int customerID) {
        return customers.stream()
        .filter(c -> c.getCustomerID() == customerID)
        .findFirst()
        .orElse(null);
    }

    @Override
    public Customer create(String customerName, double customerMoney, String customerPassword) throws IncorrectCustomerInformationException {
        Customer checkExistingCustomer = customers.stream()
        .filter(c -> c.getCustomerName().equals(customerName))
        .findFirst()
        .orElse(null);  
        if(checkExistingCustomer == null) {
            int id = nextID;
            Customer c = new Customer(id, customerName, customerMoney);
            customers.add(c);
            password.put(c, customerPassword);
            nextID++;
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(customers);
                oos.writeInt(nextID);
                oos.writeObject(password);
            }
            catch (IOException io) { io.printStackTrace(); }
            return c;
        }
        return null;
    }

    @Override
    public boolean update(Customer customer, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if (customerName == null || customerName.isBlank() || customerMoney < 0) {
            return false;
        }
        customer.setCustomerName(customerName);
        customer.setCustomerMoney(customerMoney);
        try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(customers);
                oos.writeInt(nextID);
                oos.writeObject(password);
            }
        catch (IOException io) { io.printStackTrace(); }
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
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
            ) {
                oos.writeObject(customers);
                oos.writeInt(nextID);
                oos.writeObject(password);
            } catch (IOException io) {
                io.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public String getPassword(int customerID) {
        Customer customer = customers.get(customerID);
        if(customer != null) {
            return password.get(customer);
        }
        return null;
    }

    @Override
    public Stream<Customer> stream() {
        return customers.stream();
    }
}
