package com.yijingoracle.iching.app;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import com.yijingoracle.iching.core.AppPlugin;
import com.yijingoracle.iching.core.Const;
import com.yijingoracle.iching.core.util.Dialog;


class PluginLoader
{
    public List<AppPlugin> loadPlugins()
    {
        List<AppPlugin> ret = new ArrayList<>();

        try
        {
            Path exePath = Paths.get(PluginLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            File loc = new File(exePath.getParent() + File.separator + Const.PLUGIN_PATH);

            File[] flist = loc.listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));

            if (flist == null)
            {
                return ret;
            }

            URL[] urls = new URL[flist.length];
            for (int i = 0; i < flist.length; i++)
                urls[i] = flist[i].toURI().toURL();

            URLClassLoader ucl = new URLClassLoader(urls);

            final ServiceLoader<AppPlugin> plugins = ServiceLoader.load(AppPlugin.class, ucl);

            for (final AppPlugin plugin : plugins)
                ret.add(plugin);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }

        return ret;
    }

    public void installPlugin(String id)
    {
        try
        {
            URL remote = new URL(Const.SITE_PLUGINS + "/" + id);
            ReadableByteChannel rbc = Channels.newChannel(remote.openStream());

            String local = id + ".jar";
            FileOutputStream fos = new FileOutputStream("." + File.separator + Const.PLUGIN_PATH + File.separator + local);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch(Exception e)
        {
            Dialog.showException(e);
        }
    }
}
