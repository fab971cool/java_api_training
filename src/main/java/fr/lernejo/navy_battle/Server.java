package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.ServerHandler.CallHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class Server
{
    public Server(){}

    public void run(int port) throws IOException {
        HttpServer server;
        HttpContext context;
        // Lance IllegalArgumentException si port < 0
        InetSocketAddress socketAddress = new InetSocketAddress(port);
        server = HttpServer.create(socketAddress, 1);
        server.setExecutor(Executors.newFixedThreadPool(1));

        // permet de gérer les URLs "/ping", "/game/api", ...
        context = server.createContext("/");
        context.setHandler(new CallHandler());

        server.start();
    }
    public void requestServer( String serverUrl, String portNumber) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        HttpRequest requestePost = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + portNumber + "\", \"message\":\"hello\"}"))
            .build();
        var response = client.send(requestePost, HttpResponse.BodyHandlers.ofString());
        System.out.println("Connection OK. \nReceived:\t"+response.body() +"\nStatus :\t" + response.statusCode());
    }
}
