package com.goiching.iching.plugin.plumblossom.longterm.controller;

import com.goiching.iching.core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class MovingLine implements Initializable, TextFactoryCallback
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            Image arrowUp = new Image(getClass().getResourceAsStream("/longterm/image/up.png"));
            ImageView viewUp = new ImageView(arrowUp);
            ImageView viewDown = new ImageView(arrowUp);
            viewDown.setRotate(180);

            _up.setGraphic(viewUp);
            _down.setGraphic(viewDown);

            _up.setOnAction(e ->
            {
                Hexagram hex = _decompositionLeft.getHexagram();

                if (_line < Hexagram.LINES)
                {
                    ++_line;
                    hex.changeLine(_line);
                }

                loadHexagramRight(hex);
            });

            _down.setOnAction(e ->
            {
                Hexagram hex = _decompositionLeft.getHexagram();

                if (_line > 1)
                {
                    hex.unchangeLine(_line);
                    --_line;
                }

                loadHexagramRight(hex);
            });

            _decompositionRight.setSelector(_decompositionLeft.getSelector());

            _textFactory.register(this);
        }
        catch(Exception e)
        {
            com.goiching.iching.core.util.Dialog.showException(e);
        }
    }

    public void loadResult(Hexagram left, String queryUp, int queryUpLength, String queryDown, int queryDownLength)
    {
        _line = 1;
        left.changeLine(_line);

        _calcQueryUp.setText(queryUp);
        _calcQueryUpLength.setText(String.valueOf(queryUpLength));
        _calcQueryDown.setText(queryDown);
        _calcQueryDownLength.setText(String.valueOf(queryDownLength));

        loadHexagramLeft(left);
    }

    private void moveHexagramLines(Hexagram hexagram, int dir)
    {
        int lStart = dir < 0 ? 2 : (Hexagram.LINES - 1);
        int lStop = dir < 0 ? (Hexagram.LINES + 1) : 0;

        for (int i = lStart; i != lStop; i -= dir)
        {
            if (hexagram.lineChanged(i))
            {
                int l = i + dir;

                hexagram.unchangeLine(i);
                hexagram.changeLine(l);
            }
        }
    }

    private void loadHexagramLeft(Hexagram left)
    {
        Hexagram right = left.getChangingHexagram();

        _decompositionRight.setHexagram(right, _textFactory.getText().getHexagramTitle(right.getId()));
        _decompositionLeft.setHexagram(left, _textFactory.getText().getHexagramTitle(left.getId()));

        loadHexagramText(left);
    }

    private void loadHexagramRight(Hexagram left)
    {
        Hexagram right = left.getChangingHexagram();

        _decompositionLeft.setHexagram(left, _textFactory.getText().getHexagramTitle(left.getId()));
        _decompositionRight.setHexagram(right, _textFactory.getText().getHexagramTitle(right.getId()));

        loadHexagramText(right);
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
    private int _line;

    @FXML private Button _up;
    @FXML private Button _down;
    @FXML private Browser _browser;
    @FXML private HexagramDecomposition _decompositionLeft;
    @FXML private HexagramDecomposition _decompositionRight;
    @FXML private TextField _calcQueryUp;
    @FXML private TextField _calcQueryDown;
    @FXML private TextField _calcQueryUpLength;
    @FXML private TextField _calcQueryDownLength;
}
