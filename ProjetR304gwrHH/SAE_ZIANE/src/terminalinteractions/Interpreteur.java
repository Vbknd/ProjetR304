package terminalinteractions;

import plateau.Plateau;
import commandes.*;
import ia.*;
import java.util.Scanner;

public class Interpreteur {
    private final Plateau plateau;
    private final GestionnaireCommandes gestionnaire;
    private int numCommande = 0;

    public Interpreteur() {
        this.plateau = new Plateau(7); // Plateau par défaut
        this.gestionnaire = new GestionnaireCommandes();
    }

    public void lancer() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(++numCommande + " ");
            String commande = scanner.nextLine().trim();
            if (traiterCommande(commande)) break;
        }

        scanner.close();
    }

    private boolean traiterCommande(String commande) {
        commande = commande.trim();
        try {
            if (commande.isEmpty()) {
                System.out.println("?" + numCommande + " invalid command");
                return false;
            }

            if (commande.startsWith("boardsize")) {
                return traiterBoardSize(commande);
            } else if (commande.equals("clear_board")) {
                return traiterClearBoard();
            } else if (commande.startsWith("play")) {
                return traiterPlay(commande);
            } else if (commande.equals("showboard")) {
                return traiterShowBoard();
            } else if (commande.startsWith("genmove")) {
                return traiterGenMove(commande);
            } else if (commande.equals("quit")) {
                return traiterQuit();
            } else {
                System.out.println("?" + numCommande + " unknown command");
                return false;
            }
        } catch (Exception e) {
            System.out.println("?" + numCommande + " error: " + e.getMessage());
            return false;
        }
    }

    private boolean traiterBoardSize(String commande) {
        int taille = Integer.parseInt(commande.split(" ")[1]);
        Commande definirTaille = new DefinirTaillePlateauCommande(plateau, taille);
         gestionnaire.executerCommande(definirTaille);
        System.out.println("=" + numCommande);
        return false;
    }

    private boolean traiterClearBoard() {
        Commande reinitialiser = new ReinitialiserPlateauCommande(plateau);
        gestionnaire.executerCommande(reinitialiser);
        System.out.println("=" + numCommande);
        return false;
    }

    private boolean traiterPlay(String commande) {
        String[] parties = commande.split(" ");

        String couleur = parties[1];
        String position = parties[2];

        int[] coords = parsePosition(position);
        if (!plateau.estCoupLegal(coords[0], coords[1])) {
            System.out.println("?" + numCommande + " illegal move");
            return false;
        }

        Commande jouer = new JouerCommande(plateau, couleur, position);
        String c =gestionnaire.executerCommande(jouer);
        System.out.println("=" + numCommande + " " + c);
        return false;
    }

    private boolean traiterShowBoard() {
        System.out.println("=" + numCommande);
        System.out.println(plateau + "\n"); //+ plateau.getCaptureInfo());
        return false;
    }

    private boolean traiterGenMove(String commande) {
        try {

            String[] parties = commande.split(" ");

            String couleur = parties[1];
            if (!couleur.equals("black") && !couleur.equals("white")) {
                System.out.println("?" + numCommande + " invalid color");
                return false;
            }


            char symboleIA = couleur.equals("black") ? 'X' : 'O';
            char symboleAdversaire = couleur.equals("black") ? 'O' : 'X';


            IA ia = new IAMoyenne();
            Commande genMove = new GenMoveCommande(plateau, ia, symboleIA, symboleAdversaire);


            String c = gestionnaire.executerCommande(genMove);

            // Affiche la réponse correctement formatée
            System.out.println("=" + numCommande + " " + c.trim());
            return false;
        } catch (Exception e) {
            System.out.println("?" + numCommande + " error: " + e.getMessage());
            return false;
        }
    }

    private boolean traiterQuit() {
        System.out.println("="+ numCommande);
        return true;
    }

    private int[] parsePosition(String position) {
        try {
            char colonne = position.charAt(0);
            int ligne = Integer.parseInt(position.substring(1)) - 1;

            if (ligne < 0 || ligne >= plateau.getTaille() || colonne < 'A' || colonne >= 'A' + plateau.getTaille()) {
                throw new IllegalArgumentException("Position hors limites");
            }

            return new int[]{ligne, colonne - 'A'};
        } catch (Exception e) {
            throw new IllegalArgumentException("Format de position invalide");
        }
}
}
