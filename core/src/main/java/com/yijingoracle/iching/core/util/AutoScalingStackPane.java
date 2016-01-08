package com.yijingoracle.iching.core.util;

import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class AutoScalingStackPane extends StackPane
{
    public void rescale()
    {
        if (!getChildren().isEmpty())
        {
            getChildren().forEach((c) ->
            {
                double xScale = getWidth() / c.getBoundsInLocal().getWidth();
                double yScale = getHeight() / c.getBoundsInLocal().getHeight();

                if (autoScale.get() == AutoScale.FILL)
                {
                    c.setScaleX(xScale);
                    c.setScaleY(yScale);
                }
                else if (autoScale.get() == AutoScale.FIT)
                {
                    double scale = Math.min(xScale, yScale);
                    c.setScaleX(scale);
                    c.setScaleY(scale);
                }
                else
                {
                    c.setScaleX(1d);
                    c.setScaleY(1d);
                }
            });
        }
    }

    public AutoScalingStackPane()
    {
        super();
        init();
    }

    public AutoScalingStackPane(Node content)
    {
        super(content);
        init();
    }

    public enum AutoScale
    {

        /**
         * No scaling - revert to behaviour of <code>StackPane</code>.
         */
        NONE,
        /**
         * Independently scaling in x and y so content fills whole region.
         */
        FILL,
        /**
         * Scale preserving content aspect ratio and center in available space.
         */
        FIT
    }

    private void init()
    {
        widthProperty().addListener((b, o, n) -> rescale());
        heightProperty().addListener((b, o, n) -> rescale());
    }

    private ObjectProperty<AutoScale> autoScale =
            new SimpleObjectProperty<>(this, "autoScale", AutoScale.FIT);

    public ObjectProperty<AutoScale> autoScaleProperty()
    {
        return autoScale;
    }

    public AutoScale getAutoScale()
    {
        return autoScale.getValue();
    }

    public void setAutoScale(AutoScale newAutoScale)
    {
        autoScale.setValue(newAutoScale);
    }
}

