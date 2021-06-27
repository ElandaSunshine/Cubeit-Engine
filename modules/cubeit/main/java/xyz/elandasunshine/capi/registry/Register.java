package xyz.elandasunshine.capi.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Registers annotated classes into the Minecraft object registry.
 *  Objects that are annotated with this and don't possess a special registry for them are ignored.
 *  
 *  By default any object needs to have a default constructor, but in cases where this is not plausible you can set the
 *  class-path to an already-initialised object instead.
 *  
 *  @author elanda
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Register
{
	/**
	 *  Defines the name for the registry in which this object is going to be registered.
	 *  If the registry already contains a name with this object, a runtime exception is thrown.
	 *  
	 *  Note: The unlocalised name of uninitialised game objects will also get the registry name by default.
	 *  So make sure to change it if you need a different name for your object.
	 *  
	 *  @return The registry name of this object
	 */
	String value();
	
	/**
	 *  Sets the priority of this object in what order these should be registered.
	 *  
	 *  A value below 1 will be treated as 1 and anything above will be sorted in that order.
	 *  The limit is whatever the positive numerical limit of integers is.
	 *  
	 *  @return The priority value
	 */
	int priority() default 1;
	
	/**
	 *  If there is already an instance of your object somewhere then you can specify the class-path that leads to
	 *  these objects.
	 *  Note that already instantiated objects need to be public static.
	 *  
	 *  This is useful for objects that don't have a defaulted constructor.
	 *  
	 *  As an example, for a class with block objects:
	 *  {@code
	 *  	package classpath.to.block.class
	 *  
	 *  	public class MyBlocks
	 *  	{
	 *  		public static final MyBlock MY_BLOCK = new MyBlock(...);
	 *      }
	 *  }
	 *  
	 *  This should look like: classpath.to.block.class.MyBlocks:MY_BLOCK
	 *  If you are defining objects that are lazily instantiated, make sure to have them at least constructed before
	 *  your games main initialisation routine ended.
	 *  
	 *  @return The path string to the instantiated object
	 */
	String instancePath() default "";
}
