package fr.lernejo.navy_battle.ServerHandler.PlayingRequest;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.FileParser.ParsingJson;

import java.io.IOException;
import java.io.OutputStream;

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
}
