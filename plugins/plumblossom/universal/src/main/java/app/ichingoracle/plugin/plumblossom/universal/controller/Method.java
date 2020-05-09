package app.ichingoracle.plugin.plumblossom.universal.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import app.ichingoracle.core.util.Dialog;
import app.ichingoracle.core.Hexagram;
import app.ichingoracle.core.Trigram;
import app.ichingoracle.plugin.plumblossom.universal.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jfxtras.scene.control.LocalDateTimeTextField;
import app.ichingoracle.core.Browser;


public class Method implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/universal/fxml/Result.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("universal/plugin", new Locale("en"));
            loader.setResources(bundle);

            _query.setTooltip(new Tooltip(bundle.getString("compute")));

            _date.setDateTimeFormatter(DateTimeFormatter.ofPattern(DATE_FORMAT));
            _date.setLocalDateTime(LocalDateTime.now());

            URL method = getClass().getResource("/universal/description.html");

            _browser.loadUrl(method);

            _node = loader.load();
            _result = loader.getController();
        }
        catch(Exception e)
        {
            Dialog.showException(e);
        }
    }

    public void onKeyPressed(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String text = _query.getText();

            if (_subscriber != null)
            {
                if(computeHexagramAndLoadResult(text))
                    _subscriber.onResult(_node);
            }

            event.consume();
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private String filterText(String text)
    {
        return text.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("  +", " ");
    }

    private int getLengthWithoutWhitespace(String text)
    {
        return text.replaceAll(" ", "").length();
    }

    private boolean computeHexagramAndLoadResult(String text)
    {
        String query = filterText(text);
        int queryLength = getLengthWithoutWhitespace(query);

        if (queryLength < 1)
            return false;

        LocalDateTime date = _date.getLocalDateTime();

        int dateSum = date.getYear() + date.getMonthValue() + date.getDayOfMonth();
        int top = modulateNumber(queryLength, Trigram.COUNT);
        int bottom = modulateNumber(dateSum, Trigram.COUNT);
        int line = modulateNumber(dateSum + date.getHour(), Hexagram.LINES);

        int hexIndex = Hexagram.getHexagramIdFromTrigrams(Trigram.getNameFromEarlyHeavenValue(top),
            Trigram.getNameFromEarlyHeavenValue(bottom));

        Hexagram hex = new Hexagram(hexIndex);
        hex.changeLine(line);

        String dateStr = _date.getLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        _result.loadResult(hex, query, queryLength, dateStr, line);

        return true;
    }

    private int modulateNumber(int num, int mod) { return ((num - 1) % mod) + 1; }

    private final String DATE_FORMAT = "dd MMMM yyyy    hh a";

    private Plugin _subscriber;
    private Node _node;
    private Result _result;

    @FXML private Browser _browser;
    @FXML private TextField _query;
    @FXML private LocalDateTimeTextField _date;
}

