package xyz.elandasunshine.cvm.util;

public class IndexedException extends Exception
{
	//==================================================================================================================
	private static final long serialVersionUID = -5383791634134460913L;
	
	//==================================================================================================================
	public final int id;
	
	//==================================================================================================================
	public IndexedException(int parId)
	{
		this.id = parId;
	}
}
