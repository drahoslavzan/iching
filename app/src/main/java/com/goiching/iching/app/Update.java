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
        private String link;

        public String getId() { return id; }
        public String getVersion() { return version; }
        public String getName() { return name; }
        public String getLink() { return link; }
    };

    public class Info
    {
        private String name;
        private String link;

        public String getName() { return name; }
        public String getLink() { return link; }
    };

    public class PluginList { public List<Update.Plugin> plugins = new ArrayList<>(); }
    public class NewsList { public List<Update.Info> news = new ArrayList<>(); }

    private String version;
    private String link;
    private PluginList plugins = new PluginList();
    private NewsList news = new NewsList();

    public String getAppVersion() { return version; }
    public String getLink() { return link; }
    public List<Plugin> getPlugins() { return plugins.plugins; }
    public List<Info> getNews() { return news.news; }
}
