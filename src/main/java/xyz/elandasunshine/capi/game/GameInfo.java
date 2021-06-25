package xyz.elandasunshine.capi.game;

public class GameInfo
{
	//==================================================================================================================
	private static GameInfo INSTANCE = null;
	
	//==================================================================================================================
	public final String   gameId;
	public final String   gameVersion;
	public final String   gameName;
	public final String   gameUrl;
	public final String[] gameAuthors;
	
	//==================================================================================================================	
	public static final GameInfo get()
	{
		return INSTANCE;
	}
	
	//==================================================================================================================
	public GameInfo(final String id, final String version, final String name, final String url, final String[] authors)
	{	
		if (INSTANCE == null)
		{
			INSTANCE = this;
		}
				
		this.gameId      = id;
		this.gameVersion = version;
		this.gameName    = name;
		this.gameUrl     = url;
		this.gameAuthors = authors;
	}
}
