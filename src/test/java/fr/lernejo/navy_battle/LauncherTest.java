package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class LauncherTest {

    @Test
    public void mainStringTest() throws IOException {

        assertThrows(NumberFormatException.class, ()-> {
            Launcher.main(new String[]{"ARS"});
        });
    }

    @Test
    public void mainNothingTest() throws IOException {

        assertThrows(NumberFormatException.class, ()-> {
            Launcher.main(new String[]{" "});
        });
    }

}
