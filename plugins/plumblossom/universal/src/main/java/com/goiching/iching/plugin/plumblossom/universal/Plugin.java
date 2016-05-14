package com.goiching.iching.plugin.plumblossom.universal;

import com.goiching.iching.core.*;
import com.goiching.iching.plugin.plumblossom.universal.controller.Method;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.util.Locale;
import java.util.ResourceBundle;


public class Plugin implements MethodPlugin
{
    @Override
    public String getId() { return "plumblossom-universal"; }

    @Override
    public String getVersion() { return "1.0"; }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public String getHash() { return HASH; }

    @Override
    public Node getMethod()
    {
        Node node;

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setDefaultClassLoader(getClass().getClassLoader());

            loader.setResources(_bundle);
            node = loader.load(getClass().getResource("/universal/fxml/Method.fxml").openStream());

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
            _bundle = ResourceBundle.getBundle("universal/plugin", new Locale("en"));
            _name = _bundle.getString("name");
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

    private static final String HASH = "0123456789ABCDEFGHIJKLMNOPQRSTUV";
}

