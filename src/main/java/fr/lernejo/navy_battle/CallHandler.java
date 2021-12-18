package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CallHandler implements HttpHandler
{

    ParsingJson parser = new ParsingJson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // On récupère l'url en entier sous forme de string
        var url = exchange.getRequestURI().getPath();
        if ("POST".equals(exchange.getRequestMethod()) && url.equals("/game/start") )
        {
            InputStream inputStream = exchange.getRequestBody();
            if (parser.checkJson(inputStream))
            {
                handleGameRequest(exchange);
            }
            else
            {
               handleRequest(exchange);
            }
        }
        else if ("GET".equals(exchange.getRequestMethod()) && url.equals("/ping")){
            handlePingRequest(exchange);
        }
        else{
            handleRequest(exchange);
        }
    }

    public void handleRequest(HttpExchange exchange) throws IOException {
        String body = "404 Not Found";
        exchange.sendResponseHeaders(404, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }

    public void handleGameRequest(HttpExchange exchange) throws IOException {

        var port = exchange.getLocalAddress().getPort();

        String body = parser.generateJsonToString("1", "http://localhost:" + Integer.toString(port), "start" );

        System.out.println(body);
        exchange.sendResponseHeaders(202, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            // create Json POST request with HTTPURLConnection
            os.write(body.getBytes());
        }
    }

    public void handlePingRequest(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }

}
