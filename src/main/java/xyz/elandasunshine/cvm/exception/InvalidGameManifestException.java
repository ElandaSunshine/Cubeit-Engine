package xyz.elandasunshine.cvm.exception;

public class InvalidGameManifestException extends RuntimeException
{
	//==================================================================================================================
	private static final long serialVersionUID = -3436953457045865591L;
	
	//==================================================================================================================
	public InvalidGameManifestException(final String message)
	{
		super(message);
	}
	
	public InvalidGameManifestException(final Throwable throwable)
	{
		super(throwable);
	}
}
