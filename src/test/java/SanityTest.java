import groovy.util.logging.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
public class SanityTest {


    @Test
    public void getSpotifyTokenTest() throws IOException, InterruptedException {
        String clientID = "73d279a0b6e2403dae09b84f79df5fce";
        String clientSecret = "8b43600405dd49d099bf1f3e6cda9c34";
        var keys = clientID + ":" + clientSecret;
        var URL = "https://accounts.spotify.com/api/token";

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode() + response.body().toString());

    }
}