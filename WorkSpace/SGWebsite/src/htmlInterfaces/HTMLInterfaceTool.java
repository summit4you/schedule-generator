package htmlInterfaces;

import htmlInterfaces.HTMLTablable.TableHead;
import htmlInterfaces.HTMLTablable.TableInput;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * <b>Class containing static methods to handle HTMLinterfaces </b> </br>
 * This class will be used by interfaces based on annotations.
 * @author Alexander
 * @version 1.02
 * @see HTMLTablable, HTMLFormable
 */
public class HTMLInterfaceTool 
{	
	/**
	 * Checks if the HTMLTablable is well implemented in the given class
	 * The implementation is considered successful if there is one HeadTable 
	 * and the number of InputTable annotations is equal to number specified in
	 * The HeadTable annotation. 
	 */
	public static boolean checkHTMLInterface(Class<? extends HTMLTablable> cl)
	{
		Annotation classAnnotations= AnnotationTool.fetchClassAnnotation(cl,TableHead.class);
		if (classAnnotations!=null)
		{
			int numberOfinputs=TableHead.class.cast(classAnnotations).numberOfInputs();
			Vector<Annotation> methodAnnotations= AnnotationTool.fetchMethodAnnotation(cl,TableInput.class);
			if ((methodAnnotations!=null)&& (methodAnnotations.size()==numberOfinputs))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method collects all information needed to represent a HTMLTablable
	 * object as a table. If the operation is successful the resulting vector 
	 * will contain the rows of a table as needed. The first row contains the 
	 * title of the table. The following rows contain the name and value of 
	 * every element. Remember that the names are normally references to a 
	 * language file and still need to be replaced.</br>
	 * This method doesn't check if the interface of the given object is well
	 * implemented. When an error occurs null is returned. </br>
	 */
	public static Vector<Vector<String>> fetchTableContent(HTMLTablable obj)
	{
		TableHead tablehead= TableHead.class.cast(AnnotationTool.fetchClassAnnotation(obj.getClass(),TableHead.class));
		Vector<Annotation> tableinputs= AnnotationTool.fetchMethodAnnotation(obj.getClass(),TableInput.class);
		Vector<Method> methods=  AnnotationTool.fetchMethods(obj.getClass(),TableInput.class) ;
		
		Vector<Vector<String>> rows= new Vector<Vector<String>>();
		rows.setSize((tablehead.numberOfInputs()+1));
		Vector<String> row= new Vector<String>();
		row.add(tablehead.title());
		rows.set(0,row);
		
		int index = 0;
		TableInput ti;
		for (Annotation i: tableinputs)
		{
			 row= new Vector<String>();
			 ti=TableInput.class.cast(i);
			 System.out.println(ti.text());
			 row.add(ti.text());
			 try 
			 {
			   row.add(methods.get(index).invoke(obj).toString());
			 } catch (Exception e) 
			 {
				e.printStackTrace();
				return null;
			 }
			 System.out.println(row);
			 rows.set(ti.order(),row);
			 index=index+1;
		}
		return rows;
	}
	
}
