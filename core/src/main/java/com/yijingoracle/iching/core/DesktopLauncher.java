package com.yijingoracle.iching.core;

import java.awt.Desktop;
import java.io.IOException;


public class DesktopLauncher
{
    public static void launchDesktopBrowser(String url)
    {
        new Thread(() ->
        {
            try
            {
                Desktop.getDesktop().browse(java.net.URI.create(url));
            }
            catch (IOException e)
            {
            }
        }).start();
    }
}