package xyz.elandasunshine.cvm.exception;

public class GameNotFoundException extends RuntimeException
{
	//==================================================================================================================
	public GameNotFoundException()                  { super("No game directory was specified."); }
	public GameNotFoundException(final String path) { super("Could not find any game on the specified path: " + path); }
}
