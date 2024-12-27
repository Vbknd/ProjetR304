package test;

import static org.junit.jupiter.api.Assertions.*;

import commandes.*;

import joueur.BotMinimax;
import joueur.BotNaif;
import joueur.Joueur;
import joueur.JoueurHumain;
import plateau.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterpreteurTest {

    private Plateau plateau;
    private GestionnaireCommandes gestionnaire;

    @BeforeEach
    void setUp() {
        plateau = new Plateau(7);
        gestionnaire = new GestionnaireCommandes();

    }

    @Test
    void testBoardSize() {
        Commande definirTaille = new DefinirTaillePlateauCommande(plateau, 9);
        String resultat = gestionnaire.executerCommande(definirTaille);
        assertEquals("", resultat);
        assertEquals(9, plateau.getTaille());
    }

    @Test
    void testClearBoard() {
        // Place une pierre pour vérifier la réinitialisation
        plateau.placerPierre(3, 3, 'X');
        assertEquals('X', plateau.getGrille()[3][3]);

        Commande reinitialiser = new ReinitialiserPlateauCommande(plateau);
        String resultat = gestionnaire.executerCommande(reinitialiser);
        assertEquals("", resultat);
        assertEquals('.', plateau.getGrille()[3][3]);
    }

    @Test
    void testPlay() {
        Commande jouer = new JouerCommande(plateau, "black", "D4");
        String resultat = gestionnaire.executerCommande(jouer);
        assertEquals("D4", resultat.trim());
        assertEquals('X', plateau.getGrille()[3][3]); // Ligne 3, Colonne 3 (D4)
    }

    @Test
    void testPlayIllegalMove() {
        // Place une pierre pour rendre le coup illégal
        plateau.placerPierre(3, 3, 'X');

        Commande jouer = new JouerCommande(plateau, "black", "D4");
        String resultat = gestionnaire.executerCommande(jouer);
        assertTrue(resultat.startsWith("?"));
    }

    @Test
    void testShowBoard() {
        plateau.placerPierre(0, 0, 'X'); // Place une pierre pour vérifier l'affichage
        String boardRepresentation = plateau.toString();
        assertTrue(boardRepresentation.contains("X")); // Vérifie que la pierre est affichée
    }
    @Test
    void testMinimaxBot() {
        Plateau plateau = new Plateau(6); // Petit plateau 3x3
        BotMinimax bot = new BotMinimax(2); // Profondeur de 2

        // Place des pions pour simuler une situation
        plateau.placerPierre(0, 0, 'X'); // IA
        plateau.placerPierre(1, 1, 'O'); // Adversaire

        String coup = bot.jouer(plateau, 'X', 'O');
        assertNotNull(coup, "Le BotMinimax doit trouver un coup.");
        System.out.println("Coup choisi par BotMinimax : " + coup);
    }

    @Test
    void testSetPlayerHuman() {
        // Plateau fictif pour le contexte
        Plateau plateau = new Plateau(7);

        // Simuler un joueur humain
        Joueur joueurBlanc = new JoueurHumain();
        Joueur joueurNoir = new JoueurHumain();

        assertTrue(joueurBlanc instanceof JoueurHumain, "White player should be JoueurHumain");
        assertTrue(joueurNoir instanceof JoueurHumain, "Black player should be JoueurHumain");
    }

    @Test
    void testSetPlayerBotNaif() {
        // Simuler un bot naïf
        Joueur joueurBlanc = new BotNaif();
        Joueur joueurNoir = new BotNaif();

        assertTrue(joueurBlanc instanceof BotNaif, "White player should be BotNaif");
        assertTrue(joueurNoir instanceof BotNaif, "Black player should be BotNaif");
    }

    @Test
    void testSetPlayerBotMinimax() {
        // Simuler un bot Minimax avec profondeur
        int profondeurBlanc = 3;
        int profondeurNoir = 5;

        BotMinimax joueurBlanc = new BotMinimax(profondeurBlanc);
        BotMinimax joueurNoir = new BotMinimax(profondeurNoir);

        assertTrue(joueurBlanc instanceof BotMinimax, "White player should be BotMinimax");
        assertTrue(joueurNoir instanceof BotMinimax, "Black player should be BotMinimax");
        assertEquals(profondeurBlanc, joueurBlanc.getProfondeurMax(), "Minimax bot depth should be 3");
        assertEquals(profondeurNoir, joueurNoir.getProfondeurMax(), "Minimax bot depth should be 5");
    }

    @Test
    void testInvalidPlayerConfiguration() {
        // Cas de profondeur négative ou nulle pour BotMinimax
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new BotMinimax(0); // Problème de profondeur égale à 0
        });
        assertEquals("La profondeur maximale doit être supérieure à 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new BotMinimax(-1); // Problème de profondeur négative
        });
        assertEquals("La profondeur maximale doit être supérieure à 0.", exception.getMessage());
    }




}
