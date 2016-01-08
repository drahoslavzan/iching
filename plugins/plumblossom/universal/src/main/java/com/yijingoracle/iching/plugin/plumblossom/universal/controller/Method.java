package com.yijingoracle.iching.plugin.plumblossom.universal.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            event.consume();
        }
        else
        {
            _text += event.getText();
        }
    }

    private String _text = "";

    @FXML private Browser _browser;
    @FXML private LocalDateTimeTextField _date;
}

