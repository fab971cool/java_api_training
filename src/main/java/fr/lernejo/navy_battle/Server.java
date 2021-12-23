package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.ServerHandler.CallHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
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

}
