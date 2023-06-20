
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Exercice {
    public static void main(String[] args) {
        try {
            // URL de la page contenant le formulaire
            URL url = new URL("http://localhost/tp2/motDePasse.html");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Entrez le mot de passe : ");
            String password = reader.readLine();

            // Paramètre du formulaire
            String param1 = password;

            // Création des données à envoyer
            String postData = "passwordInput=" + URLEncoder.encode(param1, "UTF-8");

            // Ouverture de la connexion HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            if (password.equals("passer") && response.toString().contains("Authentification réussie")) {
                System.out.println("Authentification réussie !");
            } else {
                System.out.println("Erreur d'authentification.");
            }

            // Fermeture de la connexion
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

