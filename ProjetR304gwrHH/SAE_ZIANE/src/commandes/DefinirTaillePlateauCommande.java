package commandes;

import plateau.Plateau;

public class DefinirTaillePlateauCommande implements Commande {
    private Plateau plateau;
    private int taille;

    public DefinirTaillePlateauCommande(Plateau plateau, int taille) {
        if (taille <= 5 || taille > 19) {
            throw new IllegalArgumentException("La taille du plateau doit être comprise entre 1 et 19.");
        }
        this.plateau = plateau;
        this.taille = taille;
    }

    @Override
    public String executer() {
        try {
            plateau.definirTaille(taille);
            return "";
        } catch (IllegalArgumentException e) {
            return "? " + e.getMessage();
        }
    }
}
