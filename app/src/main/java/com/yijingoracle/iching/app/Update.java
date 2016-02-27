package com.yijingoracle.iching.app;

import java.util.ArrayList;
import java.util.List;


public class Update
{
    public class Plugin
    {
        private String id;
        private String version;

        public String getId() { return id; }
        public String getVersion() { return version; }
    };

    public class PluginList { public List<Update.Plugin> plugins = new ArrayList<>(); }

    private String version;
    private PluginList plugins = new PluginList();

    public String getAppVersion() { return version; }
    public List<Plugin> getPlugins() { return plugins.plugins; }
}
