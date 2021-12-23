package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// Tests = OK
public class Launcher
{
    public static void main(String[] args) throws NumberFormatException  {
        if ( args.length < 1 || args.length > 2){
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }
        try {
            int number;
            number = Integer.parseInt(args[0]);
            Server server = new Server();
            server.run(number);
            if (args.length == 2) { server.requestServer(args[1], args[0]);}
        }
        catch (Exception e) {
            System.out.println("Not an Integer");
            throw new NumberFormatException(e.getMessage());
        }
    }
}
