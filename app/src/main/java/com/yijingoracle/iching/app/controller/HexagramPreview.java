package com.yijingoracle.iching.app.controller;
 
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.yijingoracle.iching.app.*;
import com.yijingoracle.iching.core.*;


public class HexagramPreview implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        fillPreview();

        _decompositionController.setBrowser(_browser);

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
                    previewHexagram(_text);
                }
                catch(Exception e)
                {
                    _text = "";
                }

                if (_text.length() >= 2)
                    _text = "";
            }
        });
    }

    private void previewHexagram(String id)
    {
        int val = Integer.parseInt(id);

        if(val >= 1 && val <= Hexagram.COUNT)
        {
            VBox group = (VBox)((VBox)_preview.getContent()).getChildren().get(val - 1);

            _selector.selectNode(group, () ->
            {
                HexagramRegion hex = (HexagramRegion)group.getChildren().get(1);
                _preview.setVvalue((val - 1) / (double)(Hexagram.COUNT - 1));
                loadHexagram(hex.getHexagram());
            });
        }
    }

    private void loadHexagram(Hexagram hex)
    {
        _decompositionController.setHexagram(hex);
    }

    private void fillPreview()
    {
        VBox stack = new VBox();
        stack.setSpacing(30);

        for(int i = 1; i <= Hexagram.COUNT; ++i)
        {
            HexagramRegion hex = new HexagramRegion(new Hexagram(i));

            javafx.scene.text.Text label = new javafx.scene.text.Text();
            label.setText(String.format("%d", i));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setFont(new Font("Arial", 32));

            VBox group = new VBox();
            group.getChildren().add(label);
            group.getChildren().add(hex);
            group.getStyleClass().add(DEFAULT_CLASS);

            group.setOnMouseClicked(e -> _selector.selectNode(group, () ->
            {
                HexagramRegion hex1 = (HexagramRegion)group.getChildren().get(1);
                _preview.requestFocus();
                loadHexagram(hex1.getHexagram());
            }));

            stack.getChildren().add(group);
        }

        _preview.setContent(stack);
    }

    private static final String DEFAULT_CLASS = "hexagram";

    private NodeSelectGroup _selector = new NodeSelectGroup("white", "#336699");

    @FXML private ScrollPane _preview;
    @FXML private Browser _browser;
    @FXML private VBox _decomposition;
    @FXML private Decomposition _decompositionController;
}

