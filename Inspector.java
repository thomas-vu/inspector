/*==========================================================================
File: Inspector.java
Purpose: Object inspector for the Asst2TestDriver

Location: University of Calgary, Alberta, Canada
Original code by: Jordan Kidney
Adapted code by: Thomas Vu
Created on: Oct 26, 2017
Last Updated: Oct 26, 2017
========================================================================*/

import java.util.*;
import java.lang.reflect.*;
import java.lang.reflect.Array;

public class Inspector
{
	public Queue<Object> objectsToInspect = new ArrayDeque<Object>();
	public ArrayList<Object> inspectedObjects = new ArrayList<Object>();
	public Queue<Class> classesToInspect = new ArrayDeque<Class>();
	public ArrayList<Class> inspectedClasses = new ArrayList<Class>();
	
	public Inspector() { }
	
	private static final Set<Class> WRAPPER_TYPES = new HashSet(
			Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));
	
	public static boolean isWrapperType(Class c) {
	    return WRAPPER_TYPES.contains(c);
	}
	
	public void inspect(Object obj, boolean recursive)
    {
		inspectedObjects.add(obj);
		
		Class objClass = obj.getClass();

		inspectClass(objClass, recursive);
		
		Field[] fields = objClass.getDeclaredFields();
		System.out.println("***OBJECT: " + objClass.getName() + " " + System.identityHashCode(obj) + "***");
		System.out.println("Field values:");
		for (Field f : fields)
		{
			try
			{
				f.setAccessible(true);
				Object value = f.get(obj);
				
				if (value != null)
				{
					if (value.getClass().isArray())
					{
						System.out.println(f.getName() + " = Array");
						System.out.println("       Type: " + value.getClass().getComponentType());
						System.out.println("       Length: " + Array.getLength(value));
						System.out.print("       Contents: [");
						for (int i = 0; i < Array.getLength(value); i++)
							if (i == Array.getLength(value)-1)
								System.out.print(Array.get(value, i));
							else
								System.out.print(Array.get(value, i) + ", ");
						System.out.println("]");
					}
					else if (!value.getClass().isPrimitive() && !isWrapperType(value.getClass()))
					{
						System.out.println(f.getName() + " = " + value.toString() + " " + System.identityHashCode(value));
						if (recursive)
						{							
							if (!inspectedClasses.contains(value.getClass())) 
							{
								classesToInspect.add(value.getClass());
							}
							if (!inspectedObjects.contains(value)) 
							{
								objectsToInspect.add(value);
							}
						}
					}					
					else
						System.out.println(f.getName() + " = " + value.toString());
				}
				else
					System.out.println(f.getName() + " = " + "Null");
			}
			catch (Exception exp) { exp.printStackTrace(); }
    	}
		
		System.out.println();
		
		if (!objectsToInspect.isEmpty())
			inspect(objectsToInspect.remove(), recursive);
		
		objectsToInspect.clear();
		inspectedObjects.clear();
		classesToInspect.clear();
		inspectedClasses.clear();
    }
	
	
	

	
	public void inspectClass(Class cl, boolean recursive)
    {		
		if (inspectedClasses.contains(cl)) return;
		
		inspectedClasses.add(cl);
		
		if (!cl.isInterface())
		{
			Class objSuperClass = cl.getSuperclass();
			
			if (!inspectedClasses.contains(objSuperClass))
			{
				if (!objSuperClass.getName().equals("java.lang.Class") && !objSuperClass.getName().equals("java.lang.Object"))
				{
					classesToInspect.add(objSuperClass);
				}
			}
		}
		
		System.out.println("***CLASS***");
		System.out.println("Declaring Class: " + cl.getName() + "\n");
		if (!cl.isInterface()) System.out.println("Immediate Superclass: " + cl.getSuperclass().getName() + "\n");
		
		inspectInterfaces(cl, recursive);
		
		// Method Details
		Method[] methods = cl.getDeclaredMethods();
		if (methods.length == 0) System.out.println("No declared methods.");
		else
		{
			System.out.println("Methods: ");
			for (Method m : methods)
			{
				System.out.println("    Name: " + m.getName());
				
				System.out.println("    Exceptions thrown: ");
				Class[] exceptions = m.getExceptionTypes();
				if (exceptions.length == 0) System.out.println("        None");
				else for (Class e : exceptions) System.out.println("        " + e.getName());
				
				System.out.println("    Parameter types: ");
				Class[] parameters = m.getParameterTypes();
				if (parameters.length == 0) System.out.println("        None");
				else for (Class p : parameters) System.out.println("        " + p.getName());
				
				System.out.println("    Modifiers: " + m.getModifiers() + "\n");
			}
		}
		
		// Constructor Details
		Constructor[] constructors = cl.getDeclaredConstructors();
		if (constructors.length == 0) System.out.println("No constructors.");
		else
		{
			System.out.println("Constructors: ");
			for (Constructor c : constructors)
			{
				System.out.println("    Name: " + c.getName());
				
				System.out.println("    Parameter types: ");
				Class[] parameters = c.getParameterTypes();
				if (parameters.length == 0) System.out.println("        None");
				else for (Class p : parameters) System.out.println("        " + p.getName());
				
				System.out.println("    Modifiers: " + c.getModifiers() + "\n");
			}
		}

		// Fields Details
		Field[] fields = cl.getDeclaredFields();
		if (fields.length == 0) System.out.println("No fields.\n");
		else
		{
			System.out.println("Fields: ");
			for (Field f : fields)
			{
				System.out.println("    Name: " + f.getName());
				System.out.println("    Type: " + f.getType());
				System.out.println("    Modifiers: " + f.getModifiers() + "\n");
			}
		}
		
		// Get new class to inspect
		if (!classesToInspect.isEmpty())
		{
			inspectClass(classesToInspect.remove(), recursive);
		}
    }
	
	
	
	
	/*
	 * This method inspects the interfaces of a class.
	 * params:
	 * 		cl - The class whose interfaces to inspect
	 * 	    recursive - Whether to check all object fields recursively
	 */
	public void inspectInterfaces(Class cl, boolean recursive)
	{
		Class[] interfaces = cl.getInterfaces();
		if (interfaces.length == 0) System.out.println("No interfaces implemented.\n");
		else
		{
			System.out.println("Interfaces implemented: ");
			for (Class c : interfaces)
			{
				System.out.println(" - " + c.getName());
				if (!inspectedClasses.contains(c) && !classesToInspect.contains(c)) 
				{
					classesToInspect.add(c);
				}
			}
			System.out.println();
		}
	}
	
	
	
}