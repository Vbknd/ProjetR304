package commandes;

import plateau.Plateau;

public class ReinitialiserPlateauCommande implements Commande {
    private Plateau plateau;

    public ReinitialiserPlateauCommande(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    public String executer() {
        plateau.reinitialiser();
        return "=2";
    }
}
