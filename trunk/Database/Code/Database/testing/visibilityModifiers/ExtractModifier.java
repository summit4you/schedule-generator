package visibilityModifiers;

import java.lang.reflect.Method;
import java.util.Vector;
import database.Databasable;
import database.Extract;

/**
 * @author Zjef
 * @version 1.0
 */
public class ExtractModifier extends Extract
{
	public static String getTableName(Class<? extends Databasable> cl)
	{
		return Extract.getTableName(cl);
	}
	
	public static Vector<Method> getSetters(Class<? extends Databasable> cl)
	{
		return Extract.getSetters(cl);
	}
	
	public static Vector<Method> getGetters(Class<? extends Databasable> cl)
	{
		return Extract.getGetters(cl);
	}
	
	public static Object getValue(Object source,Method getter)
	{
		return Extract.getValue(source, getter);
	}
	
	public static String objectToString(Object object)
	{
		return Extract.objectToStr(object);
	}
	
	public static String methodToString(Method method)
	{
		return Extract.methodToString(method);
	}
}