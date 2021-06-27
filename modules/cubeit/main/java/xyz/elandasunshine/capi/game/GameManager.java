package xyz.elandasunshine.capi.game;

import java.io.File;
import java.nio.file.FileSystem;

import xyz.elandasunshine.capi.game.file.GameFileManager;

public class GameManager
{
	//==================================================================================================================
	private static GameManager INSTANCE = null;
	
	//==================================================================================================================
	public static void createManager(final File gameFolder, final GameInfo gameInfo)
	{
		if (INSTANCE == null)
		{
			INSTANCE = new GameManager(gameFolder, gameInfo);
		}
	}

	public static GameManager getInstance()
	{
		return INSTANCE;
	}

	//==================================================================================================================
	public final GameInfo        gameInfo;
	public final GameFileManager fileManager;
	
	//==================================================================================================================
	private GameManager(final File gameFolder, final GameInfo parGameInfo)
	{
		this.gameInfo    = parGameInfo;
		this.fileManager = new GameFileManager(gameFolder);
	}
}
