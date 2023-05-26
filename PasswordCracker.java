import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class PasswordCracker {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choisissez la méthode de cassage :");
            System.out.println("1. Cassage par mot de passe");
            System.out.println("2. Cassage par hash");
            int methodChoice = scanner.nextInt();

            String password;
            PasswordCrackerFactory factory = new PasswordCrackerFactory(); // Déclaration de la fabrique d'algorithmes
            PasswordCrackingAlgorithm algorithm; // Déclaration de l'algorithme de cassage

            if (methodChoice == 1) {
                scanner.nextLine();
                System.out.println("Entrez le mot de passe :");
                password = scanner.nextLine();
            } else if (methodChoice == 2) {
                scanner.nextLine();
                System.out.println("Entrez le hash du mot de passe :");
                String hash = scanner.nextLine();
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
                algorithm = factory.createAlgorithm("bruteforce", password, "", false);
            } else if (algorithmChoice == 2) {
                String chemin = "C:/Users/dell/Documents/DIC1/2nd semestre/pattron_de_conception/tp1/Classeur1.csv";
                algorithm = factory.createAlgorithm("dictionary", password, chemin, false);
            } else {
                System.out.println("Algorithme de cassage non valide.");
                return;
            }

            // Cassage du mot de passe
            long startTime = System.currentTimeMillis();
            algorithm.crackPassword();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Temps écoulé : " + duration + " millisecondes");
        }
    }

    private static String getPasswordFromHash(String hash) {
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
