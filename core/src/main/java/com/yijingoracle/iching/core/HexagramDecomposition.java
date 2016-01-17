package com.yijingoracle.iching.core;

import com.yijingoracle.iching.core.util.NodeSelectGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;


public class HexagramDecomposition extends VBox
{
    public HexagramDecomposition()
    {
        super();

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HexagramDecomposition.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        setOnClick(_hexagram.getParent(), () -> fireEvent(new SelectEvent(_hexagram.getHexagram())));
        setOnClick(_ut.getParent(), () -> fireEvent(new SelectEvent(_ut.getTrigram())));
        setOnClick(_uct.getParent(), () -> fireEvent(new SelectEvent(_uct.getTrigram())));
        setOnClick(_lct.getParent(), () -> fireEvent(new SelectEvent(_lct.getTrigram())));
        setOnClick(_lt.getParent(), () -> fireEvent(new SelectEvent(_lt.getTrigram())));
    }

    public void setHexagram(Hexagram hex, String title)
    {
        _hexagramTitle.setText(String.format("%d. %s", hex.getId(), title));
        _hexagram.setHexagram(hex);

        Hexagram.Decomposition decs = hex.decompose();

        _ut.setTrigram(new Trigram(decs.getUpperTrigram()));
        _uct.setTrigram(new Trigram(decs.getUpperCentralTrigram()));
        _lct.setTrigram(new Trigram(decs.getLowerCentralTrigram()));
        _lt.setTrigram(new Trigram(decs.getLowerTrigram()));

        _selector.clearSelect();
        _selector.selectNode(_hexagram.getParent(), () -> {});

        _initialized = true;
    }

    public final EventHandler<SelectEvent> getOnSelectElement()
    {
        return _onSelect;
    }

    public final void setOnSelectElement(EventHandler<SelectEvent> onSelectElement)
    {
        _onSelect = onSelectElement;
        setEventHandler(SelectEvent.SELECT_ACTION, onSelectElement);
    }

    public void setSelector(NodeSelectGroup selector)
    {
        _selector = selector;
    }

    public String getUtName()
    {
        return _utName.getText();
    }

    public void setUtName(String name)
    {
        _utName.setText(name);
    }

    public String getUctName()
    {
        return _uctName.getText();
    }

    public void setUctName(String name)
    {
        _uctName.setText(name);
    }

    public String getLctName()
    {
        return _lctName.getText();
    }

    public void setLctName(String name)
    {
        _lctName.setText(name);
    }

    public String getLtName()
    {
        return _ltName.getText();
    }

    public void setLtName(String name)
    {
        _ltName.setText(name);
    }

    private void setOnClick(Node node, Runnable action)
    {
        node.setOnMouseClicked(e ->
        {
            if (!_initialized)
                return;

            _selector.selectNode(node, action);
        });
    }

    private NodeSelectGroup _selector = new NodeSelectGroup("white", "yellow");
    private boolean _initialized;
    private EventHandler<SelectEvent> _onSelect;

    @FXML private Label _hexagramTitle;
    @FXML private HexagramRegion _hexagram;
    @FXML private TrigramRegion _ut;
    @FXML private TrigramRegion _uct;
    @FXML private TrigramRegion _lct;
    @FXML private TrigramRegion _lt;
    @FXML private Label _utName;
    @FXML private Label _uctName;
    @FXML private Label _lctName;
    @FXML private Label _ltName;
}
