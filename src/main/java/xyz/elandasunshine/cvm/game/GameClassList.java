package xyz.elandasunshine.cvm.game;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.collect.Lists;

import xyz.elandasunshine.cvm.loader.ExcludeLoad;
import xyz.elandasunshine.cvm.loader.FileClassLoader;
import xyz.elandasunshine.cvm.util.constant.ConstList;

public class GameClassList
{
	//==================================================================================================================
	private final List<Class<?>> classList = Lists.newArrayList();
	private final File gameJarFile;
		
	//==================================================================================================================
	public GameClassList(File parGameJarFile)
	{
		this.gameJarFile = parGameJarFile;
	}
	
	//==================================================================================================================
	public void findClasses() throws IOException, ClassNotFoundException
	{		
		try (final FileClassLoader classLoader = new FileClassLoader(gameJarFile);
			 final JarFile         gameJar     = new JarFile(gameJarFile))
		{			
			final Enumeration<JarEntry> entries = gameJar.entries();
			
			while (entries.hasMoreElements())
			{
				final JarEntry entry = entries.nextElement(); 
				final String   name  = entry.getName();
								
				if (!entry.isDirectory() && name.endsWith(".class"))
				{
					final Class<?> loadedClass = classLoader.loadClass(name);
					
					if (!loadedClass.isAnnotationPresent(ExcludeLoad.class))
					{
						classList.add(loadedClass);
					}
				}
			}
		}
	}
	
	//==================================================================================================================
	public File getJarFile()
	{
		return this.gameJarFile;
	}
	
	public ConstList<Class<?>> getClasses()
	{
		return new ConstList<>(classList);
	}
}
