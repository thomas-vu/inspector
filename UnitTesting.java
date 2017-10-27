import static org.junit.Assert.*;

import java.io.*;
import java.util.*;
import org.junit.Test;

public class UnitTesting {

	@Test
    public void inspectInterfacesTest() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        
        Inspector inspector = new Inspector();
        ClassT clT = new ClassT();
        inspector.inspectInterfaces(clT.getClass(), true);

        String actualOutput = os.toString();
        assertEquals("Interfaces implemented: \n" + 
        		" - java.io.Serializable\n" + 
        		" - java.lang.Runnable\n\n", actualOutput);
    }

}
