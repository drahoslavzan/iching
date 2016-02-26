package com.yijingoracle.iching.plugin.plumblossom.longterm.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import com.yijingoracle.iching.core.Hexagram;
import com.yijingoracle.iching.core.Trigram;
import com.yijingoracle.iching.plugin.plumblossom.longterm.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            URL method = getClass().getResource("/longterm/description.html");

            _browser.loadUrl(method);

            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/longterm/fxml/Result.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("longterm/plugin", new Locale("en"));
            loader.setResources(bundle);

            _node = loader.load();
            _result = loader.getController();
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
            String textUp = _queryUp.getText();
            String textDown = _queryDown.getText();

            if (_subscriber != null)
            {
                if(computeHexagramAndLoadResult(textUp, textDown))
                    _subscriber.onResult(_node);
            }

            event.consume();
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private String filterText(String text)
    {
        return text.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("  +", " ");
    }

    private int getLengthWithoutWhitespace(String text)
    {
        return text.replaceAll(" ", "").length();
    }

    private boolean computeHexagramAndLoadResult(String textUp, String textDown)
    {
        String queryUp = filterText(textUp);
        String queryDown = filterText(textDown);
        int queryUpLength = getLengthWithoutWhitespace(queryUp);
        int queryDownLength = getLengthWithoutWhitespace(queryDown);

        if (queryUpLength < 1 || queryDownLength < 1)
            return false;

        int hexIndex = 20;
        int line = 2;

        Hexagram hex = new Hexagram(hexIndex);
        hex.changeLine(line);

        _result.loadResult(hex, queryUp, queryUpLength, queryDown, queryDownLength, line);

        return true;
    }

    private int modulateNumber(int num, int mod) { return ((num - 1) % mod) + 1; }

    private Plugin _subscriber;
    private Node _node;
    private Result _result;

    @FXML private Browser _browser;
    @FXML private TextField _queryUp;
    @FXML private TextField _queryDown;
}

