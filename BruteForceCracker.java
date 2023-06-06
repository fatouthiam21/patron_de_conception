import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

  class BruteForceCracker implements PasswordCrackingAlgorithm {
    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int longueur_mot_de_passe = 6;
    private String password;
    private boolean mot_de_passe_trouve;
    private String hash;
    private long temps_debut;

    public BruteForceCracker(String password, String hash,boolean estUnHash) {
        this.password = password;
        this.hash = estUnHash ? hash : getMD5Hash(password);;
        this.mot_de_passe_trouve = false;
    }
    
  
    @Override
    public void crackPassword() {
        temps_debut = System.currentTimeMillis();
        // Génération et vérification des mots de passe possibles
        for (int length = 1; length <= longueur_mot_de_passe; length++) {
            generatePasswords( "", length);
        }
        if (!mot_de_passe_trouve) {
         System.out.println("Mot de passe non trouvé.");
         } 
    }
    private void generatePasswords(String mot_de_passe_actuel, int length) {
        if (mot_de_passe_actuel.length() == length) {
            String hashedPassword = getMD5Hash(mot_de_passe_actuel);
            if (mot_de_passe_actuel.equals(password) || hashedPassword.equals(hash)) {
                long temps_fin = System.currentTimeMillis();
                long duree = temps_fin - temps_debut;
                System.out.println("Mot de passe trouvé : " + mot_de_passe_actuel);
                System.out.println("Temps écoulé : " + duree + " millisecondes");
                mot_de_passe_trouve = true;
                System.exit(0);
            }
        
            return;
        
        }
    for (int i = 0; i < CHARACTER_SET.length(); i++) {
        char c = CHARACTER_SET.charAt(i);
        generatePasswords(mot_de_passe_actuel + c, length);
    }
    }

    private String getMD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = password.getBytes();
            byte[] hashedBytes = md.digest(bytes);
    
            // Convertir les octets en une représentation hexadécimale
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithme de hachage non pris en charge.");
        }
        return null;

} 
  }
