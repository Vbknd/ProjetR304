package terminalinteractions;

import plateau.Plateau;
import commandes.*;
import ia.*;
import java.util.Scanner;

public class Interpreteur {
    private final Plateau plateau;
    private final GestionnaireCommandes gestionnaire;

    public Interpreteur() {
        this.plateau = new Plateau(7); // Plateau par défaut de taille 7x7
        this.gestionnaire = new GestionnaireCommandes();
    }

    public void lancer() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Bienvenue dans le jeu de plateau !");
        System.out.println("Choisissez un mode de jeu :");
        System.out.println("1. Jouer contre un autre joueur");
        System.out.println("2. Jouer contre l'IA");
        System.out.print("> ");
        String modeJeu = scanner.nextLine();

        if (modeJeu.equals("1")) {
            lancerModeJoueurVsJoueur(scanner);
        } else if (modeJeu.equals("2")) {
            lancerModeJoueurVsIA(scanner);
        } else {
            System.out.println("Choix invalide. Veuillez relancer le programme.");

        }



        System.out.println("Programme terminé. Merci d'avoir joué !");
        scanner.close();
    }

    private void lancerModeJoueurVsJoueur(Scanner scanner) {
        System.out.println("Mode Joueur contre Joueur sélectionné.");
        boolean partieTerminee = false;

        while (!partieTerminee) {
            // Tour du joueur 1
            System.out.print("Joueur 1 (Noir) : ");
            String ligne = scanner.nextLine();
            partieTerminee = traiterCommande(ligne, 'X');

            // Tour du joueur 2
            if (!partieTerminee) {
                System.out.print("Joueur 2 (Blanc) : ");
                ligne = scanner.nextLine();
                partieTerminee = traiterCommande(ligne, 'O');
            }
        }
    }

    private void lancerModeJoueurVsIA(Scanner scanner) {
        System.out.println("Mode Joueur contre IA sélectionné.");
        System.out.println("Choisissez votre couleur (tapez 'noir' ou 'blanc') :");
        System.out.print("> ");
        String choixCouleur = scanner.nextLine().trim(); // Nettoyage

        char symboleJoueur, symboleIA;
        boolean joueurCommence;

        if (choixCouleur.equals("noir")) {
            symboleJoueur = 'X';
            symboleIA = 'O';
            joueurCommence = true;
        } else if (choixCouleur.equals("blanc")) {
            symboleJoueur = 'O';
            symboleIA = 'X';
            joueurCommence = false;
        } else {
            System.out.println("Choix invalide. Vous jouerez noir par défaut.");
            symboleJoueur = 'X';
            symboleIA = 'O';
            joueurCommence = true;
        }

        System.out.println("Choisissez un niveau de difficulté pour l'IA :");
        System.out.println("1. Facile");
        System.out.println("2. Moyen");
        System.out.println("3. Difficile");
        System.out.print("> ");
        String niveau = scanner.nextLine().trim(); // Nettoyage

        IA ia;
        switch (niveau) {
            case "1":
                ia = new IAFacile();
                break;
            case "2":
                ia = new IAMoyenne();
                break;
            case "3":
                ia = new IADifficile();
                break;
            default:
                System.out.println("Niveau invalide. Difficulté par défaut : Facile.");
                ia = new IAFacile();
        }

        boolean partieTerminee = false;

        while (!partieTerminee) {
            if (joueurCommence) {
                // Tour du joueur
                System.out.print("Votre tour : ");
                String ligne = scanner.nextLine().trim(); // Nettoyage
                partieTerminee = traiterCommande(ligne, symboleJoueur);

                // Si la partie n'est pas terminée, l'IA joue
                if (!partieTerminee) {
                    System.out.println("IA réfléchit...");
                    String coupIA = ia.jouer(plateau, symboleIA, symboleJoueur).trim(); // Nettoyage IA
                    System.out.println("IA joue : " + coupIA);
                    partieTerminee = traiterCommande("play " + (symboleIA == 'X' ? "noir" : "blanc") + " " + coupIA, symboleIA);
                }
            } else {
                // Tour de l'IA
                System.out.println("IA réfléchit...");
                String coupIA = ia.jouer(plateau, symboleIA, symboleJoueur).trim(); // Nettoyage IA
                System.out.println("IA joue : " + coupIA);
                partieTerminee = traiterCommande("play " + (symboleIA == 'X' ? "noir" : "blanc") + " " + coupIA, symboleIA);

                // Si la partie n'est pas terminée, le joueur joue
                if (!partieTerminee) {
                    System.out.print("Votre tour : ");
                    String ligne = scanner.nextLine().trim(); // Nettoyage
                    partieTerminee = traiterCommande(ligne, symboleJoueur);
                }
            }
        }
    }



    private boolean traiterCommande(String commande, char symbole) {
        commande = commande.trim(); // Nettoie la commande avant de la traiter
        System.out.println("Commande reçue : [" + commande + "]");

        try {
            if (commande.isEmpty()) {
                System.out.println("? Commande vide ignorée");
                return false;
            }

            if (commande.startsWith("boardsize")) {
                return traiterBoardSize(commande);
            } else if (commande.equals("clear_board")) {
                return traiterClearBoard();
            } else if (commande.startsWith("play")) {
                return traiterPlay(commande, symbole);
            } else if (commande.equals("showboard")) {
                return traiterShowBoard();
            } else if (commande.startsWith("genmove ")) {
                return traiterGenMove(commande);
            } else if (commande.equals("quit")) {
                return traiterQuit();
            } else {
                System.out.println("? Commande inconnue");
                return false;
            }
        } catch (Exception e) {
            System.out.println("? Erreur : " + e.getMessage());
            return false;
        }
    }


    private boolean traiterBoardSize(String commande) {
        int taille = Integer.parseInt(commande.split(" ")[1]);
        Commande definirTaille = new DefinirTaillePlateauCommande(plateau, taille);
        System.out.println(gestionnaire.executerCommande(definirTaille));
        return false;
    }

    private boolean traiterClearBoard() {
        Commande reinitialiser = new ReinitialiserPlateauCommande(plateau);
        System.out.println(gestionnaire.executerCommande(reinitialiser));
        return false;
    }

    private boolean traiterPlay(String commande, char symbole) {
        String[] parties = commande.split(" ");
        if (parties.length != 3) {
            System.out.println("? Commande invalide pour jouer. Utilisez : jouer [couleur] [position]");
            return false;
        }

        String couleur = parties[1];
        String position = parties[2];

        if (!couleurValide(couleur, symbole)) {
            System.out.println("Vous ne pouvez jouer qu'avec vos propres pions (" + (symbole == 'X' ? "Noir" : "Blanc") + ").");
            return false;
        }

        int[] coords = parsePosition(position);
        if (!plateau.estCoupLegal(coords[0], coords[1])) {
            System.out.println("La case " + position + " est déjà occupée.");
            return false;
        }

        Commande jouer = new JouerCommande(plateau, couleur, position);
        String resultat = gestionnaire.executerCommande(jouer);
        System.out.println(resultat);

        if (resultat.contains("a gagné")) {
            return gererFinPartie();
        }

        return false;
    }

    private boolean traiterShowBoard() {
        System.out.println("= \n" + plateau);
        return false;
    }

    private boolean traiterGenMove(String commande) {
        String couleur = commande.split(" ")[1];
        char symboleIA = couleur.equals("noir") ? 'X' : 'O';
        char symboleAdversaire = couleur.equals("noir") ? 'O' : 'X';

        IA ia = new IAFacile();
        String coupIA = ia.jouer(plateau, symboleIA, symboleAdversaire);
        System.out.println("=" + coupIA);
        return false;
    }

    private boolean traiterQuit() {
        System.out.println("=8");
        System.out.println("Merci d'avoir joué !");
        return true;
    }

    private boolean couleurValide(String couleur, char symbole) {
        return (symbole == 'X' && couleur.equals("noir")) || (symbole == 'O' && couleur.equals("blanc"));
    }

    private boolean gererFinPartie() {
        System.out.println("\nQue voulez-vous faire ?");
        System.out.println("1. Rejouer");
        System.out.println("2. Quitter");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        String choix = scanner.nextLine();

        if (choix.equals("1")) {
            plateau.reinitialiser();
            System.out.println("Nouvelle partie !");
            System.out.println(plateau);
            return false;
        } else {
            System.out.println("Merci d'avoir joué !");
            return true;
        }
    }


    private int[] parsePosition(String position) {
        try {
            char colonne = position.charAt(0);
            int ligne = Integer.parseInt(position.substring(1)) - 1;

            // Vérification des limites
            if (ligne < 0 || ligne >= plateau.getTaille() || colonne < 'A' || colonne >= 'A' + plateau.getTaille()) {
                throw new IllegalArgumentException("Position hors limites : " + position);
            }

            return new int[]{ligne, colonne - 'A'};
        } catch (Exception e) {
            throw new IllegalArgumentException("Format de position invalide : " + position);
        }
    }





}
