package xyz.elandasunshine.cvm.exception;

public class GameNotFoundException extends RuntimeException
{
	//==================================================================================================================
	private static final long serialVersionUID = -2022174153360255803L;
	
	//==================================================================================================================
	public GameNotFoundException()
	{
		super("No game directory was specified.");
	}
	
	public GameNotFoundException(final String path)
	{
		super("Could not find any game on the specified path: " + path);
	}
}
