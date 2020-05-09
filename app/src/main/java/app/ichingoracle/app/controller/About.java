package app.ichingoracle.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import java.net.URL;
import java.util.ResourceBundle;
import app.ichingoracle.core.Const;
import app.ichingoracle.core.DesktopLauncher;


public class About implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        _link.setOnAction(event -> DesktopLauncher.launchDesktopBrowser(Const.SITE_HTTP));
    }

    @FXML private Hyperlink _link;
}
