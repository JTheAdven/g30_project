package domain;

import domain.domainException.IncorrectOwnerInformationException;

public class Owner {
    private final int ownerID;
    private String ownerName;
    private double ownerMoney;

    public Owner(int ownerID, String ownerName, double ownerMoney) throws IncorrectOwnerInformationException {
        if(ownerID < 0 || ownerName == null || ownerName.isBlank()
        || ownerMoney < 0) throw new IncorrectOwnerInformationException();
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.ownerMoney = ownerMoney;
    }

    public int getOwnerID() { return ownerID; }
    public String getOwnerName() { return ownerName; }
    public double getOwnerMoney() { return ownerMoney; }
    public void setOwnerName(String ownerName) throws IncorrectOwnerInformationException {
        if(ownerName == null || ownerName.isBlank()) {
            throw new IncorrectOwnerInformationException("Owner name must not be \"null\""
            + ", or blank! (This includes whitespaces without a character or number)");
        }
        this.ownerName = ownerName;
    }
    public void setOwnerMoney(double ownerMoney) throws IncorrectOwnerInformationException {
        if(ownerMoney < 0) {
            throw new IncorrectOwnerInformationException("Onwer money must not be less than 0.00!");
        }
        this.ownerMoney = ownerMoney;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ownerID;
        result = prime * result + ((ownerName == null) ? 0 : ownerName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(ownerMoney);
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
        Owner other = (Owner) obj;
        if (ownerID != other.ownerID)
            return false;
        if (ownerName == null) {
            if (other.ownerName != null)
                return false;
        } else if (!ownerName.equals(other.ownerName))
            return false;
        if (Double.doubleToLongBits(ownerMoney) != Double.doubleToLongBits(other.ownerMoney))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Owner [ID: " + ownerID + ", ownerName: " + ownerName + ", ownerMoney: " + ownerMoney + "]";
    }
}
