package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler
{

    public void handle(HttpExchange exchange) throws IOException {


        if ("POST".equals(exchange.getRequestMethod()))
        {
            handlePostRequest(exchange);
        }
        else{
            String body = "Hello";
            exchange.sendResponseHeaders(200, body.length());
            try (OutputStream os = exchange.getResponseBody())
            {
                os.write(body.getBytes());
            }
        }
    }

    public void handlePostRequest(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod()))
        {
            String body = "Get post method";
            exchange.sendResponseHeaders(200, body.length());
            try (OutputStream os = exchange.getResponseBody())
            {
                os.write(body.getBytes());
            }
        }


    }
}
