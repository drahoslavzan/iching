package com.goiching.iching.plugin.plumblossom.longterm.controller;

import com.goiching.iching.core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class Result implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("longterm/plugin", new Locale("en"));

            _tabClassic.setText(bundle.getString("tabClassic"));
            _tabWithoutLine.setText(bundle.getString("tabWithoutLine"));
            _tabMovingLine.setText(bundle.getString("tabMovingLine"));
        }
        catch(Exception e)
        {
            com.goiching.iching.core.util.Dialog.showException(e);
        }
    }

    public void loadResult(Hexagram left, String queryUp, int queryUpLength, String queryDown, int queryDownLength, int line)
    {
        _classicController.loadResult(left, queryUp, queryUpLength, queryDown, queryDownLength, line);
        _withoutLineController.loadResult(new Hexagram(left.getId()), queryUp, queryUpLength, queryDown, queryDownLength);
        _movingLineController.loadResult(new Hexagram(left.getId()), queryUp, queryUpLength, queryDown, queryDownLength);
    }

    @FXML private Tab _tabClassic;
    @FXML private Tab _tabWithoutLine;
    @FXML private Tab _tabMovingLine;
    @FXML private ScrollPane _classic;
    @FXML private ScrollPane _withoutLine;
    @FXML private ScrollPane _movingLine;
    @FXML private Classic _classicController;
    @FXML private WithoutLine _withoutLineController;
    @FXML private MovingLine _movingLineController;
}
