package com.yijingoracle.iching.app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import com.yijingoracle.iching.app.NodeSelectGroup;
import com.yijingoracle.iching.app.TextFactory;
import com.yijingoracle.iching.core.*;


public class Decomposition implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        setOnClick(_hexagram.getParent(), () -> _browser.load(_textFactory.getText().getHexagramText(_hexagram.getHexagram().getId())));
        setOnClick(_ut.getParent(), () -> _browser.load(_textFactory.getText().getTrigramText(_ut.getTrigram().getName())));
        setOnClick(_uct.getParent(), () -> _browser.load(_textFactory.getText().getTrigramText(_uct.getTrigram().getName())));
        setOnClick(_lct.getParent(), () -> _browser.load(_textFactory.getText().getTrigramText(_lct.getTrigram().getName())));
        setOnClick(_lt.getParent(), () -> _browser.load(_textFactory.getText().getTrigramText(_lt.getTrigram().getName())));
    }

    public void setSelector(NodeSelectGroup selector)
    {
        _selector = selector;
    }

    public void setBrowser(Browser browser)
    {
        _browser = browser;
    }

    public void setHexagram(Hexagram hex)
    {
        Text text = _textFactory.getText();

        _hexagramTitle.setText(String.format("%d. %s", hex.getId(), text.getHexagramTitle(hex.getId())));
        _hexagram.setHexagram(hex);

        Hexagram.Decomposition decs = hex.decompose();

        _ut.setTrigram(new Trigram(decs.getUpperTrigram()));
        _uct.setTrigram(new Trigram(decs.getUpperCentralTrigram()));
        _lct.setTrigram(new Trigram(decs.getLowerCentralTrigram()));
        _lt.setTrigram(new Trigram(decs.getLowerTrigram()));

        _selector.clearSelect();
        _selector.selectNode(_hexagram.getParent(), () ->
        {
            if (_browser != null)
                _browser.load(_textFactory.getText().getHexagramText(_hexagram.getHexagram().getId()));
        });

        _initialized = true;
    }

    private void setOnClick(Node node, Runnable action)
    {
        node.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                if (!_initialized)
                    return;

                _selector.selectNode(node, () ->
                {
                    if (_browser != null)
                        action.run();
                });
            }
        });
    }

    private Browser _browser;
    private NodeSelectGroup _selector = new NodeSelectGroup("white", "yellow");
    private TextFactory _textFactory = new TextFactory();
    private boolean _initialized;

    @FXML private Label _hexagramTitle;
    @FXML private HexagramRegion _hexagram;
    @FXML private TrigramRegion _ut;
    @FXML private TrigramRegion _uct;
    @FXML private TrigramRegion _lct;
    @FXML private TrigramRegion _lt;
}