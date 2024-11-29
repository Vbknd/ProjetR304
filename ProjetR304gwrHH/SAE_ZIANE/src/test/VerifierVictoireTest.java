package test;

import plateau.Plateau;
import verification.VerifierVictoire;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerifierVictoireTest {

    @Test
    public void testVictoireHorizontale() {
        Plateau plateau = new Plateau(7);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(0, 0, 'X');
        plateau.placerPierre(0, 1, 'X');
        plateau.placerPierre(0, 2, 'X');
        plateau.placerPierre(0, 3, 'X');
        plateau.placerPierre(0, 4, 'X');

        assertTrue(verifier.aGagne(0, 4, 'X'));
    }

    @Test
    public void testVictoireVerticale() {
        Plateau plateau = new Plateau(7);
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        plateau.placerPierre(0, 0, 'O');
        plateau.placerPierre(1, 0, 'O');
        plateau.placerPierre(2, 0, 'O');
        plateau.placerPierre(3, 0, 'O');
        plateau.placerPierre(4, 0, 'O');

        assertTrue(verifier.aGagne(4, 0, 'O'));
    }
}
