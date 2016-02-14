package com.yijingoracle.iching.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.yijingoracle.iching.core.*;


public final class TextFactory
{
    public static void setText(Text text)
    {
        _text = text;
        _subscribers.forEach(s -> s.OnTextChanged(text));
    }

    public static Text getText() { return _text; }

    public static void Register(TextFactoryCallback subscriber)
    {
        _subscribers.add(subscriber);
    }

    private static Text _text = new Text();
    private static List<TextFactoryCallback> _subscribers = new ArrayList<>();
}
