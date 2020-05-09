package app.ichingoracle.core;

import javafx.event.Event;
import javafx.event.EventType;


public class SelectEvent extends Event
{
    public static final EventType<SelectEvent> SELECT_ACTION = new EventType<>("SELECT_ACTION");
    private final Object _obj;

    public SelectEvent(Object obj)
    {
        super(SELECT_ACTION);
        _obj = obj;
    }


    public Object getSelected()
    {
        return _obj;
    }
}

