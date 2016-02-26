package com.yijingoracle.iching.plugin.plumblossom.masters.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.yijingoracle.iching.core.Hexagram;
import com.yijingoracle.iching.core.Trigram;
import com.yijingoracle.iching.plugin.plumblossom.masters.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jfxtras.scene.control.LocalDateTimeTextField;
import com.yijingoracle.iching.core.Browser;


public class Method implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            URL method = getClass().getResource("/masters/description.html");

            _browser.loadUrl(method);

            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/masters/fxml/Result.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("masters/plugin", new Locale("en"));
            loader.setResources(bundle);

            _node = loader.load();
            _result = loader.getController();
        }
        catch(Exception e)
        {
            com.yijingoracle.iching.core.util.Dialog.showException(e);
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private boolean computeHexagramAndLoadResult(String text)
    {
        int hexIndex = 20;
        int line = 2;

        Hexagram hex = new Hexagram(hexIndex);
        hex.changeLine(line);

        _result.loadResult(hex, line);

        return true;
    }

    private int modulateNumber(int num, int mod) { return ((num - 1) % mod) + 1; }

    private Plugin _subscriber;
    private Node _node;
    private Result _result;

    @FXML private Browser _browser;
}

