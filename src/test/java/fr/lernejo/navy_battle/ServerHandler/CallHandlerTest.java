package fr.lernejo.navy_battle.ServerHandler;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.FileParser.ParsingJson;
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
    void Handle_No_Existing_Path_Request() throws IOException, InterruptedException {
        baseURL = new URL(target + "/erreur/chemin");
        server.start();
        System.out.println("ouuuuuuuuuu");
        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("GET");
        assertEquals(404, conn.getResponseCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        assertEquals("404 Not Found", in.readLine());
    }

    @Test
    void Handle_good_fire_game_request() throws IOException, InterruptedException {
        baseURL = new URL(target + "/api/game/fire?cell=A5");
        server.start();
        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("GET");
        assertEquals(202, conn.getResponseCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        assertTrue(new ParsingJson().checkJson(conn.getInputStream(), "/schema/schema2.json"));
    }

    @Test
    void Handle_bad_fire_game_request() throws IOException, InterruptedException {
        baseURL = new URL(target + "/api/game/fire?noEqual&missingCell=3");
        server.start();
        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("GET");
        assertEquals(404, conn.getResponseCode());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        assertEquals("missing cell query", in.readLine());
    }
}
