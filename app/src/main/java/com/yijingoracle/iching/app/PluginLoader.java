package com.yijingoracle.iching.app;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import com.yijingoracle.iching.core.AppPlugin;


class PluginLoader
{
    public List<AppPlugin> loadPlugins()
    {
        List<AppPlugin> ret = new ArrayList<>();

        try
        {
            File loc = new File(Const.PLUGIN_PATH);

            File[] flist = loc.listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));

            URL[] urls = new URL[flist.length];
            for (int i = 0; i < flist.length; i++)
                urls[i] = flist[i].toURI().toURL();

            URLClassLoader ucl = new URLClassLoader(urls);

            final ServiceLoader<AppPlugin> plugins = ServiceLoader.load(AppPlugin.class, ucl);

            for (final AppPlugin plugin : plugins)
                ret.add(plugin);
        }
        catch (Exception ignored)
        {
        }

        return ret;
    }
}
