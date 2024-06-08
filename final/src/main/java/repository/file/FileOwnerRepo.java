package repository.file;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import domain.Owner;
import domain.domainException.IncorrectOwnerInformationException;
import repository.OwnerRepo;

public class FileOwnerRepo implements OwnerRepo {
    private String filename = "E:\\Study folder\\Year 1\\Term 2\\INT103\\project\\final\\src\\main\\java\\repository\\file\\ownerFile.txt";
    private List<Owner> owners;
    private int nextID;

    public FileOwnerRepo(String filename) {
        File file = new File(filename);
        if(file.exists()) {
            try (FileInputStream fis = new FileInputStream(filename);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);) {
                owners = (List<Owner>) ois.readObject();
                nextID = ois.readInt();
            }
            catch (IOException io) { io.printStackTrace(); }
            catch (ClassNotFoundException cnf) { cnf.printStackTrace(); }
        } else {
            this.owners = new LinkedList<>();
            nextID = 1;
        }
    }

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
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                ) {
                    oos.writeObject(owners);
                    oos.writeInt(nextID);
                }
            catch (IOException io) { io.printStackTrace(); }
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
        try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                ) {
                    oos.writeObject(owners);
                    oos.writeInt(nextID);
                }
        catch (IOException io) { io.printStackTrace(); }
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
            try (FileOutputStream fos = new FileOutputStream(filename);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                ) {
                    oos.writeObject(owners);
                    oos.writeInt(nextID);
                }
            catch (IOException io) { io.printStackTrace(); }
            return true;
        }
        return false;
    }

    @Override
    public Stream<Owner> stream() {
        return owners.stream();
    }
}
