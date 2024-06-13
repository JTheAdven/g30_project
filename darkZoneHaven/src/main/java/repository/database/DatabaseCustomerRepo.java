package repository.database;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;
import domain.Customer;
import domain.domainException.IncorrectCustomerInformationException;
import repository.*;

public class DatabaseCustomerRepo implements CustomerRepo {
    private List<Customer> customers;
    private int nextID;

    public DatabaseCustomerRepo() throws IncorrectCustomerInformationException {
        String sql = "SELECT * FROM CUSTOMERS";
        try (Connection connection = DatabaseConnect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            ) {
            customers = new LinkedList<>();
            int count = 0;
            while(results.next()) {
                count++;
                int customerID = results.getInt(1);
                String customerName = results.getString(2);
                double customerMoney = results.getDouble(3);
                customers.add(new Customer(customerID, customerName, customerMoney));
            }
            if (count == 0) {
                nextID = 1;
            }
            else {
                Customer last = customers.get(count-1);
                int number = last.getCustomerID();
                nextID = number + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public Customer create(String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        Customer checkExistingCustomer = customers.stream()
        .filter(c -> c.getCustomerName().equals(customerName))
        .findFirst()
        .orElse(null); 
        if(checkExistingCustomer == null) {
            int id = nextID;
            Customer c = new Customer(id, customerName, customerMoney);
            customers.add(c);
            nextID++;
            String sql = "INSERT INTO CUSTOMERS(customerID, customerName, customerMoney) VALUES (?, ?, ?)";
            try (Connection connection = DatabaseConnect.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, customerName);
                preparedStatement.setDouble(3, customerMoney);
                int result = preparedStatement.executeUpdate();
                if(result == 0) return null;
            } catch (SQLException e) { e.printStackTrace(); }
            return c;
        }
        return null;
    }

    @Override
    public boolean update(Customer customer, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if (customer == null || customerName == null || customerName.isBlank() || customerMoney < 0) {
            return false;
        }
        customer.setCustomerName(customerName);
        customer.setCustomerMoney(customerMoney);
        String sql = "UPDATE CUSTOMERS SET customerName = ?, customerMoney = ? WHERE customerID = ?";
        try (Connection connection = DatabaseConnect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(3, customer.getCustomerID());
                preparedStatement.setString(1, customerName);
                preparedStatement.setDouble(2, customerMoney);
                int result = preparedStatement.executeUpdate();
                if(result == 0) return false;
            }
        catch (SQLException e) { e.printStackTrace(); }
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
            String sql = "DELETE FROM CUSTOMERS WHERE customerID = ?";
            try (Connection connection = DatabaseConnect.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, customer.getCustomerID());
                int result = preparedStatement.executeUpdate();
                if(result == 0) return false;
            } catch (SQLException e) { e.printStackTrace(); }
            return true;
        }
        return false;
    }

    @Override
    public Stream<Customer> stream() {
        return customers.stream();
    }
}
