package com.yijingoracle.iching.app;

import com.thoughtworks.xstream.XStream;
import com.yijingoracle.iching.core.util.Dialog;
import javafx.application.Platform;

import java.io.File;


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

                File file = getRemoteUpdateFile();
                Update update = (Update) xstream.fromXML(file);

                callback.onUpdate(update);
            }
            catch (Exception e)
            {
                //Platform.runLater(() -> Dialog.showException(e));
            }
        }).start();
    }

    private static File getRemoteUpdateFile()
    {
        return new File("./update.xml");
    }
}
