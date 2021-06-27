package xyz.elandasunshine.capi.game.file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import xyz.elandasunshine.capi.game.exception.DirectoryLockedException;

public class GameDirectory implements Iterable<GameDirectory>
{
	//==================================================================================================================
	protected static final List<GameDirectory> PURGE_ON_START = Lists.newArrayList();
	protected static final List<GameDirectory> PURGE_ON_END   = Lists.newArrayList();
		
	//==================================================================================================================
	private final File                       path;
	private final Map<String, GameDirectory> directories = Maps.newHashMap();
	private final GameDirectory              parent;
	private final boolean                    ignoreSubDirectories;
	
	protected boolean locked = false;
	
	//==================================================================================================================
	protected GameDirectory(final File parPath, final GameDirectory parParent, 
			                final EnumSet<PurgePolicy> parPurgePolicy)
	{
		this.path   = parPath;
		this.parent = parParent;

		if (parPurgePolicy != null)
		{
			this.ignoreSubDirectories = parPurgePolicy.contains(PurgePolicy.IGNORE_SUBDIRECTORIES);

			if (parPurgePolicy.contains(PurgePolicy.ON_START))
			{
				PURGE_ON_START.add(this);
			}

			if (parPurgePolicy.contains(PurgePolicy.ON_END))
			{
				PURGE_ON_END.add(this);
			}
		}
		else
		{
			this.ignoreSubDirectories = false;
		}
	}
	
	//==================================================================================================================
	/**
	 *  Adds a new registered sub-directory to the GameFileManager.
	 *  
	 *  @param directoryName The name of the directory
	 *  @param purgePolicy   The purge policy of this directory
	 *  @return The directory that has been added or null if the directory already existed
	 */
	public GameDirectory addDirectory(final String directoryName, final EnumSet<PurgePolicy> purgePolicy)
	{
		if (this.locked)
		{
			throw new DirectoryLockedException(path);
		}
		
		GameDirectory dir = directories.get(directoryName);
		
		if (dir == null)
		{
			dir = new GameDirectory(new File(path, directoryName), this, purgePolicy);
			directories.put(directoryName, dir);
			return dir;
		}
		
		return null;
	}
	
	/**
	 *  Adds a new registered sub-directory to the GameFileManager.
	 *  
	 *  @param directoryName The name of the directory
	 *  @return
	 */
	public GameDirectory addDirectory(final String directoryName)
	{
		return addDirectory(directoryName, EnumSet.noneOf(PurgePolicy.class));
	}
	
	/**
	 *  Gets the file of this directory instance.
	 *  @return The file of this directory
	 */
	public File getFile()
	{
		return path;
	}
	
	//==================================================================================================================
	/**
	 *  Gets a sub-directory if it exists, else null.
	 *  
	 *  @param directoryName The name of the directory
	 *  @return The directory or null
	 */
	public GameDirectory getDirectory(final String directoryName)
	{
		return directories.get(directoryName);
	}
	
	/**
	 *  Gets the parent directory instance of this directory.
	 *  
	 *  If this returns null, that means that this directory instance is the root directory.
	 *  Checking the return value of this function against null is exactly equivalent as getting the value of isRoot().
	 *  
	 *  @return The parent directory instance or null if there is no parent
	 */
	public GameDirectory getParentDirectory()
	{
		return parent;
	}
	
	/**
	 *  Gets the name of this directory instance.
	 *  @return The name
	 */
	public String getName()
	{
		return path.getName();
	}
	
	//==================================================================================================================
	/**
	 *  Checks whether this directory instance is the root directory.
	 *  @return True if this is the root directory
	 */
	public boolean isRoot()
	{
		return parent == null;
	}
	
	/**
	 *  Checks whether this directory instance has been locked.
	 *  @return True if this directory is locked
	 */
	public boolean isLocked()
	{
		return locked;
	}
	
	//==================================================================================================================
	/**
	 *  Locks a directory so as to that it can't be modified anymore.
	 *  If you try to add sub-directories or purge it, an exception will be thrown.
	 *  
	 *  Note that once a directory is locked, you cannot unlock it anymore.
	 */
	public void lock()
	{
		this.locked = true;
	}
	
	//==================================================================================================================
	/** Purge this and, optionally, any sub-directory. */
	public void purge()
	{
		if (this.locked)
		{
			throw new DirectoryLockedException(path);
		}
		
		final Stream<File> fileStream = Arrays.stream(path.listFiles());
		fileStream.filter(x -> (x.isFile() || !directories.containsKey(x.getName()))).forEach(x ->
		{
			try { FileUtils.forceDelete(x); }
			catch (final IOException ex) {}
		});
		
		if (!ignoreSubDirectories)
		{
			for (final GameDirectory dir : directories.values())
			{
				dir.purge();
			}
		}
	}

	//==================================================================================================================
	@Override
	public Iterator<GameDirectory> iterator()
	{
		return directories.values().iterator();
	}
}
