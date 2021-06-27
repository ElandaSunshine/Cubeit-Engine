package xyz.elandasunshine.capi.game.file;

/**
 *  The policy of when to clean a directory if it should be cleaned.
 *  @author elanda
 */
public enum PurgePolicy
{
	/** Purge directory on VM startup only. */
	ON_START,
	
	/** Purge directory on VM shutdown only, possible issues on crashes. */
	ON_END,
	
	/**
	 *  Determines that whenever this directory gets purged whether sub-directories should be purged too.
	 *  Note that this does not prevent sub-directories from applying their own purge-policy.
	 */
	IGNORE_SUBDIRECTORIES
}
