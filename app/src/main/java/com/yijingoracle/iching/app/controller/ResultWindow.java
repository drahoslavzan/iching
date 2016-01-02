package com.yijingoracle.iching.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.yijingoracle.iching.app.Const;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public void show()
    {
        _stage.setX(_stage.getOwner().getX() + Const.RESULT_WINDOW_OFFSET);
        _stage.setY(_stage.getOwner().getY() + Const.RESULT_WINDOW_OFFSET);

        _stage.show();
    }

    @FXML private Stage _stage;
}

