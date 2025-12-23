package org.server;

import org.fimproso.OperationBancairePOA;
import org.omg.CORBA.ORB;

public class BankServant extends OperationBancairePOA {
    private ORB orb;
    private double amount = 2000;

    public void setOrb(ORB orb){
        this.orb = orb;
    }

    @Override
    public double balance() {
        return amount;
    }

    @Override
    public void depot(double amount) {
        this.amount += amount;
    }

    @Override
    public void retrait(double amount) {
        this.amount -= amount;
    }
}
