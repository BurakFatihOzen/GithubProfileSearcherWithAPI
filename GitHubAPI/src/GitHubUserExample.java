import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class GitHubUserExample {
    public static void main(String[] args) {
        // Kullanıcı girişi ve API key
        Scanner scanner = new Scanner(System.in);
        System.out.print("Lütfen GitHub kullanıcısı adı girin: ");
        String username = scanner.nextLine();

        final String API_KEY = "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";
        final String BASE_URL = "https://api.github.com/users/";

        String url = BASE_URL + username;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "token " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                String name = jsonObject.has("name") && !jsonObject.get("name").isJsonNull() ? jsonObject.get("name").getAsString() : "Bilgi Yok";
                String company = jsonObject.has("company") && !jsonObject.get("company").isJsonNull() ? jsonObject.get("company").getAsString() : "Bilgi Yok";
                String location = jsonObject.has("location") && !jsonObject.get("location").isJsonNull() ? jsonObject.get("location").getAsString() : "Bilgi Yok";
                int publicRepos = jsonObject.get("public_repos").getAsInt();

                System.out.println("\nKullanıcı Adı: " + username);
                System.out.println("Ad: " + name);
                System.out.println("Şirket: " + company);
                System.out.println("Konum: " + location);
                System.out.println("Public Repos: " + publicRepos);

            } else {
                System.out.println("Kullanıcı bulunamadı veya API isteği başarısız oldu.");
            }
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        }
    }
}
