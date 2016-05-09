package com.goiching.iching.app;

import com.thoughtworks.xstream.XStream;
import com.goiching.iching.core.Const;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


class UpdateChecker
{
    public static void checkForUpdates(UpdateCheckerCallback callback)
    {
        new Thread(() ->
        {
            try
            {
                XStream xstream = new XStream();

                xstream.alias("iching", Update.class);
                xstream.alias("plugin", Update.Plugin.class);
                xstream.alias("plugins", Update.PluginList.class);
                xstream.addImplicitCollection(Update.PluginList.class, "plugins");

                InputStream file = getRemoteUpdateFile(Const.SITE_UPDATE);
                Update update = (Update) xstream.fromXML(file);

                callback.onUpdate(update);
            }
            catch (Exception e)
            {
                //Platform.runLater(() -> Dialog.showException(e));
            }
        }).start();
    }

    private static InputStream getRemoteUpdateFile(String file) throws IOException
    {
        URL url = new URL(file);
        return url.openStream();
    }
}
