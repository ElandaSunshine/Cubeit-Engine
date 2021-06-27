package xyz.elandasunshine.cvm.client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import xyz.elandasunshine.capi.game.GameManager;

public class CubeitResourceFolder implements IResourcePack
{   	
	//==================================================================================================================
	private static <T extends IMetadataSection> T readMetadata(final MetadataSerializer metadataSerializer,
															   final InputStream        inputStream,
															   final String             sectionName)
	{
		JsonObject     jsonobject     = null;
		BufferedReader bufferedreader = null;
		
		try
		{
			bufferedreader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			jsonobject     = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
		}
		catch (final RuntimeException runtimeexception)
		{
			throw new JsonParseException(runtimeexception);
		}
		finally
		{
			IOUtils.closeQuietly((Reader)bufferedreader);
		}
		
		return metadataSerializer.<T>parseMetadataSection(sectionName, jsonobject);
	}
	
	//==================================================================================================================
	private final File assetsFolder;
	
	//==================================================================================================================
    public CubeitResourceFolder(final File parAssetsFolder)
    {
        this.assetsFolder = parAssetsFolder;
    }

	//==================================================================================================================
    @Override
    public boolean resourceExists(final ResourceLocation location)
    {
        return getFileFromLocation(location).exists();
    }

    @Nullable
    @Override
    public <T extends IMetadataSection> T getPackMetadata(final MetadataSerializer metadataSerializer,
    													  final String metadataSectionName) throws IOException
    {
        try
        {
            final InputStream inputstream = new FileInputStream(new File(assetsFolder, "assets.info"));
            return readMetadata(metadataSerializer, inputstream, metadataSectionName);
        }
        catch (final Exception ex) {}
        return (T) null;
    }

	//==================================================================================================================
    @Override
    public InputStream getInputStream(final ResourceLocation location) throws IOException
    {
        final InputStream stream = this.getInputStreamAssets(location);

        if (stream != null)
        {
            return stream;
        }
        else
        {
            throw new FileNotFoundException(location.getResourcePath());
        }
    }
    
    @Override
    public Set<String> getResourceDomains()
    {
    	return ImmutableSet.of(GameManager.getInstance().gameInfo.gameId);
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException
    {
    	return null;
    }
    
    @Override
    public String getPackName() 
    {
    	return GameManager.getInstance().gameInfo.gameName;
    }
    
	//==================================================================================================================
    @Nullable
    public InputStream getInputStreamAssets(final ResourceLocation location) throws IOException, FileNotFoundException
    {
        final File file = getFileFromLocation(location);
        return file != null && file.isFile() ? new FileInputStream(file) : null;
    }
    
    private File getFileFromLocation(final ResourceLocation location)
    {
    	return new File(assetsFolder, location.getResourceDomain() + "/" + location.getResourcePath());
    }
}
