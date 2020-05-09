package app.ichingoracle.core;

import java.util.ArrayList;
import java.util.List;


public final class TextFactory
{
    public static void setText(Text text)
    {
        _text = text;
        _subscribers.forEach(s -> s.onTextChanged(text));
    }

    public static Text getText() { return _text; }

    public static void register(TextFactoryCallback subscriber)
    {
        _subscribers.add(subscriber);
    }

    private static Text _text = new Text();
    private static List<TextFactoryCallback> _subscribers = new ArrayList<>();
}
