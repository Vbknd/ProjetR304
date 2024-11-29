package commandes;

import ia.IA;
import plateau.Plateau;

public class GenMoveCommande implements Commande {
    private final Plateau plateau;
    private final IA ia;
    private final char symboleIA;
    private final char symboleAdversaire;

    public GenMoveCommande(Plateau plateau, IA ia, char symboleIA, char symboleAdversaire) {
        this.plateau = plateau;
        this.ia = ia;
        this.symboleIA = symboleIA;
        this.symboleAdversaire = symboleAdversaire;
    }

    @Override
    public String executer() {
        try {
            String coup = ia.jouer(plateau, symboleIA, symboleAdversaire);
            return "=" + coup;
        } catch (Exception e) {
            return "? " + e.getMessage();
        }
    }
}
