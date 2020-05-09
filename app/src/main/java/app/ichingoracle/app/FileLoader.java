package app.ichingoracle.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipFile;


public class FileLoader
{
    public static File[] loadFilesFromDirectoryRelativeToJar(String directory, String suffix)
    {
        try
        {
            File loc = new File(getRunningFolder() + directory);

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
            Path dest = Paths.get(getRunningFolder() + directory + File.separator + file.getName());

            Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static InputStream getRemoteStream(URL file) throws IOException
    {
        return file.openStream();
    }

    public static File downloadJarFile(String link)
    {
        try
        {
            File temp = File.createTempFile("iching", ".tmp");

            InputStream remote = getRemoteStream(new URL(link));

            Files.copy(remote, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (isJarValid(temp))
                return temp;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }

        throw new RuntimeException(String.format("File %s is not valid JAR file", link));
    }

    public static void downloadJarFile(String link, Path local)
    {
        try
        {
            File dl = downloadJarFile(link);

            Files.move(dl.toPath(), local, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Path getPathToJar(Class<?> cls)
    {
        try
        {
            return new File(cls.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toPath();
        }
        catch(URISyntaxException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean isJarValid(final File file)
    {
        ZipFile zipfile = null;

        try
        {
            zipfile = new ZipFile(file);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            try
            {
                if (zipfile != null)
                    zipfile.close();
            } catch (IOException e)
            {
            }
        }
    }

    private static String getRunningFolder() throws java.net.URISyntaxException
    {
        String path = FileLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return new File(path).getParent() + File.separator;
    }
}
