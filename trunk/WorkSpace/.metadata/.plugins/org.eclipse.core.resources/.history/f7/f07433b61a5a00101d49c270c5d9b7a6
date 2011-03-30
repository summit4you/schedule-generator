package database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 'getter' Methods (of classes interfacing {@link Databasable}) of parameters that need to be stored in the database need to add this annotation.<br>
 * The method cannot have any parameters<br><br>
 * The method's name has to start with 'get' or 'is' followed by the same name as in the setter method<br><br>
 * @author Zjef
 * @version 1.0
 * @see Databasable
 * @see OutDatabase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InDatabase
{
	
}