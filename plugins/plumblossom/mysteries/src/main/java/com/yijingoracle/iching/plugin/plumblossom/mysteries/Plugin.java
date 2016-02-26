package com.yijingoracle.iching.plugin.plumblossom.mysteries;

import com.yijingoracle.iching.core.*;
import com.yijingoracle.iching.plugin.plumblossom.mysteries.controller.Method;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.util.Locale;
import java.util.ResourceBundle;


public class Plugin implements AppPlugin
{
    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public Node getMethod()
    {
        Node node;

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setDefaultClassLoader(getClass().getClassLoader());

            loader.setResources(_bundle);
            node = loader.load(getClass().getResource("/mysteries/fxml/Method.fxml").openStream());

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
    public void register(AppPluginCallback subscriber)
    {
        _subscriber = subscriber;
    }

    public Plugin()
    {
        try
        {
            _bundle = ResourceBundle.getBundle("mysteries/plugin", new Locale("en"));
            _name = _bundle.getString("name");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private AppPluginCallback _subscriber;
    private ResourceBundle _bundle;
    private String _name;
    private Node _result;
}

