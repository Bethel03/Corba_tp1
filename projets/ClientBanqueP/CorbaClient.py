import sys

import CosNaming
import CORBA

import OperationsBancaires_idl as idl_module


def resolve_operation_bancaire(orb):
    obj = orb.resolve_initial_references("NameService")
    nc = obj._narrow(CosNaming.NamingContext)
    if nc is None:
        raise RuntimeError("NameService is not a NamingContext")

    name = [CosNaming.NameComponent("OperationBancaire", "")]
    obj_ref = nc.resolve(name)
    return obj_ref._narrow(idl_module._0_org.fimproso.OperationBancaire)


def main():
    orb = CORBA.ORB_init(
        ["-ORBInitRef", "NameService=corbaname::corba-nameservice:1050"],
        CORBA.ORB_ID,
    )

    op = resolve_operation_bancaire(orb)
    print("initial balance:", op.balance())

    amount = 150
    op.depot(amount)
    print("deposited:", amount)
    print("current balance:", op.balance())

    amount = 300
    op.retrait(amount)
    print("withdrew:", amount)
    print("final balance:", op.balance())


if __name__ == "__main__":
    try:
        main()
    except Exception as exc:
        print(f"Erreur lors de l ouverture du serveur {exc}", file=sys.stderr)
        sys.exit(1)
