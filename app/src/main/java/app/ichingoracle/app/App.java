package app.ichingoracle.app;

import app.ichingoracle.core.*;
import app.ichingoracle.app.controller.ResultWindow;
import app.ichingoracle.core.util.Dialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;


public class App extends Application implements MethodPluginCallback, UpdateCheckerCallback
{
    @Override
    public void start(Stage stage)
    {
        try
        {
            ResultWindowFactory.setOwner(stage);

            Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
            Dialog.setIcon(icon);

            Scene root = getMainScene();

            ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

            stage.setScene(root);
            stage.setTitle(bundle.getString("name"));
            stage.getIcons().add(icon);
            stage.setMinWidth(Const.MIN_WIDTH);
            stage.setMinHeight(Const.MIN_HEIGHT);
            stage.setWidth(Const.DEFAULT_WIDTH);
            stage.setHeight(Const.DEFAULT_HEIGHT);

            Stage splash = getSplashScreen();
            splash.show();

            loadResourcesAndPerformFinalUiAction(() ->
            {
                splash.close();
                stage.show();
            });

            UpdateChecker.checkForUpdates(this);
        }
        catch(Exception e)
        {
            Dialog.showException(e);
            Platform.exit();
        }
    }

    @Override
    public void onResult(MethodPlugin plugin)
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

            updateNews(update);
            updatePlugins(update);
            updateApplication(update);
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

    private Scene getMainScene() throws IOException
    {
        ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));
        Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"), bundle);

        ((Hyperlink) scene.lookup("#_linkTexts")).setOnAction(event -> DesktopLauncher.launchDesktopBrowser(Const.SITE_TEXTS));
        _tabs = (TabPane) scene.lookup("#_tabs");
        _textCombo = (ComboBox<String>) scene.lookup("#_texts");
        _aboutCentral = (VBox) scene.lookup("#_aboutCentral");

        URL style = getClass().getResource("/app.css");
        scene.getStylesheets().add(style.toExternalForm());

        allowDragAndDropForPlugins(_tabs);
        allowDragAndDropForTexts(_textCombo);

        return scene;
    }

    private void allowDragAndDropForTexts(Node node)
    {
        node.setOnDragOver(event ->
        {
            Dragboard db = event.getDragboard();

            if (db.hasFiles())
            {
                List<File> files = db.getFiles();
                ArrayList<File> del = files.stream().filter(file -> !new TextLoader().isTextFile(file)).collect(Collectors.toCollection(ArrayList::new));
                del.forEach(files::remove);

                if (!files.isEmpty()) event.acceptTransferModes(TransferMode.COPY);
            }
            else
            {
                event.consume();
            }
        });

        node.setOnDragDropped(event ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles())
            {
                success = true;
                List<File> files = db.getFiles();
                ArrayList<File> del = files.stream().filter(file -> !new TextLoader().isTextFile(file)).collect(Collectors.toCollection(ArrayList::new));
                del.forEach(files::remove);

                files.forEach(this::installAndLoadText);
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void allowDragAndDropForPlugins(Node node)
    {
        node.setOnDragOver(event ->
        {
            Dragboard db = event.getDragboard();

            if (db.hasFiles())
            {
                List<File> files = db.getFiles();
                ArrayList<File> del = files.stream().filter(file -> !PluginLoader.isMethodPluginFile(file)).collect(Collectors.toCollection(ArrayList::new));
                del.forEach(files::remove);

                if (!files.isEmpty()) event.acceptTransferModes(TransferMode.COPY);
            }
            else
            {
                event.consume();
            }
        });

        node.setOnDragDropped(event ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles())
            {
                success = true;
                List<File> files = db.getFiles();
                ArrayList<File> del = files.stream().filter(file -> !PluginLoader.isMethodPluginFile(file)).collect(Collectors.toCollection(ArrayList::new));
                del.forEach(files::remove);

                files.forEach(this::installAndLoadPlugin);
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void installAndLoadText(File file)
    {
        TextLoader tl = new TextLoader();

        try
        {
            Text text = tl.installText(file);

            insertAndSelectText(text);
        }
        catch(Exception e)
        {
            Dialog.showException(e, file.getPath());
        }

        FlowPane fp = new FlowPane();
        Label label = new Label("Text installed successfully");
        fp.getChildren().addAll(label);

        Dialog.messageBox("Success", String.format("%s", file.getName()), fp);
    }

    private void installAndLoadPlugin(File file)
    {
        PluginLoader pl = new PluginLoader();

        try
        {
            MethodPlugin plugin = pl.installMethodPlugin(file);

            insertAndSelectPlugin(plugin);
        }
        catch(Exception e)
        {
            Dialog.showException(e, file.getPath());
            return;
        }

        FlowPane fp = new FlowPane();
        Label label = new Label("Plugin installed successfully");
        fp.getChildren().addAll(label);

        Dialog.messageBox("Success", String.format("%s", file.getName()), fp);
    }

    private void updateApplication(Update update)
    {
        if (update.getAppVersion().compareToIgnoreCase(Const.VERSION) < 0)
        {
            try
            {
                Path path = FileLoader.getPathToJar(App.class);
                FileLoader.downloadJarFile(update.getLink(), path);
            }
            catch(Exception ex)
            {
                ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

                String title = bundle.getString("updateTitle");
                String header = String.format("%s: %s %s", bundle.getString("updateVersion"), bundle.getString("name"), update.getAppVersion());

                FlowPane fp = new FlowPane();
                Hyperlink link = new Hyperlink(update.getLink());
                link.setOnAction(e -> DesktopLauncher.launchDesktopBrowser(update.getLink()));
                fp.getChildren().addAll(link);

                Platform.runLater(() -> Dialog.messageBox(title, header, fp));
            }
        }
    }

    private void updateNews(Update update)
    {
        List<Update.Info> news = update.getNews();

        if (news == null || news.isEmpty())
            return;

        FlowPane box = new FlowPane();

        box.getStyleClass().add("about-news");

        for (Update.Info info : news)
        {
            Hyperlink link = new Hyperlink(info.getName());
            link.setOnAction(e -> DesktopLauncher.launchDesktopBrowser(update.getLink()));
            box.getChildren().add(link);
        }

        Platform.runLater(() -> _aboutCentral.getChildren().add(0, box));
    }

    private void updatePlugin(String link, String id)
    {
        try
        {
            File dl = FileLoader.downloadJarFile(link);
            File dest = new File(dl.getParent() + File.separator + id + Const.PLUGIN_SUFFIX);

            Files.move(dl.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            dest.deleteOnExit();

            PluginLoader.installMethodPlugin(dest);
        }
        catch(Exception e)
        {
            System.out.println(e);
            //Platform.runLater(() -> Dialog.showException(e));
        }
    }

    private void updatePlugins(Update update)
    {
        List<Update.Plugin> pluginList = update.getPlugins();

        if (pluginList == null || pluginList.isEmpty())
            return;

        for (Update.Plugin plugin : pluginList)
        {
            Map.Entry<MethodPlugin, Tab> entry = _plugins.entrySet().stream().filter(p -> p.getKey().getId().equals(plugin.getId())).findFirst().orElse(null);

            if (entry == null)
            {
                Platform.runLater(() -> insertNotPresentPlugin(plugin));
            }
            else if (entry.getKey().getVersion().compareToIgnoreCase(plugin.getVersion()) < 0)
            {
                updatePlugin(String.format("%s?%s", plugin.getLink(), entry.getKey().getHash()), plugin.getId());
            }
        }
    }

    private Pane getSplashLayout()
    {
        Image img = new Image(getClass().getResourceAsStream("/image/iching.png"));
        ImageView splash = new ImageView(img);
        VBox imgLayout = new VBox();

        _splashWidth = (int) (img.getWidth() / 4.0);
        _splashHeight = (int) (img.getHeight() / 4.0);

        splash.setFitWidth(_splashWidth);
        splash.setFitHeight(_splashHeight);
        imgLayout.getChildren().add(splash);
        imgLayout.setPadding(new Insets(30));

        ProgressBar loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(_splashWidth);

        ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

        Label progressText = new Label(bundle.getString("loading"));
        VBox splashLayout = new VBox();
        splashLayout.getChildren().addAll(imgLayout, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
        splashLayout.setEffect(new DropShadow());

        return splashLayout;
    }

    private Stage getSplashScreen()
    {
        Scene splashScene = new Scene(getSplashLayout());

        final Rectangle2D bounds = Screen.getPrimary().getBounds();

        ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(bundle.getString("name"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
        stage.setScene(splashScene);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - _splashWidth / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - _splashHeight / 2);

        return stage;
    }

    private void loadTexts()
    {
        try
        {
            TextLoader tl = new TextLoader();

            List<Text> texts = tl.loadTexts();

            texts.forEach(t -> insertAndSelectText(t));

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
            Platform.runLater(() -> Dialog.showException(e));
        }
    }

    private boolean insertAndSelectText(Text text)
    {
        try
        {
            _texts.put(text.getName(), text);
            _textCombo.getItems().remove(text.getName());
            _textCombo.getItems().add(text.getName());

            TextFactory.setText(text);
            _textCombo.setValue(text.getName());
        }
        catch (Exception e)
        {
            Dialog.showException(e);
            return false;
        }

        return true;
    }

    private void loadPlugins()
    {
        try
        {
            PluginLoader pl = new PluginLoader();

            List<MethodPlugin> plugins = pl.loadMethodPlugins();
            plugins.forEach(this::insertAndSelectPlugin);
            _tabs.getSelectionModel().select(0);
        }
        catch (Exception e)
        {
            Platform.runLater(() -> Dialog.showException(e));
        }
        finally
        {
            synchronized (_sync)
            {
                _pluginsLoaded = true;
                _sync.notifyAll();
            }
        }
    }

    private void insertNotPresentPlugin(Update.Plugin plugin)
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));

            Tab tabClick = new Tab(plugin.getName());
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

                    DesktopLauncher.launchDesktopBrowser(plugin.getLink());
                }
            });

            _tabs.getTabs().add(tabClick);
            _plugins.put(new MethodPlugin()
            {
                 @Override
                 public String getId() { return plugin.getId(); }

                 @Override
                 public String getVersion() { return "0.0"; }

                 @Override
                 public String getName() { return plugin.getName(); }

                 @Override
                 public String getHash() { return null; }

                 @Override
                 public Node getMethod() { return null; }

                 @Override
                 public Node getResult() { return null; }

                 @Override
                 public void register(MethodPluginCallback subscriber) {}
             }, tabClick);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
        }
    }

    private boolean insertAndSelectPlugin(MethodPlugin plugin)
    {
        assert _tabs != null;

        try
        {
            int pos = 3;

            MethodPlugin found = _plugins.keySet().stream().filter(p -> p.getId().equals(plugin.getId())).findFirst().orElse(null);

            if (found != null)
            {
                if (found.getVersion().compareToIgnoreCase(plugin.getVersion()) >= 0)
                    return false;

                Tab tab = _plugins.remove(found);
                pos = _tabs.getTabs().indexOf(tab);
                _tabs.getTabs().remove(tab);
            }

            plugin.register(this);

            Tab tab = new Tab();

            tab.setContent(plugin.getMethod());
            tab.setText(plugin.getName());
            tab.setGraphic(getMethodTabIcon());
            tab.setClosable(false);

            _tabs.getTabs().add(pos, tab);
            _tabs.getSelectionModel().select(tab);
            _plugins.put(plugin, tab);
        }
        catch (Exception e)
        {
            Dialog.showException(e);
            return false;
        }

        return true;
    }

    private ImageView getMethodTabIcon()
    {
        Image tabIcon = new Image(getClass().getResourceAsStream("/image/method.png"));
        ImageView tabIconView = new ImageView(tabIcon);
        tabIconView.setFitWidth(18);
        tabIconView.setFitHeight(18);

        return tabIconView;
    }

    private void loadResourcesAndPerformFinalUiAction(Runnable action)
    {
        new Thread(() ->
        {
            long loadTimestamp = System.currentTimeMillis();

            Platform.runLater(this::loadPlugins);

            loadTexts();

            tryWaitForPluginsLoaded();

            long loadTime = System.currentTimeMillis() - loadTimestamp;

            trySleepFor(SPLASH_SHOW_TIME - loadTime);

            Platform.runLater(action);

            synchronized (_sync)
            {
                _appRunning = true;
                _sync.notifyAll();
            }
        }).start();
    }

    private void tryWaitForPluginsLoaded()
    {
        try
        {
            synchronized (_sync)
            {
                while (!_pluginsLoaded) _sync.wait();
            }
        }
        catch(Exception e)
        {
            // Ignored
        }
    }

    private void trySleepFor(long milliseconds)
    {
        if (milliseconds <= 0) return;

        try
        {
            Thread.sleep(milliseconds);
        }
        catch(Exception e)
        {
            // Ignored
        }
    }

    private final long SPLASH_SHOW_TIME = 2000;

    private static int _splashWidth;
    private static int _splashHeight;
    private Object _sync = new Object();
    private boolean _appRunning = false;
    private boolean _pluginsLoaded = false;

    private TabPane _tabs;
    private Map<MethodPlugin, Tab> _plugins = new HashMap<>();
    private ComboBox<String> _textCombo;
    private Dictionary<String, Text> _texts = new Hashtable<>();
    private VBox _aboutCentral;
}

