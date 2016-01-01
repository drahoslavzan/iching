package com.yijingoracle.iching.app.controller;
 
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.yijingoracle.iching.app.*;
import com.yijingoracle.iching.core.*;


public class TrigramPreview implements Initializable
{
    @FXML
    public void onQueryKeyPressed(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String str = _query.getText().replaceAll("\\s", "");

            if(str.length() < 1)
                return;

            int id = (str.length() - 1) % 8 + 1;

            if(_lastNodeSelected != null)
            {
                _lastNodeSelected.getStyleClass().remove(SELECTED_CLASS);
                _lastNodeSelected.getStyleClass().add(DEFAULT_CLASS);
            }

            Node node = getNodeFromGridPane(_preview, id - 1, 0);

            _lastNodeSelected = node;
            node.getStyleClass().remove(DEFAULT_CLASS);
            node.getStyleClass().add(SELECTED_CLASS);

            _browser.load(_textFactory.getText().getTrigramText(id));
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        fillPreview();
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row)
    {
        for (Node node : gridPane.getChildren())
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                return node;

        return null;
    }

    private void fillPreview()
    {
        for(int i = 1; i <= Trigram.TRIGRAM_COUNT; ++i)
        {
            VBox group = new VBox();

            Trigram trig = new Trigram(i);

            group.getChildren().add(trig);
            group.getStyleClass().add(DEFAULT_CLASS);

            group.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e)
                {
                    if(_lastNodeSelected != null)
                    {
                        _lastNodeSelected.getStyleClass().remove(SELECTED_CLASS);
                        _lastNodeSelected.getStyleClass().add(DEFAULT_CLASS);
                    }

                    _lastNodeSelected = group;
                    group.getStyleClass().remove(DEFAULT_CLASS);
                    group.getStyleClass().add(SELECTED_CLASS);

                    _browser.load(_textFactory.getText().getTrigramText(trig.getTrigramId()));
                }
            });

            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / Trigram.TRIGRAM_COUNT);
            _preview.getColumnConstraints().add(column);

            _preview.add(group, i - 1, 0);
        }
    }

    private static final String DEFAULT_CLASS = "trigram";
    private static final String SELECTED_CLASS = "trigram-selected";

    private TextFactory _textFactory = new TextFactory();
    private Node _lastNodeSelected;

    @FXML
    private TextField _query;

    @FXML
    private Browser _browser;

    @FXML
    private GridPane _preview;
}

