package com.yijingoracle.iching.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import java.net.URL;
import java.util.ResourceBundle;
import com.yijingoracle.iching.core.Const;
import com.yijingoracle.iching.core.DesktopLauncher;


public class About implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        _link.setOnAction(event -> DesktopLauncher.launchDesktopBrowser(Const.SITE));
    }

    @FXML private Hyperlink _link;
}
