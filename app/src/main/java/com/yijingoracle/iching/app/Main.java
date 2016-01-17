package com.yijingoracle.iching.app;

import com.yijingoracle.iching.app.controller.ResultWindow;
import com.yijingoracle.iching.core.*;
import com.yijingoracle.iching.core.util.Dialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application implements AppPluginCallback
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            ResultWindowFactory.setOwner(stage);

            ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));
            Scene root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"), bundle);

            ((Hyperlink) root.lookup("#_linkTexts")).setOnAction(event -> DesktopLauncher.launchDesktopBrowser(Const.SITE_TEXTS));
            _tabs = (TabPane) root.lookup("#_tabs");
            _texts = (ComboBox<String>) root.lookup("#_texts");

            URL style = getClass().getResource("/app.css");
            root.getStylesheets().add(style.toExternalForm());

            stage.setScene(root);
            stage.setTitle(bundle.getString("name"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
            stage.setMinWidth(Const.MIN_WIDTH);
            stage.setMinHeight(Const.MIN_HEIGHT);
            stage.setWidth(Const.DEFAULT_WIDTH);
            stage.setHeight(Const.DEFAULT_HEIGHT);

            loadPlugins(bundle);
            loadTexts(bundle);

            stage.show();

            UpdateChecker.checkForUpdates();
        }
        catch(Exception e)
        {
            Dialog.showException(e);
            Platform.exit();
        }
    }

    @Override
    public void onResult(AppPlugin plugin)
    {
        ResultWindowFactory rwf = new ResultWindowFactory();

        ResultWindow result = rwf.getResultWindow();

        result.setTitle(plugin.getName());
        result.setResult(plugin.getResult());

        result.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void loadTexts(ResourceBundle bundle)
    {
        try
        {
            TextLoader tl = new TextLoader();

            List<Text> texts = tl.loadTexts();

            texts.forEach((t) -> _texts.getItems().add(t.getName()));

            if (!texts.isEmpty())
            {
                _texts.setValue(texts.get(0).getName());
            }
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private void loadPlugins(ResourceBundle bundle)
    {
        try
        {
            PluginLoader pl = new PluginLoader();

            List<AppPlugin> plugins = pl.loadPlugins();

            plugins.forEach(this::insertPlugin);

            Browser browser = new Browser();
            browser.enableJavaScript();
            browser.loadUrl(new URL("http://google.sk"));

            Tab tab = new Tab(bundle.getString("morePlugins"));
            tab.setStyle("-fx-background-color: #ff5555;");
            tab.setContent(browser);
            _tabs.getTabs().add(tab);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private void insertPlugin(AppPlugin plugin)
    {
        assert _tabs != null;

        try
        {
            plugin.register(this);

            Tab tab = new Tab();

            tab.setStyle("-fx-background-color: #55ff55;");
            tab.setContent(plugin.getMethod());
            tab.setText(plugin.getName());

            _tabs.getTabs().add(tab);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private TabPane _tabs;
    private ComboBox<String> _texts;
}

