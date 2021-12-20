package fr.lernejo.navy_battle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


// Tests = OK
public class Launcher
{
    public static void main(String[] args) throws IOException, NumberFormatException  {

        if ( args.length < 1 || args.length > 2)
        {
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }
        int number = 0;
        try {
            number = Integer.parseInt(args[0]);
            Server server = new Server(number);

            server.run();
            if (args.length == 2) {
                var client = HttpClient.newHttpClient();
                HttpRequest requestePost = HttpRequest.newBuilder()
                    .uri(URI.create(args[1] + "/api/game/start"))
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"hello\"}"))
                    .build();
                var response = client.send(requestePost, HttpResponse.BodyHandlers.ofString());
                System.out.println("Connection OK. \nReceived:\t"+response.body() +"\nStatus :\t" + response.statusCode());
            }
        }
        catch (Exception e) {
            System.out.println("Not an Integer");
            throw new NumberFormatException(e.getMessage());
        }
    }
}
