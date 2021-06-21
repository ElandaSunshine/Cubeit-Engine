package xyz.elandasunshine.cvm.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;

import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.ResourcePackFileNotFoundException;
import net.minecraft.util.Util;

public class CubeitResourceFolder extends AbstractResourcePack
{
    private static final boolean field_191386_b = Util.getOSType() == Util.EnumOS.WINDOWS;
    private static final CharMatcher field_191387_c = CharMatcher.is('\\');

    public CubeitResourceFolder(File resourcePackFileIn)
    {
        super(resourcePackFileIn);
    }

    protected static boolean func_191384_a(File p_191384_0_, String p_191384_1_) throws IOException
    {
        String s = p_191384_0_.getCanonicalPath();

        if (field_191386_b)
        {
            s = field_191387_c.replaceFrom(s, '/');
        }

        return s.endsWith(p_191384_1_);
    }

    protected InputStream getInputStreamByName(String name) throws IOException
    {
        File file1 = this.func_191385_d(name.substring(6));

        if (file1 == null)
        {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
        }
        else
        {
            return new BufferedInputStream(new FileInputStream(file1));
        }
    }

    protected boolean hasResourceName(String name)
    {
        return this.func_191385_d(name.substring(6)) != null;
    }

    @Nullable
    private File func_191385_d(String p_191385_1_)
    {
        try
        {
            File file1 = new File(this.resourcePackFile, p_191385_1_);

            if (file1.isFile() && func_191384_a(file1, p_191385_1_))
            {
                return file1;
            }
        }
        catch (IOException var3)
        {
            ;
        }

        return null;
    }

    public Set<String> getResourceDomains()
    {
        return ImmutableSet.of("cubeit");
    }
    
    public String getPackName()
    {
        return "Cubeit Default";
    }
}
