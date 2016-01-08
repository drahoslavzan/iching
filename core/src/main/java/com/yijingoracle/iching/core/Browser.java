package com.yijingoracle.iching.core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.net.URL;


public class Browser extends Region
{
    public Browser()
    {
        WebEngine engine = _view.getEngine();
        engine.setJavaScriptEnabled(false);

        _view.setContextMenuEnabled(false);

        setZoomOnScroll();

        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
        {
            @Override
            public void changed(ObservableValue ov, State oldState, State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    EventListener listener = (ev) ->
                    {
                        String domEventType = ev.getType();

                        if (domEventType.equals(EVENT_TYPE_CLICK))
                        {
                            //String href = ((Element)ev.getTarget()).getAttribute("href");
                            //System.out.println(href);

                            ev.preventDefault();
                        }
                    };

                    Document doc = engine.getDocument();
                    NodeList nodeList = doc.getElementsByTagName("a");
                    for (int i = 0; i < nodeList.getLength(); i++)
                    {
                        ((EventTarget) nodeList.item(i)).addEventListener(EVENT_TYPE_CLICK, listener, false);
                    }
                }
            }
        });

        getChildren().add(_view);
    }

    public void loadUrl(URL file)
    {
        WebEngine engine = _view.getEngine();

        engine.load(file.toString());
    }

    public void load(String content)
    {
        WebEngine engine = _view.getEngine();

        engine.loadContent(content);
    }

    @Override
    protected void layoutChildren()
    {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(_view, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    private void setZoomOnScroll()
    {
        _view.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>()
        {
            private double MIN_ZOOM  = 0.5;
            private double MAX_ZOOM  = 3.0;
            private double ZOOM_STEP = 0.1;

            private double _zoom = 1.0;

            @Override
            public void handle(ScrollEvent e)
            {
                if(e.isControlDown())
                {
                    if(e.getDeltaY() > 0)
                        _zoom += ZOOM_STEP;
                    else
                        _zoom -= ZOOM_STEP;

                    _zoom = Math.min(_zoom, MAX_ZOOM);
                    _zoom = Math.max(_zoom, MIN_ZOOM);

                    _view.setZoom(_zoom);

                    e.consume();
                }
            }
        });
    }

    private static final String EVENT_TYPE_CLICK = "click";
    private final WebView _view = new WebView();
}
