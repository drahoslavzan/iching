package app.ichingoracle.plugin.plumblossom.masters;

import app.ichingoracle.core.MethodPlugin;
import app.ichingoracle.core.MethodPluginCallback;
import app.ichingoracle.plugin.plumblossom.masters.controller.Method;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;


public class Plugin implements MethodPlugin
{
    @Override
    public String getId() { return "plumblossom-masters"; }

    @Override
    public String getVersion() { return "1.0"; }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public String getHash() { return License.HLOCAL; }

    @Override
    public Node getMethod()
    {
        Node node;

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setDefaultClassLoader(getClass().getClassLoader());

            loader.setResources(_bundle);
            node = loader.load(getClass().getResource("/masters/fxml/Method.fxml").openStream());

            VBox content = new VBox();
            content.getChildren().add(_license);
            content.getChildren().add(node);
            content.setVgrow(node, Priority.ALWAYS);
            node = content;

            Method controller = loader.getController();

            controller.register(this);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        return node;
    }

    @Override
    public Node getResult()
    {
        return _result;
    }

    public void onResult(Node node)
    {
        _result = node;
        _subscriber.onResult(this);
    }

    @Override
    public void register(MethodPluginCallback subscriber)
    {
        _subscriber = subscriber;
    }

    public Plugin()
    {
        try
        {
            _bundle = ResourceBundle.getBundle("masters/plugin", new Locale("en"));
            _name = _bundle.getString("name");

            InputStream file = getClass().getResource("/info").openStream();
            String info = License.checkLicenseAndGetUserInfo(file, getHash());
            _license = License.getUserInfoContainer(info);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private MethodPluginCallback _subscriber;
    private ResourceBundle _bundle;
    private String _name;
    private Node _result;
    private HBox _license;
}

