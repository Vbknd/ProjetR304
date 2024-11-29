package test;

import plateau.Plateau;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlateauTest {

    @Test
    public void testInitialisationPlateau() {
        Plateau plateau = new Plateau(7);
        assertEquals(7, plateau.getTaille());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', plateau.getGrille()[i][j]);
            }
        }
    }

    @Test
    public void testDefinirTaille() {
        Plateau plateau = new Plateau(7);
        plateau.definirTaille(9);
        assertEquals(9, plateau.getTaille());
        assertThrows(IllegalArgumentException.class, () -> plateau.definirTaille(4));
    }

    @Test
    public void testReinitialiser() {
        Plateau plateau = new Plateau(7);
        plateau.placerPierre(3, 3, 'X');
        plateau.reinitialiser();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', plateau.getGrille()[i][j]);
            }
        }
    }

    @Test
    public void testPlacerPierre() {
        Plateau plateau = new Plateau(7);
        plateau.placerPierre(3, 3, 'X');
        assertEquals('X', plateau.getGrille()[3][3]);
        assertThrows(IllegalArgumentException.class, () -> plateau.placerPierre(3, 3, 'O')); // Case occupée
        assertThrows(IllegalArgumentException.class, () -> plateau.placerPierre(10, 10, 'X')); // Case hors limites
    }

    @Test
    public void testEstCoupLegal() {
        Plateau plateau = new Plateau(7);
        assertTrue(plateau.estCoupLegal(3, 3));
        plateau.placerPierre(3, 3, 'X');
        assertFalse(plateau.estCoupLegal(3, 3));
    }
}
