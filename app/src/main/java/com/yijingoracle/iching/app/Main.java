package com.yijingoracle.iching.app;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.yijingoracle.iching.core.util.*;


public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();

            ResourceBundle bundle = ResourceBundle.getBundle("iching", new Locale("en"));
            Scene root = loader.load(getClass().getResource("/fxml/Main.fxml"), bundle);

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
        }
        catch(Exception e)
        {
            Dialog.showException(e);
            Platform.exit();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private void loadPlugins()
    {
        PluginLoader pl = new PluginLoader();

        Node[] plugins = pl.loadPlugins(Const.PLUGIN_PATH);

        for (Node plugin : plugins)
        {
            Tab tab = new Tab();

            tab.setContent(plugin);

            _tabs.getTabs().add(tab);
        }
    }

    @FXML
    private TabPane _tabs;
}

