package xyz.elandasunshine.cvm.loader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Manifest;

import net.minecraft.util.Util;

public class FileClassLoader implements Closeable
{
	//==================================================================================================================
	private static final char PATH_SEPERATOR = (Util.getOSType() == Util.EnumOS.WINDOWS ? '\\' : '/');
	
	//==================================================================================================================
	private final File           gameFile;
	private       URLClassLoader classLoader = null;

	//==================================================================================================================
	public FileClassLoader(final File parGameFile) throws IOException
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
	public Class<?> loadClass(final String name) throws ClassNotFoundException
	{
		String classPath = name.replace(PATH_SEPERATOR, '.');
		
		if (classPath.endsWith(".class"))
		{
			classPath = classPath.substring(0, classPath.length() - ".class".length());
		}
		
		return Class.forName(classPath, false, classLoader);
	}
	
	public Manifest getManifestFile() throws IOException
	{
		final URL mfUrl = classLoader.findResource("META-INF/MANIFEST.MF");
		return new Manifest(mfUrl.openStream());
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
