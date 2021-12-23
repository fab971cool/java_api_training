package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ServerTest {

    @Test
    void run() throws IOException, InterruptedException {
        Server server = new Server();
        server.run(9765);

        // creer un client
        var client = HttpClient.newHttpClient();

        //creer une requete
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9765/ping"))
            .GET()
            .build();
        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200 ,response.statusCode());;
    }
}
