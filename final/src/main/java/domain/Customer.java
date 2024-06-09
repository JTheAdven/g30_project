package domain;

import java.io.Serializable;
import domain.domainException.IncorrectCustomerInformationException;

public class Customer implements Serializable {
    private final int customerID;
    private String customerName;
    private double customerMoney;

    public Customer(int customerID, String customerName, double customerMoney) throws IncorrectCustomerInformationException {
        if(customerID < 0 || customerName == null || customerName.isBlank()
        || customerMoney < 0) throw new IncorrectCustomerInformationException();
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
