package terminal;

import terminal.Verif;

public class Util {
    public static String delID(String commande){
        return commande.replaceFirst("^\\S+\\s*", "");
    }

    private static String getMot1 (String commande){
        String mot1 = commande.split("\\s+")[0];
        return mot1;
    }

    public static int getID (String commande){
        commande = commande.trim();
        if (commande.isEmpty())
            sendError("la commande est vide");

        if (!Verif.ID(commande))
            sendError("id obligatoire, ex : '3 set_player white human'.");

        String premierMot = getMot1(commande);
        int id = Integer.parseInt(premierMot);
        return id;
    }

    public static void sendError(String txt) {
        System.out.println("? " + txt);
    }

    public static void sendError(int numCommande, String txt) {
        System.out.println("?" + numCommande + " " + txt);
    }

}
