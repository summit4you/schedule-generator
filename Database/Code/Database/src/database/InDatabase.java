package database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 'getter' Methods (of classes interfacing {@link Databasable}) of parameters that need to be stored in the database need to add this annotation.<br>
 * The method cannot have any parameters<br><br>
 * The method's name has to start with 'get' or 'is' and has to be followed by the parameter's name (the same name as in the setter method)<br><br>
 * When the method, to which this annotation is added, returns a Vector&ltXXX&gt, then explicitly specify the class of the Objects within this Vector:<code>@InDatabase(XXX)</code><br>
 * <ul>
 * <lh>XXX is limited to one of the following:</lh>
 * <li>a class implementing {@link Databasable}</li>
 * <li>an <code>Integer</code></li>
 * <li>a <code>Double</code> </li>
 * <li>a <code>String</code></li>
 * <li>a <code>Boolean</code></li>
 * </ul> 
 * @author Zjef
 * @version 1.0
 * @see Databasable
 * @see OutDatabase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InDatabase
{
	Class<?> value() default Object.class;
}