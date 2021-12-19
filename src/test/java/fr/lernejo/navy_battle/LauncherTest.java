package fr.lernejo.navy_battle;

import com.github.stefanbirkner.systemlambda.SystemLambda;
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

    @Test
    public void mainGoodTest() throws Exception {

        int status = SystemLambda.catchSystemExit(() -> {
            Launcher.main(new String[]{" fzdff", "3", "fndfz"});
            //the code under test, which calls System.exit(0);
        });
        assertEquals(1, status);
    }


}
