package repository;

import java.util.stream.Stream;
import domain.Owner;
import domain.domainException.IncorrectOwnerInformationException;

public interface OwnerRepo {
    public Owner get(int ownerID);
    public Owner create(String ownerName, double ownerMoney) throws IncorrectOwnerInformationException;
    public boolean update(Owner owner, String ownerName, double ownerMoney) throws IncorrectOwnerInformationException;
    public boolean remove(Owner owner);
    public Stream<Owner> stream();
}
