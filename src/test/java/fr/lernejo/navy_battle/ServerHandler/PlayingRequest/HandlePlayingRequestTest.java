package fr.lernejo.navy_battle.ServerHandler.PlayingRequest;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.ServerHandler.CallHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;



class HandlePlayingRequestTest {

    HttpServer server;
    String target;
    HttpContext context;
    CallHandler handler = new CallHandler();

    @BeforeEach
    void server_establishment() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 0);

        server = HttpServer.create(socketAddress, 1);
        server.setExecutor(Executors.newFixedThreadPool(1));
        context = server.createContext("/", handler);
        target = String.format("http://%s:%s", server.getAddress().getHostName(), server.getAddress().getPort());
    }

    @AfterEach
    void serverclose()
    {
        server.stop(0);
    }

    @Test
    void handle_bad_start_game_request_() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        server.start();

        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"No_id\":\"1\", \"url\":\"http://localhost:" + server.getAddress().getPort() + "\"}"))
            .build();
        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Bad Request", response.body());
    }

    @Test
    void handle_bad_start_game_request_Exception() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        server.start();

        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(" corps vide ou string simple ou mauvais format json"))
            .build();
        Assertions.assertThrows( IOException.class, ()-> {
            client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        });
    }

    @Test
    void handle_good_start_game_request_() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        server.start();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + server.getAddress().getPort() + "\", \"message\":\"hello\"}"))
            .build();
        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(202, response.statusCode());
    }
}
