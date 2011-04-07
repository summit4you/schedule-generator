package htmlInterfaces;


import htmlInterfaces.HTMLTablable.TableInfo;
import htmlInterfaces.HTMLTablable.TableInput;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;


/**
 * <b>Class containing static methods to handle HTMLinterfaces </b> </br>
 * This class will be used by interfaces based on annotations.
 * @author Alexander
 * @version 1.3.1
 * @see HTMLTablable, HTMLFormable
 */
public class HTMLInterfaceTool 
{	
	/**
	 *  This method gets TableInfo annotation of the class of an object
	 */
	public static TableInfo fetchTableInfo(HTMLTablable obj)
	{
		return fetchTableInfo(obj.getClass());

	}
	
	public static TableInfo fetchTableInfo(Class<? extends HTMLTablable> cl)
	{
		Annotation tableinputs= AnnotationTool.fetchClassAnnotation(cl,TableInfo.class);
		return (TableInfo) tableinputs;

	}
	
	/**
	 * This method collects all the text out of the TableIput annotations and puts them in the
	 * right order.
	 */
	public static Vector<String> fetchTexts(Class<? extends HTMLTablable> cl)
	{
		Vector<Annotation> tableinputs= AnnotationTool.fetchMethodAnnotation(cl,TableInput.class);
		Vector<String> res= new Vector<String>();
		res.setSize((tableinputs.size()));
		for (Annotation i: tableinputs)
		{
			res.set(TableInput.class.cast(i).order()-1,TableInput.class.cast(i).text());
		}
		return res;
	}
	
	public static Vector<String> fetchTexts(HTMLTablable obj)
	{
		return fetchTexts(obj.getClass());
	}
	
	/**
	 * This method collects all the values of the methods annotated with the TableIput annotations 
	 * and puts them in the right order.
	 */
	public static Vector<String> fetchValues(HTMLTablable obj)
	{
		Vector<Annotation> tableinputs= AnnotationTool.fetchMethodAnnotation(obj.getClass(),TableInput.class);
		Vector<Method> methods=  AnnotationTool.fetchMethods(obj.getClass(),TableInput.class) ;
		Vector<String> res= new Vector<String>();
		res.setSize((tableinputs.size()));
		int index = 0;
		for (Annotation i: tableinputs)
		{
			 try 
			 {
			   res.set(TableInput.class.cast(i).order()-1,methods.get(index).invoke(obj).toString());
			 } catch (Exception e) 
			 {
				e.printStackTrace();
				return null;
			 }
			 index=index+1;
		}
		return res;
	}
	
	/**
	 * <b>This method collects all information needed to represent a HTMLTablable
	 * object as a table.</b></br></br> If the operation is successful the resulting 
	 * vector will contain the rows of a table as needed.The rows contain the name and
	 * value of every element. Remember that the names are normally references 
	 * to a language file and still need to be replaced.</br>
	 * This method doesn't check if the interface of the given object is well
	 * implemented. When an error occurs null is returned. </br>
	 */
	@Deprecated
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
	 * <b>This method collects all information needed to represent a HTMLTablable
	 * vector as a table.</b></br></br>  If the operation is successful the resulting vector 
	 * will contain the rows of a table as needed. The first row contains the 
	 * name of every column. Remember that the names are normally references 
	 * to a language file and still need to be replaced. The following rows 
	 * each represent an element out of the vector</br>
	 * This method doesn't check if the interface of the given object is well
	 * implemented neither does it checks if all object are from the same class. 
	 * When an error occurs null is returned. When an empty vector is given as
	 * an argument null is returned </br>
	 */
	@Deprecated
	public static Vector<Vector<String>> fetchTableContent(Vector<? extends HTMLTablable> vec)
	{
		if (vec!=null)
		{
			Vector<Vector<String>> rows= new Vector<Vector<String>>();	
			rows.add(fetchTexts(vec.get(0)));
			for (HTMLTablable t:vec)
			{
				rows.add(fetchValues(t));
			}
			return rows;
		}
		return null;
	}

	/**
	 * Easy to use method  that creates a table from an
	 * HTMLTablable object. This method combines HTMLUtils.toHTMLTable()
	 * and HTMLInterfaceTool.fetchTableContent() to generate its result.
	 */
	public static String changeToHTMLTable(HTMLTablable obj)
	{
		return HTMLUtils.toHTMLTable(HTMLInterfaceTool.fetchTableContent(obj)).write();
	}
	
	/**
	 * Easy to use method  that creates a table from an
	 * HTMLTablable Vector. This method combines HTMLUtils.toHTMLTable()
	 * and HTMLInterfaceTool.fetchTableContent() to generate its result.
	 */
	public static String changeToHTMLTable(Vector<? extends HTMLTablable> vec)
	{
		return HTMLUtils.toHTMLTable(HTMLInterfaceTool.fetchTableContent(vec)).write();
	}
	
	/**
	 * Easy to use method  that creates a table from an
	 * HTMLTablable Vector. This method combines HTMLUtils.toDataTable()
	 * and HTMLInterfaceTool.fetchValues() to generate its result.
	 */
	public static String changeToDataTable(String tableID,Vector<? extends HTMLTablable> vec)
	{
		if (vec!=null)
		{
			Vector<Vector<String>> rows= new Vector<Vector<String>>();
			for (HTMLTablable t:vec)
			{
				rows.add(fetchValues(t));
			}
			return HTMLUtils.toDataTable(tableID, fetchTexts(vec.get(0)),rows).write(); 
		}
		return null;
	}
	
	/**
	 * Voor Zjef
	 */
	public static String makeEmptyTable(String tableID,Class<? extends HTMLTablable> cl)
	{
		Vector<String> row= new Vector<String>();
		Vector<Vector<String>> rows= new Vector<Vector<String>>();
		rows.add(row);
		return HTMLUtils.toDataTable(tableID, fetchTexts(cl),rows).write(); 
	}
}
