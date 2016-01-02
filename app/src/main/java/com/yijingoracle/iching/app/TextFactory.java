package com.yijingoracle.iching.app;

import java.io.InputStream;
import com.yijingoracle.iching.core.*;


public final class TextFactory
{
    public TextFactory()
    {
        if(!_initialized)
        {
            InputStream xslHexagram = getClass().getResourceAsStream("/text/hexagram.xsl");
            InputStream xslTrigram = getClass().getResourceAsStream("/text/trigram.xsl");
            InputStream hex = getClass().getResourceAsStream("/text/hexagrams.xml");
            InputStream tri = getClass().getResourceAsStream("/text/trigrams.xml");

            _text.renderHexagrams(hex, xslHexagram);
            _text.renderTrigrams(tri, xslTrigram);

            _initialized = true;
        }
    }

    public Text getText()
    {
        return _text;
    }

    private static Text _text = new Text();
    private static boolean _initialized;
}
