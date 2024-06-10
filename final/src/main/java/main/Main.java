package main;

import domain.domainException.*;
import repository.memory.*;
import service.*;
import ui.*;

public class Main {
    public static void main(String[] args) {
        testUI();
    }

    private static void testUI() {
        try {
            InMemoryCustomerRepo cr = new InMemoryCustomerRepo();
            InMemoryItemRepo ir = new InMemoryItemRepo();
            cr.create("Jake", 10000);
            cr.create("Jean", 69.99);
            cr.create("Admin", 0);
            ir.create("Bottle of poison", 2.50, 100, """

                    When come into contact with the substance, you will suffer the chemical reaction between
                    your skin and the substance for a period of time. If not treated right away, you will suffer
                    some injuries and/or die from the aftermath. Works well when you want something dead like weed,
                    insects, etc.""");
            ir.create("Shotgun", 12.99, 20, """
                    
                    When shot, it makes the ammunitions inside the gun burst into a swarm of bullets in front of
                    you. The ammo capacity that you can load in is 10. Works well when you are up close to
                    your enemy.""");
            ir.create("Bottle of water", 1.00, 999, """
                    
                    An essential element for every living beings to stay alive. It also very important when
                    you\'re in a very hot place such as; desert, savanna. It\'s also an ingredient to make various
                    stuff.""");
            ir.create("A loaf of bread", 2.99, 200, """
                    
                    A food source that is well known across the world. It\'s easy to make and fast to produce.
                    It is also an ingredient to make various stuff like sandwiches, breadcrumps, etc.""");
            ir.create("\"Survival Guide\" book", 5.00, 50, """
                    
                    A book containing a guide of how to survive when lost in the wild. It\'s very popular that
                    many copies were sold instantly after its debut. It is also a limited item.""");
            AdjustCustomer ac1 = new AdjustCustomer(cr, 1);
            AdjustCustomer ac2 = new AdjustCustomer(cr, 2);
            AdjustCustomer ac3 = new AdjustCustomer(cr, 3);
            var appCustomer = new BackUI(cr, ir, 2);
            // appCustomer.loginUI();
            var appAdmin = new BackUI(cr, ir, 3);
            // appAdmin.loginUI();
        } catch (IncorrectCustomerInformationException ce) {
            ce.printStackTrace();
        } catch (IncorrectItemInformationException ie) {
            ie.printStackTrace();
        }
    }
}