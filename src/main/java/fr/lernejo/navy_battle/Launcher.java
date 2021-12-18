package fr.lernejo.navy_battle;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;


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
