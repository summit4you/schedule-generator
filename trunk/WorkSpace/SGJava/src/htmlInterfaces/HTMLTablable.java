package htmlInterfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an interface to declare that an object can be transformed in to a 
 * HTMLtable. The getter of every attribute, that has to be represented in the
 * table, should be marked with the annotation TableInput.
 * @author Alexander
 * @version 0.1
 * @see TableInput,TableHead 
 */
public interface HTMLTablable
{
	/**
	 * TableHead should be placed above every class that implements HTMLTablable.
	 * </br></br>
	 * numberOfInputs: Total number of elements in the table.</br>
	 * title: title of the table corresponding with this class</br>  
	 * @author Alexander
	 * @version 0.1
	 * @see HTMLTablable
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface TableHead 
	{
		int numberOfInputs() default 1;
		String title() default "<TextNotSpecified>";
	}
	
	/**
	 * TableInput is used to mark the getters of the attributes that have to be
	 * put into an HTML table. These getters should not have any parameters. The 
	 * Annotation should only be used in a class implementing the HTMLTablable 
	 * interface.</br></br>
	 * Order: determines the order in which the element is displayed from top to bottom.</br>
	 * Text: determines how the element is called inside the table. This should be a tag
	 * that is present in language.xml 
	 * @author Alexander
	 * @version 0.1
	 * @see HTMLTablable
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface TableInput 
	{
		int order() default 1;
		String text() default "<TextNotSpecified>";
	}
	
	

}
