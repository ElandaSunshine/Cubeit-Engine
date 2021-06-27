package xyz.elandasunshine.capi.game.file;

import java.io.File;
import java.util.EnumSet;

/**
 *  Represents the game's directory structure in its simplest form.
 *  For any directory you need for your game, you can reach out here in this class.
 *  
 *  You can also add your custom directories, but any folder beyond the game root directory is invalid.
 *  
 *  @author elanda
 */
public class GameFileManager
{
	//==================================================================================================================
	/** The name of the binary directory. */
	public static final String DIRNAME_BIN = "bin";
	
	/** The name of the assets directory. */
	public static final String DIRNAME_ASSETS = "assets";
	
	/** The name of the temp directory. */
	public static final String DIRNAME_TEMP = "temp";
	
	/** The name of the server resource directory. */
	public static final String DIRNAME_SERVER = "server";
	
	//==================================================================================================================
	/** The game root directory. */
	public final GameDirectory root;
	
	/** The game binary directory, this is where the game code sits. */
	public final GameDirectory binDir;
	
	/** The game assets directory, contains the game's resources and any minecraft replacements. */
	public final GameDirectory assetDir;
	
	/** The game server dir is for resources and files that only the server should be using. */
	public final GameDirectory serverDir;
	
	/** 
	 *  The game directory for temporary files.
	 *  You can use this directory to place any temporary files in, it will be purged once the game closes,
	 *  or if the game crashed, on the next start.
	 */
	public final GameDirectory tempDir;
	
	private boolean initialised = false;
	
	//==================================================================================================================
	public GameFileManager(final File parGameDir)
	{
		this.root = new GameDirectory(parGameDir, null, null);
		
		this.assetDir  = this.root.addDirectory(DIRNAME_ASSETS);
		this.binDir    = this.root.addDirectory(DIRNAME_BIN);
		this.serverDir = this.root.addDirectory(DIRNAME_SERVER);
		this.tempDir   = this.root.addDirectory(DIRNAME_TEMP, EnumSet.of(PurgePolicy.ON_START, PurgePolicy.ON_END));
		
		this.assetDir .lock();
		this.binDir   .lock();
		this.serverDir.lock();
		this.tempDir  .lock();
	}
	
	//==================================================================================================================
	public void initialise()
	{
		if (!this.initialised)
		{
			this.initialised = true;
			
			for (final GameDirectory dir : GameDirectory.PURGE_ON_START)
			{
				final boolean locked = dir.locked;
				dir.locked = false;
				
				dir.purge();
				
				if (locked)
				{
					dir.locked = true;
				}
			}
		}
	}
	
	public void shutdown()
	{
		if (this.initialised)
		{
			for (final GameDirectory dir : GameDirectory.PURGE_ON_END)
			{
				final boolean locked = dir.locked;
				dir.locked = false;
				
				dir.purge();
				
				if (locked)
				{
					dir.locked = true;
				}
			}
		}
	}
}
