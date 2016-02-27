package com.yijingoracle.iching.app;


import com.yijingoracle.iching.core.Const;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileLoader
{
    public static File[] loadFilesFromDirectoryRelativeToJar(String directory, String suffix)
    {
        try
        {
            String path = FileLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File jarFile = new File(path);
            File loc = new File(jarFile.getParent() + File.separator + directory);

            return loc.listFiles(file -> file.getPath().toLowerCase().endsWith(suffix));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void getRemoteStream(URL file)
    {
        try
        {
            /*
            ReadableByteChannel rbc = Channels.newChannel(file.openStream());

            String local = id + ".jar";
            FileOutputStream fos = new FileOutputStream("." + File.separator + Const.PLUGIN_PATH + File.separator + local);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            */
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }
}
