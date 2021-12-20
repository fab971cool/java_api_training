package fr.lernejo.navy_battle;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;


class LauncherTest {

    @Test
    public void main_string_Test(){

        assertThrows(NumberFormatException.class, ()-> {
            Launcher.main(new String[]{"ARS"});
        });
    }

    @Test
    public void main_nothing_test(){

        assertThrows(NumberFormatException.class, ()-> {
            Launcher.main(new String[]{" "});
        });
    }

    @Test
    public void main_bad_number_arguments() throws Exception {

        int status = SystemLambda.catchSystemExit(() -> {
            Launcher.main(new String[]{" first", "3", "second"});
        });
        assertEquals(1, status);
    }

    @Test
    void main_server_run_test() throws Exception {
        Launcher.main(new String[]{String.valueOf(9745)});

        var client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9745/ping"))
            .GET()
            .build();
        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        assertEquals (200 ,response.statusCode());;
    }

    @Test
    void main_server_response_test() throws Exception {
        Launcher.main(new String[]{"8432"});
        Launcher.main(new String[]{"9735", "http://localhost:8432"});

        var client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8432/ping"))
            .GET()
            .build();
        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        assertEquals (200 ,response.statusCode());;
    }
}
