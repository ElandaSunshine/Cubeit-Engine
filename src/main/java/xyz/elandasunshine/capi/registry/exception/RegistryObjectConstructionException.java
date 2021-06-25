package xyz.elandasunshine.capi.registry.exception;

public class RegistryObjectConstructionException extends RuntimeException
{
	//==================================================================================================================
	private static final long serialVersionUID = -5297153825201837781L;

	//==================================================================================================================
	public RegistryObjectConstructionException(final Exception ex)
	{
		super(ex);
	}
}
