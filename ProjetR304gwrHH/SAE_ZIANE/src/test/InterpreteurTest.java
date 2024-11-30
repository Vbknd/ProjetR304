package test;

import org.junit.jupiter.api.Test;
import terminalinteractions.Interpreteur;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreteurTest {

    @Test
    public void testCommandeQuit() {
        String simulationEntree = "quit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=8"));
        assertTrue(sortie.contains("Merci d'avoir joué !"));
    }

    @Test
    public void testDefinirTailleCommande() {
        String simulationEntree = "definir-taille 9\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=1")); // Commande réussie
    }

    @Test
    public void testReinitialiserPlateauCommande() {
        String simulationEntree = "reinitialiser-plateau\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=2")); // Commande réussie
    }

    @Test
    public void testJouerCommande() {
        String simulationEntree = "jouer noir D4\njouer blanc D5\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=3")); // Chaque commande jouer est réussie
    }

    @Test
    public void testShowBoardCommande() {
        String simulationEntree = "showboard\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=")); // Affichage réussi
        assertTrue(sortie.contains("A B C D E F G")); // Vérifie le plateau
    }

    @Test
    public void testGenMoveCommande() {
        String simulationEntree = "genmove noir\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("=")); // Coup de l'IA réussi
    }


    @Test
    public void testModeJoueurVsJoueur() {
        String simulationEntree = "1\nD4\nD5\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("Joueur 1 (Noir) :"));
        assertTrue(sortie.contains("Joueur 2 (Blanc) :"));
    }


    @Test
    public void testModeJoueurVsIA() {
        String simulationEntree = "2\nnoir\n1\nD4\nquit\n";
        ByteArrayInputStream entreeSimulee = new ByteArrayInputStream(simulationEntree.getBytes());
        ByteArrayOutputStream sortieCapturee = new ByteArrayOutputStream();

        System.setIn(entreeSimulee);
        System.setOut(new PrintStream(sortieCapturee));

        Interpreteur interpreteur = new Interpreteur();
        interpreteur.lancer();

        String sortie = sortieCapturee.toString();
        assertTrue(sortie.contains("Votre tour :"));
        assertTrue(sortie.contains("IA réfléchit..."));
    }


}
