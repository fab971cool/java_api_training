package fr.lernejo.navy_battle.ServerHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.security.auth.callback.TextCallbackHandler;
import fr.lernejo.navy_battle.FileParser.ParsingJson;
import fr.lernejo.navy_battle.ServerHandler.PlayingRequest.HandlePlayingRequest;
import fr.lernejo.navy_battle.ServerHandler.SimpleRequest.HandleSimpleRequest;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler
{
    HandleSimpleRequest simpleRequest = new HandleSimpleRequest();
    HandlePlayingRequest playingRequest = new HandlePlayingRequest();
    boolean error = true;
    ParsingJson parser = new ParsingJson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // On récupère l'url en entier sous forme de string
        var url = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && url.equals("/api/game/start") ) {
            handleUrlPostRequest(url, exchange);
        }
        else if ("GET".equals(exchange.getRequestMethod())){
            handleUrlGetRequest(url, exchange);
        }
        if (error){
            simpleRequest.handleRequest(exchange);
        }
    }

    public void handleUrlPostRequest(String url, HttpExchange exchange) throws IOException {
        if (parser.checkJson(exchange.getRequestBody(), "/schema/schema1.json"))
            playingRequest.handleGameRequest(exchange);
        else // modifer
            simpleRequest.handleBadRequest(exchange);
        error = false;
    }

    public void handleUrlGetRequest(String url, HttpExchange exchange) throws IOException {
        if (url.equals("/ping")) {
            simpleRequest.handlePingRequest(exchange);
            error = false;
        }
        else if (url.equals("/api/game/fire")){
            playingRequest.handleFireRequest(exchange);
            error = false;
        }
    }

}
