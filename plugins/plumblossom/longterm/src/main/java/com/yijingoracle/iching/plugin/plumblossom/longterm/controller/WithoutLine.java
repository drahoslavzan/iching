package com.yijingoracle.iching.plugin.plumblossom.longterm.controller;

import com.yijingoracle.iching.core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class WithoutLine implements Initializable, TextFactoryCallback
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            _textFactory.register(this);
        }
        catch(Exception e)
        {
            com.yijingoracle.iching.core.util.Dialog.showException(e);
        }
    }

    public void loadResult(Hexagram hex, String queryUp, int queryUpLength, String queryDown, int queryDownLength)
    {
        loadHexagram(hex);

        _calcQueryUp.setText(queryUp);
        _calcQueryUpLength.setText(String.valueOf(queryUpLength));
        _calcQueryDown.setText(queryDown);
        _calcQueryDownLength.setText(String.valueOf(queryDownLength));

        loadHexagramText(hex);
    }

    private void loadHexagram(Hexagram hex)
    {
        _decomposition.setHexagram(hex, _textFactory.getText().getHexagramTitle(hex.getId()));
    }

    @Override
    public synchronized void onTextChanged(Text text)
    {
        if (_lastText != null)
            _lastText.run();

        Hexagram hex = _decomposition.getHexagram();

        if (hex != null)
            _decomposition.setHexagramTitle(_textFactory.getText().getHexagramTitle(hex.getId()));
    }

    private synchronized void loadHexagramText(Hexagram hex)
    {
        _lastText = () -> _browser.load(_textFactory.getText().getHexagramText(hex.getId()));

        _lastText.run();
    }

    private synchronized void loadTrigramText(Trigram trig)
    {
        _lastText = () -> _browser.load(_textFactory.getText().getTrigramText(trig.getName()));

        _lastText.run();
    }

    @FXML
    private void onSelectElement(SelectEvent event)
    {
        if (event.getSelected() instanceof Hexagram)
        {
            loadHexagramText((Hexagram)event.getSelected());
        }
        else if  (event.getSelected() instanceof Trigram)
        {
            loadTrigramText((Trigram)event.getSelected());
        }
    }

    private Runnable _lastText;
    private TextFactory _textFactory = new TextFactory();

    @FXML private Browser _browser;
    @FXML private HexagramDecomposition _decomposition;
    @FXML private TextField _calcQueryUp;
    @FXML private TextField _calcQueryDown;
    @FXML private TextField _calcQueryUpLength;
    @FXML private TextField _calcQueryDownLength;
}
