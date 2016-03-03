package com.yijingoracle.iching.app;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileLoader
{
    public static File[] loadFilesFromDirectoryRelativeToJar(String directory, String suffix)
    {
        try
        {
            File loc = new File(getJarFolder() + directory);

            return loc.listFiles(file -> file.getPath().toLowerCase().endsWith(suffix));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void copyFileToDirectoryRelativeToJar(File file, String directory)
    {
        try
        {
            Path dest = Paths.get(getJarFolder() + directory + File.separator + file.getName());

            Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
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

    private static String getJarFolder() throws java.net.URISyntaxException
    {
        String path = FileLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return new File(path).getParent() + File.separator;
    }
}
