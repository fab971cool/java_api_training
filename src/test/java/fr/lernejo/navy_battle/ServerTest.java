package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void run() throws IOException, InterruptedException {
        Server server = new Server(9765);
        server.run();

        // creer un client
        var client = HttpClient.newHttpClient();

        //creer une requete
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9765/ping"))
            .GET()
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());

        assertEquals (200 ,response.statusCode());;
    }
}
