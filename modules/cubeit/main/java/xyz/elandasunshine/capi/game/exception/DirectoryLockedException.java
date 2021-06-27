package xyz.elandasunshine.capi.game.exception;

import java.io.File;

public class DirectoryLockedException extends RuntimeException
{
	//==================================================================================================================
	private static final long serialVersionUID = 1191949587158295885L;
	
	//==================================================================================================================
	public DirectoryLockedException(final File lockedFile)
	{
		super("Could not modify GameDirectory '" + lockedFile.getName() + "' because it has been locked.");
	}
}
