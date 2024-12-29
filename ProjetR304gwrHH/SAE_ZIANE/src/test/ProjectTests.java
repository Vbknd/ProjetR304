package test;

import org.junit.Test;
import plateau.Plateau;
import terminal.Interpreteur;
import verification.VerifierVictoire;

import static org.junit.Assert.*;

public class ProjectTests {

    // ========================
    // Tests pour Plateau
    // ========================

    @Test
    public void testInitialisationPlateau() {
        Plateau plateau = new Plateau(5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals('.', plateau.getGrille()[i][j]);
            }
        }
    }

    @Test
    public void testReinitialisationPlateau() {
        Plateau plateau = new Plateau(3);

        plateau.placerPierre(0, 0, 'X');
        plateau.reinitialiser();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals('.', plateau.getGrille()[i][j]);
            }
        }
    }

    @Test
    public void testPlacementPierre() {
        Plateau plateau = new Plateau(3);

        assertTrue(plateau.estCoupLegal(1, 1));
        plateau.placerPierre(1, 1, 'O');
        assertFalse(plateau.estCoupLegal(1, 1));
        assertEquals('O', plateau.getGrille()[1][1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlacementHorsLimite() {
        Plateau plateau = new Plateau(3);
        plateau.placerPierre(3, 3, 'X'); // Hors limite
    }

    // ========================
    // Tests pour VerifierVictoire
    // ========================

    @Test
    public void testVictoireHorizontale() {
        Plateau plateau = new Plateau(5);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(2, 0, 'X');
        plateau.placerPierre(2, 1, 'X');
        plateau.placerPierre(2, 2, 'X');
        plateau.placerPierre(2, 3, 'X');

        assertTrue(verifier.aGagne(2, 3, 'X'));
    }

    @Test
    public void testVictoireVerticale() {
        Plateau plateau = new Plateau(5);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(0, 1, 'O');
        plateau.placerPierre(1, 1, 'O');
        plateau.placerPierre(2, 1, 'O');
        plateau.placerPierre(3, 1, 'O');

        assertTrue(verifier.aGagne(3, 1, 'O'));
    }

    @Test
    public void testVictoireDiagonaleMontante() {
        Plateau plateau = new Plateau(5);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(3, 0, 'X');
        plateau.placerPierre(2, 1, 'X');
        plateau.placerPierre(1, 2, 'X');
        plateau.placerPierre(0, 3, 'X');

        assertTrue(verifier.aGagne(0, 3, 'X'));
    }

    @Test
    public void testVictoireDiagonaleDescendante() {
        Plateau plateau = new Plateau(5);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(0, 0, 'O');
        plateau.placerPierre(1, 1, 'O');
        plateau.placerPierre(2, 2, 'O');
        plateau.placerPierre(3, 3, 'O');

        assertTrue(verifier.aGagne(3, 3, 'O'));
    }

    @Test
    public void testPasDeVictoire() {
        Plateau plateau = new Plateau(5);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(0, 0, 'X');
        plateau.placerPierre(0, 1, 'O');
        plateau.placerPierre(0, 2, 'X');
        plateau.placerPierre(0, 3, 'O');

        assertFalse(verifier.aGagne(0, 3, 'X'));
        assertFalse(verifier.aGagne(0, 3, 'O'));
    }

    // ========================
    // Tests pour Interpreteur
    // ========================

    @Test
    public void testDefinirTaillePlateau() {
        Interpreteur interpreteur = new Interpreteur();

        assertFalse(interpreteur.traiterCommande("set_player white human"));
        assertFalse(interpreteur.traiterCommande("set_player black human"));
        assertFalse(interpreteur.traiterCommande("boardsize 7"));
    }

    @Test
    public void testReinitialiserPlateau() {
        Interpreteur interpreteur = new Interpreteur();
        assertFalse(interpreteur.traiterCommande("set_player white human"));
        assertFalse(interpreteur.traiterCommande("set_player black human"));
        assertFalse(interpreteur.traiterCommande("boardsize 5"));
        assertFalse(interpreteur.traiterCommande("play white A1"));

        assertFalse(interpreteur.traiterCommande("clear_board"));
    }

    @Test
    public void testJouerCommande() {
        Interpreteur interpreteur = new Interpreteur();

        assertFalse(interpreteur.traiterCommande("set_player white human"));
        assertFalse(interpreteur.traiterCommande("set_player black human"));
        assertFalse(interpreteur.traiterCommande("boardsize 3"));
        assertFalse(interpreteur.traiterCommande("play white A1"));

        assertFalse(interpreteur.traiterCommande("play black B2"));
    }

    @Test
    public void testGenMoveCommande() {
        Interpreteur interpreteur = new Interpreteur();

        assertFalse(interpreteur.traiterCommande("set_player white minimax 2"));
        assertFalse(interpreteur.traiterCommande("set_player black human"));
        assertFalse(interpreteur.traiterCommande("boardsize 3"));
        assertFalse(interpreteur.traiterCommande("genmove white"));
    }

    @Test
    public void testCommandeInvalide() {
        Interpreteur interpreteur = new Interpreteur();

        assertFalse(interpreteur.traiterCommande("invalid command"));
    }

    // ========================
    // Méthodes utilitaires
    // ========================

    private int[] parsePosition(String position) {
        char colonne = position.charAt(0);
        int ligne = Integer.parseInt(position.substring(1)) - 1;
        return new int[]{ligne, colonne - 'A'};
    }
}
