package fr.lernejo.navy_battle.ServerHandler.PlayingRequest;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.FileParser.ParsingJson;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HandlePlayingRequest
{


    public void handleGameRequest(HttpExchange exchange) throws IOException {
        var port = exchange.getLocalAddress().getPort();
        var parser = new ParsingJson();
        String body = parser.generateJsonToString("1", "http://localhost:" + Integer.toString(port), "start");
        exchange.sendResponseHeaders(202, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    public void handleFireRequest(HttpExchange exchange) throws IOException {
        ParsingJson parser = new ParsingJson();
        String body = " ";
        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        if (params.get("cell") != null) {
            body = parser.generateResponse("sunk", true);
            //exchange.getResponseHeaders()
            exchange.getResponseHeaders().add("Content-type", "application/json");
            exchange.sendResponseHeaders(202, body.length());
        }else{
            body = "missing cell query";
            exchange.sendResponseHeaders(404, body.length());
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    public Map<String, String> queryToMap(String query) {
        if(query == null){
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")){
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
