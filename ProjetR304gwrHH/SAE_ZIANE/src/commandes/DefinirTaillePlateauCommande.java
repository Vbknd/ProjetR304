package commandes;

import plateau.Plateau;

public class DefinirTaillePlateauCommande implements Commande {
    private Plateau plateau;
    private int taille;

    public DefinirTaillePlateauCommande(Plateau plateau, int taille) {
        this.plateau = plateau;
        this.taille = taille;
    }

    @Override
    public String executer() {
        try {
            plateau.definirTaille(taille);
            return "=1";
        } catch (IllegalArgumentException e) {
            return "? " + e.getMessage();
        }
    }
}
