package com.yijingoracle.iching.core.util;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;


public class Dialog
{
    public static void showException(Exception e)
    {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Error");
        alert.setContentText(String.format("ERROR: %s", e.getMessage()));
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 320);

        alert.showAndWait();
    }
};
