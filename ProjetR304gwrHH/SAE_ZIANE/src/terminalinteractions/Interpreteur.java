package terminalinteractions;

import joueur.BotMinimax;
import joueur.BotNaif;
import joueur.Joueur;
import joueur.JoueurHumain;
import plateau.Plateau;
import commandes.*;
import verification.VerifierVictoire;

import java.util.Scanner;

public class Interpreteur {
    private final Plateau plateau;
    private final GestionnaireCommandes gestionnaire;
    private int numCommande = 0;
    private Joueur joueurBlanc;
    private Joueur joueurNoir;


    public Interpreteur() {
        this.plateau = new Plateau(7);
        this.gestionnaire = new GestionnaireCommandes();
    }

    public void lancer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("- Exemple : set_player white human");
        System.out.println("- Exemple : set_player black minimax 2");

        while (true) {
            System.out.print(++numCommande + " ");
            String commande = scanner.nextLine().trim();
            if (traiterCommande(commande)) break;
        }

        scanner.close();
    }

    public boolean traiterCommande(String commande) {

        if (joueurBlanc == null || joueurNoir == null) {
            if (!commande.startsWith("set_player")) {
                System.out.println("?" + numCommande + " Vous devez d'abord configurer les joueurs avec 'set_player'.");
                return false;
            }
        }


        commande = commande.trim();
        try {

            if (commande.isEmpty()) {
                System.out.println("?" + numCommande + " invalid command");
                return false;
            }

            if (commande.startsWith("set_player")) {
                return traiterSetPlayer(commande);
            }
            else if (commande.startsWith("boardsize")) {
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
        System.out.println(plateau + "\n");
        return false;
    }

    private boolean traiterGenMove(String commande) {
        try {

            String[] parties = commande.split(" ");
            if (parties.length != 2) {
                System.out.println("?" + numCommande + " invalid genmove command");
                return false;
            }

            String couleur = parties[1];
            if (!couleur.equals("black") && !couleur.equals("white")) {
                System.out.println("?" + numCommande + " invalid color");
                return false;
            }


            Joueur joueur = couleur.equals("black") ? joueurNoir : joueurBlanc;
            char symboleIA = couleur.equals("black") ? 'X' : 'O';
            char symboleAdversaire = couleur.equals("black") ? 'O' : 'X';

            // Exécute GenMoveCommande pour générer le coup
            Commande genMove = new GenMoveCommande(plateau, joueur, symboleIA, symboleAdversaire);
            String coup = genMove.executer().trim();


            int[] coordonnees = parsePosition(coup);
            if (!plateau.estCoupLegal(coordonnees[0], coordonnees[1])) {
                System.out.println("?" + numCommande + " illegal move (case already occupied or out of bounds)");
                return false;
            }

            plateau.placerPierre(coordonnees[0], coordonnees[1], symboleIA);

            VerifierVictoire verifierVictoire = new VerifierVictoire(plateau);
            if (verifierVictoire.aGagne(coordonnees[0], coordonnees[1], symboleIA)) {
                System.out.println("=" + numCommande + " " + symboleIA + " wins!");
                return true;
            }

            System.out.println("=" + numCommande + " " + coup);
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





    private boolean traiterSetPlayer(String commande) {
        String[] parties = commande.split(" ");
        if (parties.length < 3) {
            System.out.println("?" + numCommande + " invalid set_player command");
            return false;
        }

        String couleur = parties[1];
        String typeJoueur = parties[2];
        Joueur joueur = null;

        if (typeJoueur.equals("human")) {
            joueur = new JoueurHumain();
        } else if (typeJoueur.equals("randomBot")) {
            joueur = new BotNaif();
        } else if (typeJoueur.equals("minimax")) {
            if (parties.length != 4) {
                System.out.println("?" + numCommande + " missing depth for minimax");
                return false;
            }
            int profondeur = Integer.parseInt(parties[3]);
            joueur = new BotMinimax(profondeur);
        } else {
            System.out.println("?" + numCommande + " unknown player type");
            return false;
        }

        if (couleur.equals("white")) {
            joueurBlanc = joueur;
        } else if (couleur.equals("black")) {
            joueurNoir = joueur;
        } else {
            System.out.println("?" + numCommande + " invalid color");
            return false;
        }

        System.out.println("=" + numCommande);
        return false;
    }



    private int[] parsePosition(String position) {
        try {
            if (position.length() < 2) {
                throw new IllegalArgumentException("Position trop courte (ex: A1)");
            }

            char colonne = Character.toUpperCase(position.charAt(0));
            int ligne = Integer.parseInt(position.substring(1)) - 1;

            if (colonne < 'A' || colonne >= 'A' + plateau.getTaille() || ligne < 0 || ligne >= plateau.getTaille()) {
                throw new IllegalArgumentException("Position hors limites");
            }

            return new int[]{ligne, colonne - 'A'};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format de position invalide (ex: A1)");
        } catch (Exception e) {
            System.out.println("Erreur dans parsePosition : " + e.getMessage());
            throw e;
        }
    }




}
