package com.yijingoracle.iching.app;

import com.yijingoracle.iching.app.controller.ResultWindow;
import com.yijingoracle.iching.core.*;
import com.yijingoracle.iching.core.util.Dialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application implements AppPluginCallback, UpdateCheckerCallback
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
            _textCombo = (ComboBox<String>) root.lookup("#_texts");

            URL style = getClass().getResource("/app.css");
            root.getStylesheets().add(style.toExternalForm());

            stage.setScene(root);
            stage.setTitle(bundle.getString("name"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
            stage.setMinWidth(Const.MIN_WIDTH);
            stage.setMinHeight(Const.MIN_HEIGHT);
            stage.setWidth(Const.DEFAULT_WIDTH);
            stage.setHeight(Const.DEFAULT_HEIGHT);

            Stage splash = showSplashScreen();

            new Thread(() ->
            {
                loadTexts();

                Platform.runLater(this::loadPlugins);

                try
                {
                    //Thread.sleep(1000);
                }
                catch(Exception e)
                {
                }

                Platform.runLater(() ->
                {
                    splash.close();
                    stage.show();
                });

                synchronized (_sync)
                {
                    _appRunning = true;
                    _sync.notify();
                }
            }).start();

            UpdateChecker.checkForUpdates(this);
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

    @Override
    public void onUpdate(Update update)
    {
        try
        {
            synchronized (_sync)
            {
                while(!_appRunning || !_pluginsLoaded) _sync.wait();
            }

            if (!update.getAppVersion().equals(Const.VERSION))
            {
                Image icon = new Image(getClass().getResourceAsStream("/image/method.png"));
                ImageView iconView = new ImageView(icon);

                ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

                String title = bundle.getString("updateTitle");
                String header = String.format("%s: %s %s", bundle.getString("updateVersion"), bundle.getString("name"), update.getAppVersion());

                FlowPane fp = new FlowPane();
                Hyperlink link = new Hyperlink(Const.SITE_APP_DOWNLOAD);
                link.setOnAction(e -> DesktopLauncher.launchDesktopBrowser(Const.SITE_APP_DOWNLOAD));
                fp.getChildren().addAll(link);

                Platform.runLater(() -> Dialog.messageBox(title, header, fp, iconView));
            }

            List<Update.Plugin> pluginList = update.getPlugins();

            for (Update.Plugin plugin : pluginList)
            {
                Map.Entry<AppPlugin, Tab> entry = _plugins.entrySet().stream().filter(p -> p.getKey().getId().equals(plugin.getId())).findFirst().orElse(null);

                if (entry == null)
                    Platform.runLater(() -> addClickTab(plugin.getName(), plugin.getDownload()));
                else if (!entry.getKey().getVersion().equals(plugin.getVersion()))
                {
                    Platform.runLater(() ->
                    {
                        Hyperlink link = new Hyperlink(plugin.getDownload());
                        link.setOnAction(e -> DesktopLauncher.launchDesktopBrowser(plugin.getDownload()));
                        Label label = new Label(String.format("There is a new version %s available:", plugin.getVersion()));
                        label.setStyle("-fx-background-color: #eebbbb;");

                        HBox hbox = new HBox(5);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        hbox.getChildren().addAll(label, link);

                        Tab tab = entry.getValue();
                        Node node = tab.getContent();

                        VBox vbox = new VBox(10);
                        vbox.getChildren().addAll(hbox, node);
                        vbox.setVgrow(node, Priority.ALWAYS);
                        tab.setContent(vbox);
                    });
                }
            }
        }
        catch(Exception e)
        {
            Platform.runLater(() -> Dialog.showException(e));
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    private Pane getSplashLayout()
    {
        Image img = new Image(getClass().getResourceAsStream("/image/splash.png"));
        ImageView splash = new ImageView(img);

        SPLASH_WIDTH = (int) img.getWidth();
        SPLASH_HEIGHT = (int) img.getHeight();

        ProgressBar loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH);

        ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

        Label progressText = new Label(bundle.getString("loading"));
        VBox splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
        splashLayout.setEffect(new DropShadow());

        return splashLayout;
    }

    private Stage showSplashScreen()
    {
        Scene splashScene = new Scene(getSplashLayout());

        final Rectangle2D bounds = Screen.getPrimary().getBounds();

        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(splashScene);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);

        stage.show();

        return stage;
    }

    private void loadTexts()
    {
        try
        {
            TextLoader tl = new TextLoader();

            List<Text> texts = tl.loadTexts();

            texts.forEach((t) ->
            {
                _texts.put(t.getName(), t);
                _textCombo.getItems().add(t.getName());
            });

            if (!texts.isEmpty())
            {
                TextFactory.setText(texts.get(0));
                _textCombo.setValue(texts.get(0).getName());
            }

            _textCombo.valueProperty().addListener((selected, oldVal, newVal) ->
            {
                TextFactory.setText(_texts.get(newVal));
            });
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private void loadPlugins()
    {
        try
        {
            PluginLoader pl = new PluginLoader();

            List<AppPlugin> plugins = pl.loadPlugins();
            plugins.forEach(this::insertPlugin);

            synchronized (_sync)
            {
                _pluginsLoaded = true;
                _sync.notify();
            }
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private void addClickTab(String name, String path)
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

            Tab tabClick = new Tab(name);
            tabClick.setStyle("-fx-background-color: #eebbbb;");
            tabClick.setGraphic(getMethodTabIcon());
            tabClick.setClosable(false);
            tabClick.setTooltip(new Tooltip(bundle.getString("getPlugin")));

            _tabs.getSelectionModel().selectedItemProperty().addListener((arg0, prev, recent) ->
            {
                if (recent.equals(tabClick))
                {
                    SingleSelectionModel<Tab> selectionModel = _tabs.getSelectionModel();
                    selectionModel.select(prev);

                    DesktopLauncher.launchDesktopBrowser(path);
                }
            });

            _tabs.getTabs().add(tabClick);
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

            tab.setContent(plugin.getMethod());
            tab.setText(plugin.getName());
            tab.setGraphic(getMethodTabIcon());
            tab.setClosable(false);

            _tabs.getTabs().add(tab);
            _plugins.put(plugin, tab);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private ImageView getMethodTabIcon()
    {
        Image tabIcon = new Image(getClass().getResourceAsStream("/image/method.png"));
        ImageView tabIconView = new ImageView(tabIcon);
        tabIconView.setFitWidth(18);
        tabIconView.setFitHeight(18);

        return tabIconView;
    }

    private static int SPLASH_WIDTH;
    private static int SPLASH_HEIGHT;
    private Object _sync = new Object();
    private boolean _appRunning = false;
    private boolean _pluginsLoaded = false;

    private TabPane _tabs;
    private Map<AppPlugin, Tab> _plugins = new HashMap<>();
    private ComboBox<String> _textCombo;
    private Dictionary<String, Text> _texts = new Hashtable<>();
}

