package terminal;

public class Verif {
    private static boolean commandeValide = true;

    public static boolean Commande(String commande){
        return commandeValide;
    }

    public static boolean ID(String commande) {
        String premierMot = commande.split("\\s+")[0];
        try {
            Integer.parseInt(premierMot);
        } catch (NumberFormatException e) {
            commandeValide = false;
        }
        return true;
    }
}
