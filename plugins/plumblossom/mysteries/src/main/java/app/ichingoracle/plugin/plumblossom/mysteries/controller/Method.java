package app.ichingoracle.plugin.plumblossom.mysteries.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import app.ichingoracle.core.util.Dialog;
import app.ichingoracle.core.Hexagram;
import app.ichingoracle.core.Trigram;
import app.ichingoracle.plugin.plumblossom.mysteries.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mysteries/fxml/Result.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("mysteries/plugin", new Locale("en"));
            loader.setResources(bundle);

            _node = loader.load();
            _result = loader.getController();

            URL method = getClass().getResource("/mysteries/description.html");
            _browser.loadUrl(method);

            Image btnIcon = new Image(getClass().getResourceAsStream("/image/method.png"));
            ImageView btnIconView = new ImageView(btnIcon);
            btnIconView.setFitWidth(18);
            btnIconView.setFitHeight(18);
            _compute.setGraphic(btnIconView);
            _compute.setTooltip(new Tooltip(bundle.getString("compute")));

            _date.setDateTimeFormatter(DateTimeFormatter.ofPattern(DATE_FORMAT));
            _date.setLocalDateTime(LocalDateTime.now());
        }
        catch(Exception e)
        {
            Dialog.showException(e);
        }
    }

    public void onClick()
    {
        if (_subscriber != null)
        {
            if(computeHexagramAndLoadResult())
                _subscriber.onResult(_node);
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private boolean computeHexagramAndLoadResult()
    {
        LocalDateTime date = _date.getLocalDateTime();

        int dateSum = date.getYear() + date.getMonthValue() + date.getDayOfMonth();
        int top = modulateNumber(dateSum, Trigram.COUNT);
        int bottom = modulateNumber(top + date.getHour(), Trigram.COUNT);
        int line = modulateNumber(top + date.getHour(), Hexagram.LINES);

        int hexIndex = Hexagram.getHexagramIdFromTrigrams(Trigram.getNameFromEarlyHeavenValue(top),
                Trigram.getNameFromEarlyHeavenValue(bottom));

        Hexagram hex = new Hexagram(hexIndex);
        hex.changeLine(line);

        String dateStr = _date.getLocalDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        _result.loadResult(hex, dateStr, line);

        return true;
    }

    private int modulateNumber(int num, int mod) { return ((num - 1) % mod) + 1; }

    private final String DATE_FORMAT = "dd MMMM yyyy    hh a";

    private Plugin _subscriber;
    private Node _node;
    private Result _result;

    @FXML private Button _compute;
    @FXML private Browser _browser;
    @FXML private LocalDateTimeTextField _date;
}

