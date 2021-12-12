package fr.lernejo.navy_battle;

import java.io.IOException;

// Tests = OK
public class Launcher
{
    public static void main(String[] args) throws IOException, NumberFormatException  {

        if ( args.length != 1)
        {
            System.out.println("Veuillez spécifier le port d'écoute");
            System.exit(1);
        }

        int number = 0;
        try {
            number = Integer.parseInt(args[0]);
            Server server = new Server(number);
            // regarder la valeur de server pour les tests de run()
            server.run();
        }
        catch (Exception e)
        {
            System.out.println("Not an Integer");
            throw new NumberFormatException();
        }
    }
}
