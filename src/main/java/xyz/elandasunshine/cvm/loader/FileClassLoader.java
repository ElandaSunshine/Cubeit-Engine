package xyz.elandasunshine.cvm.loader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class FileClassLoader implements Closeable
{
	//==================================================================================================================
	private final File gameFile;
	private URLClassLoader classLoader = null;

	//==================================================================================================================
	public FileClassLoader(File parGameFile) throws IOException
	{
		this.gameFile = parGameFile;
		
		if (!parGameFile.exists() || parGameFile.isDirectory())
		{
			throw new IOException("File '" + parGameFile.getAbsolutePath()
								  + "' does not exist or is not a file.");
		}
		
		try { this.classLoader = new URLClassLoader(new URL[] { parGameFile.toURI().toURL() }); }
		catch (MalformedURLException e) {}
	}
	
	//==================================================================================================================
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		if (name.endsWith(".class"))
		{
			name = name.substring(0, name.length() - ".class".length());
		}
		
		name = name.replace('/', '.');
		return Class.forName(name, false, classLoader);
	}
	
	//==================================================================================================================
	public File getGameFile() { return gameFile; }
	
	//==================================================================================================================
	@Override
	public void close() throws IOException
	{
		if (classLoader != null)
		{
			classLoader.close();
		}
	}
}
