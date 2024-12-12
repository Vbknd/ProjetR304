package commandes;

import ia.IA;
import joueur.Joueur;
import plateau.Plateau;

public class GenMoveCommande implements Commande {
    private final Plateau plateau;
    private final Joueur joueur;
    private final char symboleIA;
    private final char symboleAdversaire;

    public GenMoveCommande(Plateau plateau, Joueur joueur, char symboleIA, char symboleAdversaire) {
        this.plateau = plateau;
        this.joueur = joueur;
        this.symboleIA = symboleIA;
        this.symboleAdversaire = symboleAdversaire;
    }

    @Override
    public String executer() {
        try {

            if (!(symboleIA == 'X' || symboleIA == 'O')) {
                return "? invalid color"; }

<<<<<<< HEAD
            String coup = joueur.jouer(plateau, symboleIA, symboleAdversaire);
=======
            String coup = ia.jouer(plateau, symboleIA, symboleAdversaire);
>>>>>>> origin/main

            if (coup == null || coup.isEmpty()) {
                return "? no valid moves";
            }
            return " "+coup;
        } catch (Exception e) {
            return "? " + e.getMessage();
        }
    }
}
