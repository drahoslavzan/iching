package com.yijingoracle.iching.plugin.plumblossom.universal.controller;

import com.yijingoracle.iching.core.Hexagram;
import com.yijingoracle.iching.core.HexagramDecomposition;
import com.yijingoracle.iching.core.SelectEvent;
import com.yijingoracle.iching.core.Trigram;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class Result implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
        }
        catch(Exception e)
        {
            com.yijingoracle.iching.core.util.Dialog.showException(e);
        }
    }

    @FXML
    private void onSelectElement(SelectEvent event)
    {
        if (event.getSelected() instanceof Hexagram)
        {
            System.out.println("HEX");
        }
        else if  (event.getSelected() instanceof Trigram)
        {
            System.out.println("TRIG");
        }
    }

    @FXML private HexagramDecomposition _decompositionLeft;
    @FXML private HexagramDecomposition _decompositionRight;
}
