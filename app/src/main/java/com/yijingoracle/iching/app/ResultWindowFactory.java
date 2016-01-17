package com.yijingoracle.iching.app;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.fxml.*;
import com.yijingoracle.iching.app.controller.ResultWindow;
import com.yijingoracle.iching.core.util.Dialog;


class ResultWindowFactory
{
    public ResultWindowFactory()
    {
        if(_window != null)
            return;

        assert _owner != null;

        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("app", new Locale("en"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResultWindow.fxml"), bundle);

            Stage stage = loader.load();
            stage.initOwner(_owner);

            _window = loader.getController();

            URL style = getClass().getResource("/app.css");
            stage.getScene().getStylesheets().add(style.toExternalForm());

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon.png")));
            stage.setMinWidth(Const.MIN_WIDTH);
            stage.setMinHeight(Const.MIN_HEIGHT);
            stage.setWidth(Const.DEFAULT_WIDTH);
            stage.setHeight(Const.DEFAULT_HEIGHT);

            stage.setX(_owner.getX() + Const.RESULT_WINDOW_OFFSET);
            stage.setY(_owner.getY() + Const.RESULT_WINDOW_OFFSET);
        }
        catch(Exception e)
        {
            Dialog.showException(e);
        }
    }

    public ResultWindow getResultWindow() { return _window; }

    public static void setOwner(Stage owner)
    {
        _owner = owner;
    }

    private static ResultWindow _window;
    private static Stage _owner;
}

