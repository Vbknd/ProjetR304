package joueur;

import plateau.Plateau;
import verification.VerifierVictoire;

public class BotMinimax implements Joueur {
    private final int profondeurMax;

    public BotMinimax(int profondeurMax) {
        if (profondeurMax <= 0) {
            throw new IllegalArgumentException("La profondeur maximale doit être supérieure à 0.");
        }
        this.profondeurMax = profondeurMax;
    }

    public int getProfondeurMax() {
        return profondeurMax;
    }

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        // Appelle la logique Minimax avec élagage pour choisir un coup
        int[] meilleurCoup = minimax(plateau, symboleIA, symboleAdversaire, profondeurMax, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (meilleurCoup == null) {
            throw new IllegalStateException("Aucun coup légal disponible pour le BotMinimax.");
        }

        char colonne = (char) ('A' + meilleurCoup[1]);
        int ligne = meilleurCoup[0] + 1;

        return colonne + "" + ligne;
    }

    private int[] minimax(Plateau plateau, char symboleIA, char symboleAdversaire, int profondeur, boolean maximiser, int alpha, int beta) {
        VerifierVictoire verifierVictoire = new VerifierVictoire(plateau);

        if (profondeur == 0 || estPlateauPlein(plateau)) {
            return new int[]{-1, -1, evaluerPlateau(plateau, symboleIA, symboleAdversaire)};
        }

        int meilleurScore = maximiser ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] meilleurCoup = null;

        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {
                    // Simuler le coup
                    plateau.placerPierre(i, j, maximiser ? symboleIA : symboleAdversaire);



                    if (verifierVictoire.aGagne(i, j, maximiser ? symboleIA : symboleAdversaire)) {
                        plateau.getGrille()[i][j] = '.'; // Annuler le coup
                        return new int[]{i, j, maximiser ? 10000 : -10000}; // Score maximal pour une victoire
                    }

                    // Calculer le score avec Minimax
                    int score = minimax(plateau, symboleIA, symboleAdversaire, profondeur - 1, !maximiser, alpha, beta)[2];

                    // Annuler le coup
                    plateau.getGrille()[i][j] = '.';

                    // Maximiser ou minimiser selon le joueur
                    if (maximiser) {
                        if (score > meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{i, j, score};
                        }
                        alpha = Math.max(alpha, meilleurScore);
                    } else {
                        if (score < meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{i, j, score};
                        }
                        beta = Math.min(beta, meilleurScore);
                    }

                    // Élagage
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return meilleurCoup == null ? new int[]{-1, -1, evaluerPlateau(plateau, symboleIA, symboleAdversaire)} : meilleurCoup;
    }

    private boolean estPlateauPlein(Plateau plateau) {
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.getGrille()[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluerPlateau(Plateau plateau, char symboleIA, char symboleAdversaire) {
        int score = 0;

        // Pondération des positions centrales
        int taille = plateau.getTaille();
        int centre = taille / 2;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (plateau.getGrille()[i][j] == symboleIA) {
                    score += 10; // Chaque pierre IA vaut +10
                    if (Math.abs(i - centre) <= 1 && Math.abs(j - centre) <= 1) {
                        score += 5; // Bonus pour les positions centrales
                    }
                } else if (plateau.getGrille()[i][j] == symboleAdversaire) {
                    score -= 10; // Chaque pierre adverse vaut -10
                    if (Math.abs(i - centre) <= 1 && Math.abs(j - centre) <= 1) {
                        score -= 5; // Malus pour les positions centrales occupées par l'adversaire
                    }
                }
            }
        }

        // Ajout de bonus pour alignements partiels
        score += evaluerAlignementsPartiels(plateau, symboleIA, symboleAdversaire);

        return score;
    }


    private int evaluerAlignementsPartiels(Plateau plateau, char symboleIA, char symboleAdversaire) {
        int score = 0;

        // Vérification des lignes, colonnes et diagonales
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                // Vérification des alignements pour l'IA
                score += calculerScoreAlignement(plateau, i, j, symboleIA);

                // Vérification des alignements pour l'adversaire (malus)
                score -= calculerScoreAlignement(plateau, i, j, symboleAdversaire);
            }
        }

        return score;
    }

    private int calculerScoreAlignement(Plateau plateau, int ligne, int colonne, char symbole) {
        int score = 0;

        // Alignement horizontal
        score += verifierAlignement(plateau, ligne, colonne, 0, 1, symbole);

        // Alignement vertical
        score += verifierAlignement(plateau, ligne, colonne, 1, 0, symbole);

        // Alignement diagonal (descendant)
        score += verifierAlignement(plateau, ligne, colonne, 1, 1, symbole);

        // Alignement diagonal (montant)
        score += verifierAlignement(plateau, ligne, colonne, 1, -1, symbole);

        return score;
    }

    private int verifierAlignement(Plateau plateau, int ligne, int colonne, int deltaLigne, int deltaColonne, char symbole) {
        int score = 0;
        int taille = plateau.getTaille();
        int alignement = 0;
        int casesVides = 0;

        for (int k = 0; k < 4; k++) { // Vérifie jusqu'à 4 cases pour les alignements
            int x = ligne + k * deltaLigne;
            int y = colonne + k * deltaColonne;

            if (x >= 0 && x < taille && y >= 0 && y < taille) {
                if (plateau.getGrille()[x][y] == symbole) {
                    alignement++;
                } else if (plateau.getGrille()[x][y] == '.') {
                    casesVides++;
                }
            }
        }

        // Calcul du score en fonction du nombre d'alignements et de cases vides
        if (alignement == 3 && casesVides == 1) {
            score += 100; // Alignement presque complet
        } else if (alignement == 2 && casesVides == 2) {
            score += 10; // Alignement partiel intéressant
        } else if (alignement == 1 && casesVides == 3) {
            score += 1; // Faible alignement
        }

        return score;
    }




}
