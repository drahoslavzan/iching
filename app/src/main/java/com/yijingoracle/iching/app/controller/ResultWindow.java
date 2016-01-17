package com.yijingoracle.iching.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.yijingoracle.iching.app.Const;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ResultWindow implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
    }

    public void setTitle(String title)
    {
        _stage.setTitle(title);
    }

    public void setResult(Node node)
    {
        _root.getChildren().clear();
        _root.getChildren().add(node);
    }

    public void show()
    {
        _stage.show();
    }

    @FXML private Stage _stage;
    @FXML private VBox _root;
}

