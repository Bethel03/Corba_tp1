package org.client;

import org.fimproso.OperationBancaire;
import org.fimproso.OperationBancaireHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class CorbaClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            OperationBancaire op =
                OperationBancaireHelper.narrow(
                    ncRef.resolve_str("OperationBancaire")
                );

            System.out.println("initial balance: " + op.balance());

            int amount = 150;
            op.depot(amount);
            System.out.println("deposited: " + amount);
            System.out.println("current balance: " + op.balance());

            amount = 300;
            op.retrait(amount);
            System.out.println("withdrew: " + amount);
            System.out.println("final balance: " + op.balance());

        } catch (Exception e) {
            System.out.println(
                "Erreur lors de l ouverture du serveur " + e
            );
        }
    }
}
