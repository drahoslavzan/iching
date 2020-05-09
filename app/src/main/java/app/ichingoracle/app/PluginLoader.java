package app.ichingoracle.app;

import app.ichingoracle.core.MethodPlugin;
import app.ichingoracle.core.Const;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.*;


class PluginLoader
{
    public static MethodPlugin installMethodPlugin(File file)
    {
        try
        {
            if (!file.getName().endsWith(Const.PLUGIN_SUFFIX))
                throw new RuntimeException(String.format("File does not end with '%s' suffix", Const.PLUGIN_SUFFIX));

            MethodPlugin plugin = getMethodPluginFromFile(file);

            if (plugin != null)
            {
                copyPlugin(file);
                return plugin;
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        throw new RuntimeException("Not a valid plugin");
    }

    public static boolean isMethodPluginFile(File file)
    {
        try
        {
            if (!file.getName().endsWith(Const.PLUGIN_SUFFIX))
                return false;

            MethodPlugin plugin = getMethodPluginFromFile(file);

            if (plugin != null)
                return true;
        }
        catch (Exception e)
        {
            // Ignored
        }

        return false;
    }

    public static void uninstallMethodPlugin(File file)
    {
        try
        {
            if (!file.getName().endsWith(Const.PLUGIN_SUFFIX))
                throw new RuntimeException(String.format("File does not end with '%s' suffix", Const.PLUGIN_SUFFIX));

            Files.delete(file.toPath());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<MethodPlugin> loadMethodPlugins()
    {
        List<MethodPlugin> ret = new ArrayList<>();

        URL[] urls = getUrlPathsToFilesInPluginFolder();

        for (URL url : urls)
        {
            try
            {
                URLClassLoader ucl = new URLClassLoader(new URL[] { url });

                final ServiceLoader<MethodPlugin> plugins = ServiceLoader.load(MethodPlugin.class, ucl);

                for (final MethodPlugin plugin : plugins)
                {
                    MethodPlugin exist = ret.stream().filter(p -> p.getId().equals(plugin)).findFirst().orElse(null);

                    if (exist != null)
                    {
                        if (exist.getVersion().compareToIgnoreCase(plugin.getVersion()) >= 0)
                            continue;

                        ret.remove(exist);
                    }

                    ret.add(plugin);
                }
            }
            catch (ServiceConfigurationError e)
            {
            }
            catch (Exception e)
            {
            }
        }

        return ret;
    }

    private static URLClassLoader getClassLoaderForFile(File file) throws java.net.MalformedURLException
    {
        URL[] urls = new URL[] { file.toURI().toURL() };
        return new URLClassLoader(urls);
    }

    private static MethodPlugin getMethodPluginFromFile(File file) throws java.net.MalformedURLException
    {
        URLClassLoader ucl = getClassLoaderForFile(file);

        final ServiceLoader<MethodPlugin> plugins = ServiceLoader.load(MethodPlugin.class, ucl);

        return plugins.iterator().next();
    }

    private static URL[] getUrlPathsToFilesInPluginFolder()
    {
        try
        {
            File[] fList = FileLoader.loadFilesFromDirectoryRelativeToJar(Const.PLUGIN_PATH, Const.PLUGIN_SUFFIX);

            if (fList == null)
                return new URL[0];

            URL[] urls = new URL[fList.length];
            for (int i = 0; i < fList.length; i++)
                urls[i] = fList[i].toURI().toURL();

            return urls;
        }
        catch(MalformedURLException e)
        {
        }

        return new URL[0];
    }

    private static void copyPlugin(File file)
    {
        FileLoader.copyFileToDirectoryRelativeToJar(file, Const.PLUGIN_PATH);
    }
}
