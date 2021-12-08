package fr.lernejo.navy_battle;

import java.io.IOException;


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
