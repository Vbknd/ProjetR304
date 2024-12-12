package joueur;

import plateau.Plateau;
import java.util.Scanner;

public class JoueurHumain implements Joueur {
    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre coup : ");
        return scanner.nextLine().trim();
    }
}

