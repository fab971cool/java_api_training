package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server
{
    // port par defaut
    int port = 9876;

    HttpServer server;

    // permet de gérer les URLs "/ping", "/game/api", ...
    public void handleRequest() throws IOException {
        HttpContext context = this.server.createContext("/");
        context.setHandler(new CallHandler());

    }

    public Server(int port)
    {
        this.port = port;
    }

    public void run() throws IOException
    {
        // Lance IllegalArgumentException si port < 0
        InetSocketAddress socketAddress = new InetSocketAddress(this.port);
        server = HttpServer.create(socketAddress, 1);
        server.setExecutor(Executors.newFixedThreadPool(1));
        this.handleRequest();
        this.server.start();
    }

}
