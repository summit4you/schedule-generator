package htmlInterfaces;


import htmlInterfaces.HTMLTablable.TableInput;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;


/**
 * <b>Class containing static methods to handle HTMLinterfaces </b> </br>
 * This class will be used by interfaces based on annotations.
 * @author Alexander
 * @version 1.1
 * @see HTMLTablable, HTMLFormable
 */
public class HTMLInterfaceTool 
{		
	/**
	 * This method collects all information needed to represent a HTMLTablable
	 * object as a table. If the operation is successful the resulting vector 
	 * will contain the rows of a table as needed.The rows contain the name and
	 * value of every element. Remember that the names are normally references 
	 * to a language file and still need to be replaced.</br>
	 * This method doesn't check if the interface of the given object is well
	 * implemented. When an error occurs null is returned. </br>
	 */
	public static Vector<Vector<String>> fetchTableContent(HTMLTablable obj)
	{
		Vector<Annotation> tableinputs= AnnotationTool.fetchMethodAnnotation(obj.getClass(),TableInput.class);
		Vector<Method> methods=  AnnotationTool.fetchMethods(obj.getClass(),TableInput.class) ;
		
		Vector<Vector<String>> rows= new Vector<Vector<String>>();
		rows.setSize((tableinputs.size()));
		Vector<String> row= new Vector<String>();
		
		int index = 0;
		TableInput ti;
		for (Annotation i: tableinputs)
		{
			 row= new Vector<String>();
			 ti=TableInput.class.cast(i);
			 row.add(ti.text());
			 try 
			 {
			   row.add(methods.get(index).invoke(obj).toString());
			 } catch (Exception e) 
			 {
				e.printStackTrace();
				return null;
			 }
			 rows.set(ti.order()-1,row);
			 index=index+1;
		}
		return rows;
	}
	
	/**
	 * Easy to use method  that creates a table from an
	 * HTMLTablable object. This method combines HTMLUtils.toHTMLTable()
	 * and HTMLInterfaceTool.fetchTableContent() to generate its result.
	 */
	public static String extractHTMLTable(HTMLTablable obj)
	{
		return HTMLUtils.toHTMLTable(HTMLInterfaceTool.fetchTableContent(obj)).write();
	}
}
