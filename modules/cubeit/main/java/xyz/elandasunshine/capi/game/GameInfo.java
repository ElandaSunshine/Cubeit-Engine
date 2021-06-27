package xyz.elandasunshine.capi.game;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

import xyz.elandasunshine.capi.game.exception.InvalidGameManifestException;

public class GameInfo
{
	//==================================================================================================================
	public final String gameId;
	public final String gameVersion;
	public final String gameName;
	public final String gameUrl;
	
	public final String[] gameAuthors;
	
	//==================================================================================================================
	public static GameInfo createFromManifest(final Manifest manifest)
	{
		final Attributes atts = manifest.getAttributes("game");
		
		if (atts == null)
		{
			throw new InvalidGameManifestException("Game could not be loaded because no details were found.");
		}
		
		// Required
		final String gameId      = atts.getValue("Game-Id");
		final String gameVersion = atts.getValue("Game-Version");
		
		if (gameId == null)
		{
			throw new InvalidGameManifestException("Game could not be loaded "
												   + "because of the missing game id declaration.");
		}
		
		if (gameVersion == null)
		{
			throw new InvalidGameManifestException("Game could not be loaded "
												   + "because of the missing game version declaration.");
		}
		
		// Optional
		final String gameName    = atts.getValue("Game-Name");
		final String gameAuthors = atts.getValue("Game-Authors");
		final String gameUrl     = atts.getValue("Game-Url");
		
		return new GameInfo(gameId, gameVersion, (gameName == null ? "Unknown" : gameName), gameUrl,
				            (gameAuthors == null ? new String[] { "Unspecified" } : gameAuthors.split(",")));
	}
	
	//==================================================================================================================
	private GameInfo(final String id, final String version, final String name, final String url, final String[] authors)
	{					
		this.gameId      = id;
		this.gameVersion = version;
		this.gameName    = name;
		this.gameUrl     = url;
		this.gameAuthors = authors;
	}
}
