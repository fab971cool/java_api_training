package fr.lernejo.navy_battle.ServerHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.FileParser.ParsingJson;
import fr.lernejo.navy_battle.ServerHandler.PlayingRequest.HandlePlayingRequest;
import fr.lernejo.navy_battle.ServerHandler.SimpleRequest.HandleSimpleRequest;
import java.io.IOException;

public class CallHandler implements HttpHandler
{
    

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HandlePlayingRequest playingRequest = new HandlePlayingRequest();
        HandleSimpleRequest simpleRequest = new HandleSimpleRequest();
        ParsingJson parser = new ParsingJson();
        boolean error = true;
        var url = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && url.equals("/api/game/start") ) {
            error = handleUrlPostRequest(url, exchange, simpleRequest, playingRequest, parser);
        }
        if ("GET".equals(exchange.getRequestMethod())){
            error = handleUrlGetRequest(url, exchange, simpleRequest, playingRequest );
        }
        if (error){
            simpleRequest.handleRequest(exchange);
        }
    }

    public boolean handleUrlPostRequest(String url, HttpExchange exchange, HandleSimpleRequest simpleRequest, HandlePlayingRequest playingRequest, ParsingJson parser ) throws IOException {
        if (parser.checkJson(exchange.getRequestBody(), "/schema/schema1.json"))
            playingRequest.handleGameRequest(exchange);
        else // modifer
            simpleRequest.handleBadRequest(exchange);
        return false;
    }

    public boolean handleUrlGetRequest(String url, HttpExchange exchange, HandleSimpleRequest simpleRequest, HandlePlayingRequest playingRequest) throws IOException {
        if (url.equals("/ping")) {
            simpleRequest.handlePingRequest(exchange);
            return false;
        }
        else if (url.equals("/api/game/fire")){
            playingRequest.handleFireRequest(exchange);
            return false;
        }
        return true;
    }

}
