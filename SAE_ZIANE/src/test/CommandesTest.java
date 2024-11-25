package test;

import plateau.Plateau;
import commandes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandesTest {

    @Test
    public void testDefinirTaillePlateauCommande() {
        Plateau plateau = new Plateau(7);
        Commande commande = new DefinirTaillePlateauCommande(plateau, 9);
        assertEquals("=1", commande.executer());
        assertEquals(9, plateau.getTaille());

        Commande commandeInvalide = new DefinirTaillePlateauCommande(plateau, 4);
        assertEquals("? La taille doit être entre 5 et 19", commandeInvalide.executer());
    }

    @Test
    public void testReinitialiserPlateauCommande() {
        Plateau plateau = new Plateau(7);
        plateau.placerPierre(3, 3, 'X');
        Commande commande = new ReinitialiserPlateauCommande(plateau);
        assertEquals("=2", commande.executer());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', plateau.getGrille()[i][j]);
            }
        }
    }

    @Test
    public void testJouerCommande() {
        Plateau plateau = new Plateau(7);
        Commande commande = new JouerCommande(plateau, "noir", "D5");
        assertEquals("=3", commande.executer());
        assertEquals('X', plateau.getGrille()[4][3]); // D5 correspond à [4][3]

        Commande commandeInvalide = new JouerCommande(plateau, "blanc", "D5");
        assertEquals("? Coup illégal", commandeInvalide.executer());
    }
}
