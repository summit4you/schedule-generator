package htmlInterfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is an interface to declare that an object can be transformed in to a 
 * HTMLform.  The setter of every attribute, that has to be represented in the
 * form, should be marked with the annotation FormInput. The getter of every
 * attribute, that has to be filled in by a form has to be marked with the
 * annotation FormOutput.
 * <center> <b>!!! under development !!!<b> </center>
 * @author Alexander
 * @version 0.1
 * @see FormInput,FormOutput
 */
public interface HTMLFormable
{	
	/**
	 * FormInput is used to mark the getters of the attributes that have to be
	 * put into an HTML form. These getters should not have any parameters. The 
	 * Annotation should only be used in a class implementing the HTMLFormlable 
	 * interface.
	 * <b>Order</b>: determines the order in which the element is displayed from top to bottom. </br>
	 * <b>Text</b>: determines how the element is called inside the Form.
	 * @author Alexander
	 * @version 0.1
	 * @see HTMLFormable
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface FormInput 
	{
		int order() default 1;
		String text() default " ? ";
	}
	
	/**
	 * FormOutput is used to mark the setters of the attributes that have to be
	 * filled in by a HTML form. These setters should not have any parameters. The 
	 * Annotation should only be used in a class implementing the HTMLFormable 
	 * interface.
	 * <b>Order</b>: Should be the same order as that of FormOutput </br>
	 * @author Alexander
	 * @version 0.1
	 * @see HTMLFormable
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface FormOutput
	{
		int order() default 1;
	}
}
