package com.yijingoracle.iching.plugin.plumblossom.universal;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import jfxtras.scene.control.*;
import com.yijingoracle.iching.core.*;


public class Main implements Initializable
{
    private String _text = "";

    @FXML
    private Browser _browser;

    @FXML
    private LocalDateTimeTextField _date;

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
}

