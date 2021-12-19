package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import jdk.jfr.Name;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class CallHandlerTest
{

    HttpServer server;
    String target;

    HttpContext context;
    CallHandler handler = new CallHandler();

    URL baseURL;

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
    void handleNoRequest() throws IOException {

        assertEquals(server, context.getServer());

    }

    @Test
    void handle_bad_start_game_request_() throws IOException, InterruptedException {

        // creer un client
        var client = HttpClient.newHttpClient();
        server.start();

        //creer une requete
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"No_id\":\"1\", \"url\":\"http://localhost:" + server.getAddress().getPort() + "\"}"))
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        // obtenir une reponse grace au client
        assertEquals(404, response.statusCode());
        assertEquals("404 Not Found", response.body());
    }


        // problème sur le fait de ne rien avoir dans le body = OK
    @Test
    void handle_bad_start_game_request_Exception() throws IOException, InterruptedException {

        // creer un client
        var client = HttpClient.newHttpClient();
        server.start();

        //creer une requete
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(" corps vide ou string simple ou mauvais format json"))
            .build();

        // obtenir une reponse grace au client
        assertThrows( IOException.class, ()-> {
            client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        });

        //assertTrue( JSON.parse(), response.body());



       /*baseURL = new URL(target + "/api/game/start");
        server.start();

        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("POST");*/
        //assertEquals(202, conn.getResponseCode());
        //BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        //System.out.println(conn.getResponseCode());
    }

    @Test
    void handle_good_start_game_request_() throws IOException, InterruptedException {

        // creer un client
        var client = HttpClient.newHttpClient();
        server.start();

        //creer une requete
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(target + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + server.getAddress().getPort() + "\", \"message\":\"hello\"}"))
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        // obtenir une reponse grace au client
        assertEquals(202, response.statusCode());
        //assertEquals("404 Not Found", response.body());
    }


    @Test
    void handlePingRequest() throws IOException
    {
        baseURL = new URL(target + "/ping");
        server.start();

        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        assertEquals("OK", in.readLine());
    }

    @Test
    void Handle_No_Existing_Path_Request() throws IOException{
        baseURL = new URL(target + "/erreur/chemin");
        server.start();

        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("GET");

        assertEquals(404, conn.getResponseCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        assertEquals("404 Not Found", in.readLine());
    }
}
