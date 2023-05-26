import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class DictionaryCracker implements PasswordCrackingAlgorithm {
    private String chemin;
    private String password;
    private boolean mot_de_passe_trouve;
    private String hash;
    

    DictionaryCracker(String chemin, String password, boolean estUnHash) {
             this.password=password;
             this.chemin = chemin;
             this.mot_de_passe_trouve = false;
             this.hash=getPasswordFromHash(password);  
    }

    @Override
    public void crackPassword() {
        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String mot_de_passe = parts[0].trim();
                    String hash_mot_de_passe= parts[1].trim();

                    if (password.equals(mot_de_passe) || password.equals(hash_mot_de_passe)) {
                        System.out.println("Mot de passe trouvé : " + mot_de_passe);
                        
                        this.mot_de_passe_trouve = true;
                        break;
                    }
                }
            } 
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier du dictionnaire.");
        }
    }
    private String getPasswordFromHash(String hash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = hash.getBytes();
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
