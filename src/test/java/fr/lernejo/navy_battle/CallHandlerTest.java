package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.*;
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
        server.start();


    }

    // problème sur le fait de ne rien avoir dans le body
    @Test
    @Disabled
    void handleGameRequest() throws IOException {
        baseURL = new URL(target + "/game/start");

        server.start();
        HttpURLConnection conn = (HttpURLConnection)baseURL.openConnection();
        conn.setRequestMethod("POST");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        assertEquals(202, conn.getResponseCode());
        System.out.println(conn.getResponseCode());
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

}
