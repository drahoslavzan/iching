package com.goiching.iching.core.util;

import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Dialog
{
    public static void messageBox(String title, String header, Node content)
    {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.getDialogPane().contentProperty().set(content);

        setAlertIcon(alert);

        alert.showAndWait();
    }

    public static void showException(Exception e)
    {
        Alert alert = getAlert(e, null);

        setAlertIcon(alert);

        alert.showAndWait();
    }

    public static void showException(Exception e, String msg)
    {
        Alert alert = getAlert(e, msg);

        setAlertIcon(alert);

        alert.showAndWait();
    }

    public static void setIcon(Image icon)
    {
        _icon = new ImageView(icon);
        _graphic = new ImageView(icon);

        _graphic.setFitWidth(30);
        _graphic.setFitHeight(30);
    }

    private static void setAlertIcon(Alert alert)
    {
        if (_icon != null)
        {
            alert.setGraphic(_graphic);
            ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(_icon.getImage());
        }
    }

    private static List<String> getExceptionMessageChain(Throwable throwable)
    {
        List<String> result = new ArrayList<>();

        while (throwable != null)
        {
            result.add(String.format("%s: %s", throwable.getClass().getSimpleName(), throwable.getMessage()));
            throwable = throwable.getCause();
        }

        return result;
    }

    private static Alert getAlert(Exception e, String msg)
    {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Error");
        alert.setContentText(String.format("%s%s",
            msg != null ? msg + ":\n" : "",
            String.join("\n", getExceptionMessageChain(e))));
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 320);

        return alert;
    }

    private static ImageView _icon;
    private static ImageView _graphic;
}
