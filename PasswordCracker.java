import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCracker {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choisissez la méthode de cassage :");
            System.out.println("1. Cassage par mot de passe");
            System.out.println("2. Cassage par hash");

            int methodChoice = scanner.nextInt();
            String password;
            String hash ="";
            PasswordCrackerFactory factory = new PasswordCrackerFactory(); // Déclaration de la fabrique d'algorithmes
            PasswordCrackingAlgorithm algorithm = null; // Déclaration de l'algorithme de cassage
            
            if (methodChoice == 1) {
                scanner.nextLine();
                System.out.println("Entrez le mot de passe :");
                password = scanner.nextLine();
            } else if (methodChoice == 2) {
                scanner.nextLine();
                System.out.println("Entrez le hash du mot de passe :");
                     hash = scanner.nextLine();
                password = getPasswordFromHash(hash);
            } else {
                System.out.println("Méthode de cassage non valide.");
                return;
            }

            System.out.println("Choisissez l'algorithme de cassage :");
            System.out.println("1. Brute force");
            System.out.println("2. Dictionnaire");
            int algorithmChoice = scanner.nextInt();

                if (algorithmChoice == 1) {
                    if (methodChoice == 1) {
                        algorithm = factory.createAlgorithm("bruteforce", password, "","", false);
                    } else if (methodChoice == 2) {
                        algorithm = factory.createAlgorithm("bruteforce", password, hash ,"", true);
                    } 
            } else if (algorithmChoice == 2) {
                String chemin = "C:\\Users/dell/Documents/DIC1/Classeur1.csv";
                algorithm = factory.createAlgorithm("dictionary", password, chemin, "",true);
            } else {
                System.out.println("Algorithme de cassage non valide.");
                return;
            }
            
            long temps_debut = System.currentTimeMillis();
            algorithm.crackPassword();
            long temps_fin = System.currentTimeMillis();
            long temps_mis = temps_fin - temps_debut;
            System.out.println("Temps écoulé : " + temps_mis + " millisecondes");
        }
    }

    private static String getMD5Hash(String password) {
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
 
    private static String getPasswordFromHash(String hash) {
        String chemin = "C:\\Users/dell/Documents/DIC1/Classeur1.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                String password = parts[0].trim();
                String hashValue = parts[1].trim();
                if (hash.equals(hashValue)) {
                    return password;
                }
                
            }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier CSV.");
        }

        return null;
    
}
}