package domain;

import java.io.Serializable;
import domain.domainException.IncorrectCustomerInformationException;

public class Customer implements Serializable {
    private final int customerID;
    private String customerName;
    private double customerMoney;

    public Customer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if(customerID < 0 || customerName == null || customerName.isBlank()
        || customerMoney < 0) throw new IncorrectCustomerInformationException(
            """
            Something went wrong! Please ensure that your input are correct by following these guideline:
            1. customerID must be a positive number. (Example: 12, 50. Not -1, 2.5, etc.)
            2. customerName must not be null, blank or only whitespaces. (Example: "Jake", "Kale". Not "", "   ", or null)
            3. customerMoney must be a positive integer. (Example: 100.49, 66. Not -15, -1.25, etc.)
            If you still encounter this error, please report this to us.
            (Please note that sometimes, this error can occur when you did not input anything. \n
            When that happens, kindly and carefully check the log below to see what went wrong,\n
            or sent this log to us if you did not wish to tinker it by yourself.)"""
        );
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerMoney = customerMoney;
    }

    public int getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public double getCustomerMoney() { return customerMoney; }
    public void setCustomerName(String customerName) throws IncorrectCustomerInformationException {
        if(customerName == null || customerName.isBlank()) {
            throw new IncorrectCustomerInformationException("Customer name must not be \"null\", "
            + "or blank! (This includes whitespace without any character or number)");
        }
        this.customerName = customerName;
    }
    public void setCustomerMoney(double customerMoney) throws IncorrectCustomerInformationException {
        if(customerMoney < 0) {
            throw new IncorrectCustomerInformationException("Customer money must not be lower than 0.00!");
        }
        this.customerMoney = customerMoney;
    }
    
    public boolean buyItem(double itemPrice) {
        if(customerMoney >= itemPrice) {
            customerMoney -= itemPrice;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + customerID;
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(customerMoney);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (customerID != other.customerID)
            return false;
        if (customerName == null) {
            if (other.customerName != null)
                return false;
        } else if (!customerName.equals(other.customerName))
            return false;
        if (Double.doubleToLongBits(customerMoney) != Double.doubleToLongBits(other.customerMoney))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Customer [ID: " + customerID + ", Name: " + customerName + ", Money: " + customerMoney + "]";
    }
}
