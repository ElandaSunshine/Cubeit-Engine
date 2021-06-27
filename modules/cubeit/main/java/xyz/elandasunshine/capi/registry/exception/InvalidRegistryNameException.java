package xyz.elandasunshine.capi.registry.exception;

import xyz.elandasunshine.capi.registry.Register;

public class InvalidRegistryNameException extends Exception
{
	//==================================================================================================================
	private static final long serialVersionUID = -7592300124115614195L;

	//==================================================================================================================
	public static InvalidRegistryNameException createNullNameException(final Register registerable)
	{
		return new InvalidRegistryNameException("The registry name for the object was empty: "
												+ registerable.getClass().getSimpleName());
	}
	
	public static InvalidRegistryNameException createDuplicateNameException(final Register registerable)
	{
		return new InvalidRegistryNameException("The name '" + registerable.value() + "' of object '"
												+ registerable.getClass().getSimpleName()
	                                            + "' was already registered for a previous game object.");
	}
	
	//==================================================================================================================
	private InvalidRegistryNameException(final String message)
	{
		super(message);
	}
}
