package fr.lernejo.navy_battle.ServerHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.FileParser.ParsingJson;
import fr.lernejo.navy_battle.ServerHandler.PlayingRequest.HandlePlayingRequest;
import fr.lernejo.navy_battle.ServerHandler.SimpleRequest.HandleSimpleRequest;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler
{

    ParsingJson parser = new ParsingJson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        var simpleRequest = new HandleSimpleRequest();
        var playingRequest = new HandlePlayingRequest();

        // On récupère l'url en entier sous forme de string
        var url = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && url.equals("/api/game/start") ) {

            if (parser.checkJson(exchange.getRequestBody(), "/schema/schema1.json")) {
                playingRequest.handleGameRequest(exchange);
            }
            else
                simpleRequest.handleRequest(exchange);
        }
        else if ("GET".equals(exchange.getRequestMethod()) && url.equals("/ping")){
            simpleRequest.handlePingRequest(exchange);
        }
        else{
            simpleRequest.handleRequest(exchange);
        }
    }
}
