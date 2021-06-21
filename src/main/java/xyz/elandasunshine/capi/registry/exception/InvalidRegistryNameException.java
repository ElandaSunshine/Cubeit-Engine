package xyz.elandasunshine.capi.registry.exception;

import xyz.elandasunshine.capi.registry.IRegisterable;

public class InvalidRegistryNameException extends RuntimeException
{
	//==================================================================================================================
	private static final long serialVersionUID = -7592300124115614195L;

	//==================================================================================================================
	public static InvalidRegistryNameException createNullNameException(IRegisterable registerable)
	{
		return new InvalidRegistryNameException("The registry name for the object was not set: "
												+ registerable.getClass().getSimpleName());
	}
	
	public static InvalidRegistryNameException createDuplicateNameException(IRegisterable registerable)
	{
		return new InvalidRegistryNameException("The name '" + registerable.getRegistryName() + "' of object '"
												+ registerable.getClass().getSimpleName()
	                                            + "' was already registered for a previous game object.");
	}
	
	//==================================================================================================================
	private InvalidRegistryNameException(String message)
	{
		super(message);
	}
}
