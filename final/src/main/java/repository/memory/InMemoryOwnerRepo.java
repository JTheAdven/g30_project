package repository.memory;

import java.util.*;
import java.util.stream.Stream;
import domain.Owner;
import domain.domainException.IncorrectOwnerInformationException;
import repository.OwnerRepo;

public class InMemoryOwnerRepo implements OwnerRepo {
    private List<Owner> owners;
    private int nextID = 1;

    public InMemoryOwnerRepo() { this.owners = new LinkedList<>(); }

    @Override
    public Owner get(int ownerID) {
        return owners.stream()
        .filter(o -> o.getOwnerID() == ownerID)
        .findFirst()
        .orElse(null);
    }

    @Override
    public Owner create(String ownerName, double ownerMoney) throws IncorrectOwnerInformationException {
        Owner checkExistingOwner = owners.stream()
        .filter(o -> o.getOwnerName() == ownerName)
        .findFirst()
        .orElse(null);
        if(checkExistingOwner == null) {
            int id = nextID;
            Owner o = new Owner(id, ownerName, ownerMoney);
            owners.add(o);
            nextID++;
            return o;
        }
        return null;
    }

    @Override
    public boolean update(Owner owner, String ownerName, double ownerMoney) throws IncorrectOwnerInformationException {
        if(ownerName == null || ownerName.isBlank()
        || ownerMoney < 0) return false;
        owner.setOwnerName(ownerName);
        owner.setOwnerMoney(ownerMoney);
        return true;
    }

    @Override
    public boolean remove(Owner owner) {
        Owner checkExistingOwner = owners.stream()
        .filter(o -> o.equals(owner))
        .findAny()
        .orElse(null);
        if(checkExistingOwner != null) {
            owners.remove(owner);
            return true;
        }
        return false;
    }

    @Override
    public Stream<Owner> stream() {
        return owners.stream();
    }
    
}
