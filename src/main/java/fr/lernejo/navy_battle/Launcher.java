package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// Tests = OK
public class Launcher
{
    public static void main(String[] args) throws NumberFormatException {
        if ( args.length < 1 || args.length > 2) {
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }int number = 0;
        try {
            number = Integer.parseInt(args[0]);
            Server server = new Server();
            server.run(number);
            if (args.length == 2) { new Launcher().requestServer(args[1], args[0]);}
        }
        catch (Exception e) {
            System.out.println("Not an Integer");
            throw new NumberFormatException(e.getMessage());
        }
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
