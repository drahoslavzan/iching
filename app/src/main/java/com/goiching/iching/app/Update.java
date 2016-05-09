package com.goiching.iching.app;

import java.util.ArrayList;
import java.util.List;


public class Update
{
    public class Plugin
    {
        private String id;
        private String name;
        private String version;
        private String download;

        public String getId() { return id; }
        public String getVersion() { return version; }
        public String getName() { return name; }
        public String getDownload() { return download; }
    };

    public class PluginList { public List<Update.Plugin> plugins = new ArrayList<>(); }

    private String version;
    private String download;
    private PluginList plugins = new PluginList();

    public String getAppVersion() { return version; }
    public String getDownload() { return download; }
    public List<Plugin> getPlugins() { return plugins.plugins; }
}
