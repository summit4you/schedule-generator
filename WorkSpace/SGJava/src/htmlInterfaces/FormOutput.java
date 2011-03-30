package htmlInterfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
