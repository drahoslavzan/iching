package com.yijingoracle.iching.app.controller;
 
import com.yijingoracle.iching.core.TextFactoryCallback;
import com.yijingoracle.iching.core.Text;
import com.yijingoracle.iching.core.util.NodeSelectGroup;
import com.yijingoracle.iching.core.TextFactory;
import com.yijingoracle.iching.core.Browser;
import com.yijingoracle.iching.core.Trigram;
import com.yijingoracle.iching.core.TrigramRegion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class TrigramPreview implements Initializable, TextFactoryCallback
{
    @FXML
    public void onQueryKeyPressed(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String str = _query.getText().replaceAll("\\s", "");

            if(str.length() < 1)
                return;

            int value = str.length() % 8;

            Node node = getNodeFromGridPane(_preview, value - 1, 0);

            _selector.selectNode(node, () -> loadTrigramText(Trigram.getNameFromEarlyHeavenValue(value)));
        }
    }

    private void fillPreview()
    {
        for(int i = 0; i < Trigram.COUNT; ++i)
        {
            VBox group = new VBox();

            Trigram trigram = new Trigram(Trigram.getNameFromEarlyHeavenValue(i + 1));
            TrigramRegion trigramRegion = new TrigramRegion(trigram);

            group.getChildren().add(trigramRegion);
            group.getStyleClass().add(DEFAULT_CLASS);

            group.setOnMouseClicked(e -> _selector.selectNode(group, () -> loadTrigramText(trigram.getName())));

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / Trigram.COUNT);
            _preview.getColumnConstraints().add(column);

            _preview.add(group, i, 0);
        }
    }

    private void loadTrigramText(Trigram.Name name)
    {
        _lastText = () -> _browser.load(_textFactory.getText().getTrigramText(name));

        _lastText.run();
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        _textFactory.register(this);

        fillPreview();
    }

    @Override
    public void onTextChanged(Text text)
    {
        if (_lastText != null)
            _lastText.run();
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row)
    {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                return node;

        return null;
    }

    private static final String DEFAULT_CLASS = "trigram";

    private TextFactory _textFactory = new TextFactory();
    private NodeSelectGroup _selector = new NodeSelectGroup("white", "#336699");
    private Runnable _lastText;

    @FXML private TextField _query;
    @FXML private Browser _browser;
    @FXML private GridPane _preview;
}

