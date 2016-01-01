package com.yijingoracle.iching.app.controller;
 
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.yijingoracle.iching.app.*;
import com.yijingoracle.iching.core.*;


public class HexagramPreview implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        fillPreview();

        _preview.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
        {
            private static final long SEARCH_PERIOD = 1000;

            private String _text;
            private long _lastStamp;

            @Override
            public void handle(final KeyEvent keyEvent)
            {
                long stamp = System.currentTimeMillis();

                if(stamp - _lastStamp > SEARCH_PERIOD)
                    _text = "";

                _lastStamp = stamp;
                _text += keyEvent.getText();

                try
                {
                    int val = Integer.parseInt(_text);

                    if(val >= 1 && val <= Hexagram.HEXAGRAM_COUNT)
                    {
                        VBox group = (VBox)((VBox)_preview.getContent()).getChildren().get(val - 1);

                        if(_selectedHexagram == group)
                            return;

                        if(_selectedHexagram != null)
                        {
                            _selectedHexagram.getStyleClass().remove(SELECTED_CLASS);
                            _selectedHexagram.getStyleClass().add(DEFAULT_CLASS);
                        }

                        _selectedHexagram = group;
                        group.getStyleClass().remove(DEFAULT_CLASS);
                        group.getStyleClass().add(SELECTED_CLASS);

                        Hexagram hex = (Hexagram)group.getChildren().get(1);

                        _preview.setVvalue((val - 1) / (double)(Hexagram.HEXAGRAM_COUNT - 1));
                        _browser.load(_textFactory.getText().getHexagramText(hex.getHexagramId()));
                    }
                }
                catch(Exception e)
                {
                    _text = "";
                }

                if(_text.length() >= 2)
                    _text = "";
            }
        });
    }

    private void fillPreview()
    {
        VBox stack = new VBox();
        stack.setSpacing(30);

        for(int i = 1; i <= Hexagram.HEXAGRAM_COUNT; ++i)
        {
            Hexagram hex = new Hexagram(i);

            javafx.scene.text.Text label = new javafx.scene.text.Text();
            label.setText(String.format("%d", i));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setFont(new Font("Arial", 32));

            VBox group = new VBox();
            group.getChildren().add(label);
            group.getChildren().add(hex);
            group.getStyleClass().add(DEFAULT_CLASS);

            group.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    if(_selectedHexagram != null)
                    {
                        _selectedHexagram.getStyleClass().remove(SELECTED_CLASS);
                        _selectedHexagram.getStyleClass().add(DEFAULT_CLASS);
                    }

                    _selectedHexagram = group;
                    group.getStyleClass().remove(DEFAULT_CLASS);
                    group.getStyleClass().add(SELECTED_CLASS);

                    Hexagram hex = (Hexagram)group.getChildren().get(1);

                    _preview.requestFocus();
                    _browser.load(_textFactory.getText().getHexagramText(hex.getHexagramId()));
                }
            });

            stack.getChildren().add(group);
        }

        _preview.setContent(stack);
    }

    private static final String DEFAULT_CLASS = "hexagram";
    private static final String SELECTED_CLASS = "hexagram-selected";

    private TextFactory _textFactory = new TextFactory();
    private Node _selectedHexagram;

    @FXML
    private ScrollPane _preview;

    @FXML
    private Browser _browser;
}

