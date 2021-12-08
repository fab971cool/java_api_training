package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Launcher
{
    public static void main(String[] args) throws IOException {

        if ( args.length != 1)
        {
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }

        Server server = new Server(9876);
        server.run();
    }
}
