package com.yijingoracle.iching.plugin.plumblossom.universal;
 
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import com.yijingoracle.iching.core.*;


public class Plugin implements AppPlugin
{
    @Override
    public String getName()
    {
        return "Plugin1";
    }

    @Override
    public Node getMethod()
    {
        Node node;

        try
        {
            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());

            node = FXMLLoader.load(getClass().getResource("/fxml/Method.fxml"));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        return node;
    }

    @Override
    public void register(AppPluginCallback subscriber)
    {
        _subscriber = subscriber;
    }

    private AppPluginCallback _subscriber;
}

