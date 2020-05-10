package app.ichingoracle.core;


public class Const
{
    private static final boolean DEBUG = false;
    private static final String SITE_HTTP_PREFIX = DEBUG ? "http://" : "https://";

    public static final String SITE = DEBUG ? "localhost:3000" : "ichingoracle.app";
    public static final String SITE_HTTP = SITE_HTTP_PREFIX + SITE;
    public static final String SITE_DL = SITE_HTTP;
    public static final String SITE_TEXTS = SITE_DL + "/texts";
    public static final String SITE_UPDATE = SITE_DL + "/update.xml";

    public static final String VERSION = "1.0";
    public static final String PLUGIN_PATH = "plugins";
    public static final String TEXT_PATH = "texts";
    public static final String PLUGIN_SUFFIX = ".icp";
    public static final String TEXT_SUFFIX = ".ict";

    public static final int MIN_WIDTH = 640;
    public static final int MIN_HEIGHT = 480;
    public static final int DEFAULT_WIDTH = 1200;
    public static final int DEFAULT_HEIGHT = 800;
    public static final int RESULT_WINDOW_OFFSET = 80;
}
