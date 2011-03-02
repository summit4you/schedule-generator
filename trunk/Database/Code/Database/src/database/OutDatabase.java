package database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 'setter' Methods (of classes interfacing {@link Databasable}) of parameters that need to be loaded from the database need to add this annotation.<br>
 * The method can only have one parameter<br><br>
 * The method's name has to start with 'set' and followed by the parameter's name (the same as in the getter method)<br><br>
 * When the method to which this annotation is added, has a vector&ltXXX&gt as input, then explicitly specify the class of the Objects within the Vector:<code>@OutDatabase(XXX)</code> 
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
 * @see InDatabase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OutDatabase
{
	Class<?> value() default Object.class; 
}