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

	@Test
    public void inspectMethodsTest() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        
        Inspector inspector = new Inspector();
        ClassT clT = new ClassT();
        inspector.inspectMethods(clT.getClass(), true);

        String actualOutput = os.toString();
        assertEquals("Methods: \n" + 
        		"    Name: getVal\n" + 
        		"    Exceptions thrown: \n" + 
        		"        None\n" + 
        		"    Parameter types: \n" + 
        		"        None\n" + 
        		"    Modifiers: 1\n" + 
        		"\n" + 
        		"    Name: printSomething\n" + 
        		"    Exceptions thrown: \n" + 
        		"        None\n" + 
        		"    Parameter types: \n" + 
        		"        None\n" + 
        		"    Modifiers: 2\n" + 
        		"\n" + 
        		"    Name: run\n" + 
        		"    Exceptions thrown: \n" + 
        		"        None\n" + 
        		"    Parameter types: \n" + 
        		"        None\n" + 
        		"    Modifiers: 1\n" + 
        		"\n" + 
        		"    Name: toString\n" + 
        		"    Exceptions thrown: \n" + 
        		"        None\n" + 
        		"    Parameter types: \n" + 
        		"        None\n" + 
        		"    Modifiers: 1\n" + 
        		"\n" + 
        		"    Name: setVal\n" + 
        		"    Exceptions thrown: \n" + 
        		"        java.lang.Exception\n" + 
        		"    Parameter types: \n" + 
        		"        int\n" + 
        		"    Modifiers: 1\n" + 
        		"\n", actualOutput);
    }
	
	@Test
    public void inspectConstructorsTest() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        
        Inspector inspector = new Inspector();
        ClassT clT = new ClassT();
        inspector.inspectConstructors(clT.getClass(), true);

        String actualOutput = os.toString();
        assertEquals("Constructors: \n" + 
        		"    Name: ClassT\n" + 
        		"    Parameter types: \n" + 
        		"        None\n" + 
        		"    Modifiers: 1\n" + 
        		"\n" + 
        		"    Name: ClassT\n" + 
        		"    Parameter types: \n" + 
        		"        int\n" + 
        		"    Modifiers: 1\n" + 
        		"\n", actualOutput);
    }
	
	@Test
    public void inspectFieldsTest() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        
        Inspector inspector = new Inspector();
        ClassT clT = new ClassT();
        inspector.inspectFields(clT.getClass(), true);

        String actualOutput = os.toString();
        assertEquals("Fields: \n" + 
        		"    Name: val\n" + 
        		"    Type: int\n" + 
        		"    Modifiers: 2\n" + 
        		"\n" + 
        		"    Name: val2\n" + 
        		"    Type: double\n" + 
        		"    Modifiers: 2\n" + 
        		"\n" + 
        		"    Name: val3\n" + 
        		"    Type: boolean\n" + 
        		"    Modifiers: 2\n" + 
        		"\n" + 
        		"    Name: val4\n" + 
        		"    Type: class [I\n" + 
        		"    Modifiers: 2\n" + 
        		"\n", actualOutput);
    }
	
}
