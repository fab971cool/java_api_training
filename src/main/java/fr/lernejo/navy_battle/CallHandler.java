package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // On récupère l'url en entier sous forme de string
        var url = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && url.equals("/game/api"))
        {
            handleGameRequest(exchange);
        }
        else if ("GET".equals(exchange.getRequestMethod()) && url.equals("/ping")){
            handlePingRequest(exchange);
        }
        else{
            String body = " 404 Not found";
            exchange.sendResponseHeaders(404, body.length());
            try (OutputStream os = exchange.getResponseBody())
            {
                os.write(body.getBytes());
            }
        }
    }

    public void handleGameRequest(HttpExchange exchange) throws IOException {
        String body = "Get post method";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }

    public void handlePingRequest(HttpExchange exchange) throws IOException {
        String body = "Hello";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }

}
