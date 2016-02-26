package com.yijingoracle.iching.plugin.plumblossom.mysteries.controller;

import com.yijingoracle.iching.core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class Result implements Initializable, TextFactoryCallback
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            _decompositionRight.setSelector(_decompositionLeft.getSelector());

            _textFactory.register(this);
        }
        catch(Exception e)
        {
            com.yijingoracle.iching.core.util.Dialog.showException(e);
        }
    }

    public void loadResult(Hexagram left, String date, int line)
    {
        loadHexagram(left);

        _calcDate.setText(date);
        _calcLine.setText(String.valueOf(line));

        loadHexagramText(left);
    }

    private void loadHexagram(Hexagram left)
    {
        Hexagram right = left.getChangingHexagram();

        _decompositionRight.setHexagram(right, _textFactory.getText().getHexagramTitle(right.getId()));
        _decompositionLeft.setHexagram(left, _textFactory.getText().getHexagramTitle(left.getId()));
    }

    @Override
    public synchronized void onTextChanged(Text text)
    {
        if (_lastText != null)
            _lastText.run();

        Hexagram hexLeft = _decompositionLeft.getHexagram();
        Hexagram hexRight = _decompositionRight.getHexagram();

        if (hexLeft != null && hexRight != null)
        {
            _decompositionLeft.setHexagramTitle(_textFactory.getText().getHexagramTitle(hexLeft.getId()));
            _decompositionRight.setHexagramTitle(_textFactory.getText().getHexagramTitle(hexRight.getId()));
        }
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
    @FXML private HexagramDecomposition _decompositionLeft;
    @FXML private HexagramDecomposition _decompositionRight;
    @FXML private TextField _calcDate;
    @FXML private TextField _calcLine;
}
