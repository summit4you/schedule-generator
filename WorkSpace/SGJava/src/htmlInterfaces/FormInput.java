package htmlInterfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
