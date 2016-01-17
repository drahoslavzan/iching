package com.yijingoracle.iching.plugin.plumblossom.universal.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.yijingoracle.iching.core.AppPlugin;
import com.yijingoracle.iching.plugin.plumblossom.universal.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
            _date.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd MMMM yyyy    hh a"));
            _date.setLocalDateTime(LocalDateTime.now());

            URL method = getClass().getResource("/universal.html");

            _browser.loadUrl(method);
        }
        catch(Exception e)
        {
            com.yijingoracle.iching.core.util.Dialog.showException(e);
        }
    }

    public void onKeyPressed(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            if (_subscriber != null)
            {
                Node node = CreateResult();
                _subscriber.onResult(node);
            }

            event.consume();
        }
        else
        {
            _text += event.getText();
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private Node CreateResult()
    {
        Node node;

        try
        {
            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
            ResourceBundle bundle = ResourceBundle.getBundle("plugin", new Locale("en"));

            node = FXMLLoader.load(getClass().getResource("/fxml/Result.fxml"), bundle);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        return node;
    }

    private String _text = "";
    private Plugin _subscriber;

    @FXML private Browser _browser;
    @FXML private LocalDateTimeTextField _date;
}

