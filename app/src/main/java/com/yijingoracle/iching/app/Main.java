package com.yijingoracle.iching.app;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.yijingoracle.iching.core.util.*;
import com.yijingoracle.iching.app.controller.ResultWindow;
import com.yijingoracle.iching.core.AppPlugin;
import com.yijingoracle.iching.core.AppPluginCallback;


public class Main extends Application implements AppPluginCallback
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            ResultWindowFactory.setOwner(stage);

            ResourceBundle bundle = ResourceBundle.getBundle("iching", new Locale("en"));
            Scene root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"), bundle);

            _tabs = (TabPane) root.lookup("#_tabs");

            URL style = getClass().getResource("/iching.css");
            root.getStylesheets().add(style.toExternalForm());

            stage.setScene(root);
            stage.setTitle(bundle.getString("name"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
            stage.setMinWidth(Const.MIN_WIDTH);
            stage.setMinHeight(Const.MIN_HEIGHT);
            stage.setWidth(Const.DEFAULT_WIDTH);
            stage.setHeight(Const.DEFAULT_HEIGHT);

            loadPlugins();

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
    public void onResult()
    {
        ResultWindowFactory rwf = new ResultWindowFactory();

        ResultWindow result = rwf.getResultWindow();

        result.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void loadPlugins()
    {
        try
        {
            PluginLoader pl = new PluginLoader();

            List<AppPlugin> plugins = pl.loadPlugins();

            plugins.forEach(this::insertPlugin);

            ResourceBundle bundle = ResourceBundle.getBundle("iching", new Locale("en"));
            Node more = FXMLLoader.load(getClass().getResource("/fxml/tabs/About.fxml"), bundle);

            Tab tab = new Tab(bundle.getString("morePlugins"));
            tab.setStyle("-fx-background-color: #ff5555;");
            tab.setContent(more);
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
            Tab tab = new Tab();

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
}

