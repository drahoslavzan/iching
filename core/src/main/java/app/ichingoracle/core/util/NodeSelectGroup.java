package app.ichingoracle.core.util;

import javafx.scene.Node;


public class NodeSelectGroup
{
    public NodeSelectGroup(String defaultColor, String selectedColor)
    {
        _default = String.format("-fx-background-color: %s;", defaultColor);
        _selected = String.format("-fx-background-color: %s;", selectedColor);
    }

    public void clearSelect()
    {
        if(_lastNodeSelected != null)
        {
            _lastNodeSelected.setStyle(_default);
            _lastNodeSelected = null;
        }
    }

    public boolean selectNode(Node node)
    {
        if (_lastNodeSelected == node)
            return false;

        clearSelect();

        node.setStyle(_selected);

        _lastNodeSelected = node;

        return true;
    }

    public Node getSelectedNode()
    {
        return _lastNodeSelected;
    }

    public void selectNode(Node node, Runnable action)
    {
        if (selectNode(node) && action != null)
            action.run();
    }

    private Node _lastNodeSelected;
    private String _default;
    private String _selected;
}
