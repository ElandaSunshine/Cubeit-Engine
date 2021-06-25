package xyz.elandasunshine.cvm.game;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.google.common.collect.Lists;

import net.minecraft.util.Util;
import xyz.elandasunshine.cvm.loader.ExcludeLoad;
import xyz.elandasunshine.cvm.loader.FileClassLoader;
import xyz.elandasunshine.cvm.util.constant.ConstList;

public class GameClassList
{
	//==================================================================================================================
	private static final char PATH_SEPERATOR = (Util.getOSType() == Util.EnumOS.WINDOWS ? '\\' : '/');
	
	//==================================================================================================================
	private final List<Class<?>> classList = Lists.newArrayList();
	private       Manifest       manifest  = null;
	private final File           gameJarFile;
		
	//==================================================================================================================
	public GameClassList(final File parGameJarFile)
	{
		this.gameJarFile = parGameJarFile;
	}
	
	//==================================================================================================================
	public void findClasses() throws IOException, ClassNotFoundException
	{		
		try (final FileClassLoader classLoader = new FileClassLoader(this.gameJarFile);
			 final JarFile         gameJar     = new JarFile(this.gameJarFile))
		{			
			final Enumeration<JarEntry> entries = gameJar.entries();
			
			this.manifest = gameJar.getManifest();
			
			final Attributes atts      = manifest.getAttributes("loader");
			final String     classRoot = atts.getValue("Class-Root").replace('.', PATH_SEPERATOR);
			
			while (entries.hasMoreElements())
			{
				final JarEntry entry = entries.nextElement();
				final String   name  = entry.getName();
							
				if (classRoot != null && !name.startsWith(classRoot))
				{
					continue;
				}
				
				if (!entry.isDirectory() && name.endsWith(".class"))
				{
					final Class<?> loadedClass = classLoader.loadClass(name);
					
					if (!loadedClass.isAnnotationPresent(ExcludeLoad.class))
					{
						this.classList.add(loadedClass);
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
	
	public Manifest getManifest()
	{
		return this.manifest;
	}
	
	public ConstList<Class<?>> getClasses()
	{
		return new ConstList<>(this.classList);
	}
}
