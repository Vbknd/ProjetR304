package test;

import org.junit.jupiter.api.Test;
import terminalinteractions.Interpreteur;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreteurTest {

    @Test
    public void testInteractionUtilisateur() {
        String simulationEntree = "afficher-plateau\njouer noir D5\nreinitialiser-plateau\nquitter\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("Bienvenue dans le jeu de plateau !"));
        assertTrue(sortie.contains("=3")); // Jouer une pierre
        assertTrue(sortie.contains("=2")); // Réinitialiser le plateau
        assertTrue(sortie.contains("Merci d'avoir joué !"));
    }
}
