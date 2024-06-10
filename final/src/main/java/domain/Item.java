package domain;

import java.io.Serializable;
import domain.domainException.*;

public class Item implements Serializable {
    private final int itemID;
    private String itemName;
    private double itemPrice;
    private int itemAmount;
    private String itemDesc;

    public Item(int itemID, String itemName, double itemPrice,
    int itemAmount, String itemDesc) throws IncorrectItemInformationException {
        if(itemID < 0 || itemName == null || itemName.isBlank()
        || itemPrice < 0 || itemAmount < 0 || itemDesc == null
        || itemDesc.isBlank()) throw new IncorrectItemInformationException(
            """
            Something went wrong! Please ensure that your input are correct by following these guideline:
            1. itemID must be a positive number. (Example: 12, 50. Not 0, -1, 2.5, etc.)
            2. itemName must not be null, blank or only whitespaces. (Example: "Jake", "Kale". Not "", "   ", or null)
            3. itemPrice must be a positive integer. (Example: 100.49, 66. Not -15, -1.25, etc.)
            4. itemAmount must be a positive number. (Example: 70, 1. Not -46, 31.1, etc.)
            5. itemDesc must not be null, blank or only whitespaces. (Example: "Jean", "Pete". Not "", " ", or null)
            If you still encounter this error, please report this to us.
            (Please note that sometimes, this error can occur when you did not input anything. \n
            When that happens, kindly and carefully check the log below to see what went wrong,\n
            or sent this log to us if you did not wish to tinker it by yourself.)"""
        );
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
        this.itemDesc = itemDesc;
    }

    public int getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }
    public int getItemAmount() {return itemAmount; }
    public String getItemDesc() { return itemDesc; }
    public void setItemName(String itemName) throws IncorrectItemInformationException {
        if(itemName == null || itemName.isBlank()) {
            throw new IncorrectItemInformationException("Item name must not be \"null\""
            + ", or blank! (This includes whitespaces without a character or number)");
        }
        this.itemName = itemName;
    }
    public void setItemPrice(double itemPrice) throws IncorrectItemInformationException {
        if(itemPrice < 0) {
            throw new IncorrectItemInformationException("Item price must not be less than 0.00!");
        }
        this.itemPrice = itemPrice;
    }
    public void setItemAmount(int itemAmount) throws IncorrectItemInformationException {
        if(itemAmount < 0) {
            throw new IncorrectItemInformationException("The amount of item must not be less than 0!");
        }
        this.itemAmount = itemAmount;
    }
    public void setItemDesc(String itemDesc) throws IncorrectItemInformationException {
        if(itemDesc == null || itemDesc.isBlank()) {
            throw new IncorrectItemInformationException("Item description must not be \"null\""
            + ", or blank! (This includes whitespaces without a character or number)");
        }
        this.itemDesc = itemDesc;
    }
    //Check the amount/stock of item
    public boolean checkItemAmount() throws IncorrectItemInformationException {
        if(itemAmount == 0) return false;
        if(itemAmount > 0) return true;
        throw new IncorrectItemInformationException("The amount of item must not be less than 0!");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + itemID;
        result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(itemPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + itemAmount;
        result = prime * result + ((itemDesc == null) ? 0 : itemDesc.hashCode());
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
        Item other = (Item) obj;
        if (itemID != other.itemID)
            return false;
        if (itemName == null) {
            if (other.itemName != null)
                return false;
        } else if (!itemName.equals(other.itemName))
            return false;
        if (Double.doubleToLongBits(itemPrice) != Double.doubleToLongBits(other.itemPrice))
            return false;
        if (itemAmount != other.itemAmount)
            return false;
        if (itemDesc == null) {
            if (other.itemDesc != null)
                return false;
        } else if (!itemDesc.equals(other.itemDesc))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Item [ID: " + itemID + ", Name: " + itemName + ", Price: " + itemPrice + ", Amount: " + itemAmount + "]";
    }
}
