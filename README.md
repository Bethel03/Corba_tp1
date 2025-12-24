#  CORBA Bank Demo (Docker)

Un projet de démonstration d'une application bancaire distribuée utilisant **CORBA** avec communication inter-langage (Java ↔ Python) et orchestration complète en **Docker**.



---

##  Objectif du Projet

Créer une architecture bancaire distribuée où:
-  **Un serveur Java** gère les opérations bancaires via CORBA
-  **Un client Java** teste les opérations
-  **Un client Python** communique avec le même serveur Java

Tout ça sans installer quoi que ce soit sur le host machine grâce à Docker !

---

##  Architecture

### Services
| Service | Technologie | Port | Rôle |
|---------|-------------|------|------|
| **corba-nameservice** | omniNames 4.3.2 | 1050 | Annuaire CORBA |
| **serveur-banque-java** | Java (OpenJDK 8) | - | Servant bancaire |
| **client-banque-java** | Java (OpenJDK 8) | - | Consumer Java |
| **client-banque-python** | Python + omniORBpy 4.3.2 | - | Consumer Python |

### IDL
```
OperationsBancaires.idl  ← Contrat partagé entre tous les services
                         ← Stubs générés automatiquement au build
```

---

##  Prérequis

 **Docker** avec le plugin compose  
 Rien d'autre à installer sur la machine hôte

```bash
docker --version
docker compose version
```

---

##  Démarrage Rapide

### Lancer tout d'un coup

```bash
docker compose up --build
```

### Ordre d'exécution automatique
1. **corba-nameservice** démarre → écoute sur `1050`
2. **serveur-banque-java** démarre → enregistre le servant `OperationBancaire`
3. **client-banque-java** exécute les opérations
4. **client-banque-python** teste la même API en Python

Résultat: Votre output doit afficher le solde → dépôt → retrait pour chaque client.

### Arrêter et nettoyer

```bash
docker compose down -v
```

---

##  Configuration Détaillée

### Génération des Stubs
- **Java**: `idlj -fall OperationsBancaires.idl`
- **Python**: `omniidl -bpython OperationsBancaires.idl`
-  Effectuée automatiquement dans les `Dockerfile`

### Endpoints CORBA
```
corba-nameservice:1050    ← Tous les services s'y connectent
```

### Paramètres JVM
```
-ORBInitialPort 1050 -ORBInitialHost corba-nameservice
```

### Paramètres Python
```python
corbaname::corba-nameservice:1050
```

---

##  Conseils & Astuces

 **Modification du code?** Relancer `docker compose up --build` pour régénérer les stubs.

 **Tester localement?** Tous les `Dockerfile` copient la lib omniORB au build, zéro dépendance externe.

 **Débugage?** Les logs sont en stdout du compose:
```bash
docker compose logs -f
```

---


---

##  Points Forts

 **Polyglotte**: Java et Python sur le même bus CORBA  
 **Containerisé**: Zéro installation hôte  
 **Automatisé**: Génération des stubs au build  
 **Scalable**: Architecture distribuée dès le départ  
 **Pédagogique**: Parfait pour comprendre les middlewares RPC

---
