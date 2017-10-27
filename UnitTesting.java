import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.junit.Test;

public class UnitTesting {

	@Test
    public void printTest() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        System.out.print("Testing assertions with console output");
        String actualOutput = os.toString();
        assertEquals("Testing assertions with console output", actualOutput);
    }

}
