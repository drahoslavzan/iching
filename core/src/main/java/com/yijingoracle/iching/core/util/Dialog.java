package com.yijingoracle.iching.core.util;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;


public class Dialog
{
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

    public static void showException(Exception e)
    {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Error");
        alert.setContentText(String.format("%s", String.join("\n", getExceptionMessageChain(e))));
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 320);

        alert.showAndWait();
    }
}
