package org.server;

import org.fimproso.OperationBancaire;
import org.fimproso.OperationBancaireHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class CORBAServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            POA poa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA")
            );
            poa.the_POAManager().activate();

            BankServant servant = new BankServant();
            servant.setOrb(orb);

            org.omg.CORBA.Object ref =
                poa.servant_to_reference(servant);
            OperationBancaire href =
                OperationBancaireHelper.narrow(ref);

            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            NameComponent path[] =
                ncRef.to_name("OperationBancaire");

            ncRef.rebind(path, href);

            System.out.println("Server ready and waiting...");
            orb.run();

        } catch (Exception e) {
            System.out.println(
                "Erreur lors de l ouverture du serveur " + e
            );
        }
    }
}
