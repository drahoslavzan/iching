package app.ichingoracle.plugin.plumblossom.masters.controller;

import app.ichingoracle.core.*;
import app.ichingoracle.core.util.Dialog;
import app.ichingoracle.plugin.plumblossom.masters.Plugin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class Method implements Initializable
{
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        try
        {
            URL method = getClass().getResource("/masters/description.html");

            _browser.loadUrl(method);

            FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/masters/fxml/Result.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("masters/plugin", new Locale("en"));
            loader.setResources(bundle);

            _node = loader.load();
            _result = loader.getController();

            fillPreview();
        }
        catch(Exception e)
        {
            Dialog.showException(e);
        }
    }

    public void register(Plugin subscriber)
    {
        _subscriber = subscriber;
    }

    private void computeHexagramAndLoadResult()
    {
        Trigram upper = _trigramUpper.getTrigram();
        Trigram lower = _trigramLower.getTrigram();
        int top = upper.getLateHeavenValue();
        int bottom = lower.getLateHeavenValue();
        int line = modulateNumber(top + bottom, Hexagram.LINES);

        int hexId = Hexagram.getHexagramIdFromTrigrams(upper.getName(), lower.getName());

        Hexagram hex = new Hexagram(hexId);
        hex.changeLine(line);

        _result.loadResult(hex, line);
        _subscriber.onResult(_node);
    }

    private VBox createDroppableTrigram(TrigramRegion trig)
    {
        VBox box = new VBox();
        box.getChildren().add(trig);
        box.getStyleClass().add(TRIGRAM_SUNKEN_CLASS);

        box.setOnDragOver(e ->
        {
            if (e.getGestureSource() != box &&
                e.getDragboard().hasString())
            {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            e.consume();
        });

        box.setOnDragEntered(e ->
        {
             if (e.getGestureSource() != box &&
                 e.getDragboard().hasString())
             {
                 //box.setFill(Color.GREEN);
             }

             e.consume();
        });

        box.setOnDragExited(e ->
        {
            //box.setFill(Color.BLACK);

            e.consume();
        });

        box.setOnDragDropped(e ->
        {
            Dragboard db = e.getDragboard();

            boolean success = false;

            if (db.hasString())
            {
                try
                {
                    Trigram.Name id = Trigram.Name.valueOf(db.getString());

                    trig.setTrigram(new Trigram(id));

                    Trigram upper = _trigramUpper.getTrigram();
                    Trigram lower = _trigramLower.getTrigram();

                    if(upper != null && lower != null)
                    {
                        computeHexagramAndLoadResult();
                    }

                    success = true;
                }
                catch(Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }

            e.setDropCompleted(success);
            e.consume();
         });

        return box;
    }

    private void fillPreview()
    {
        VBox hexGroup = new VBox();
        hexGroup.setAlignment(Pos.CENTER);
        hexGroup.setPadding(new Insets(25, 10, 10, 10));

        VBox hexBox = new VBox(20);
        hexBox.getStyleClass().add(HEXAGRAM_CLASS);

        VBox trigamBoxUpper = createDroppableTrigram(_trigramUpper);
        VBox trigamBoxLower = createDroppableTrigram(_trigramLower);

        hexBox.getChildren().add(trigamBoxUpper);
        hexBox.getChildren().add(trigamBoxLower);

        hexGroup.getChildren().add(hexBox);

        _preview.add(hexGroup, 0, 0, 1, 2);

        ColumnConstraints hexCs = new ColumnConstraints();
        hexCs.setPercentWidth(20);
        _preview.getColumnConstraints().add(hexCs);

        ColumnConstraints cs = new ColumnConstraints();
        cs.setPercentWidth(80 / 5);
        _preview.getColumnConstraints().add(cs);
        _preview.getColumnConstraints().add(cs);
        _preview.getColumnConstraints().add(cs);
        _preview.getColumnConstraints().add(cs);
        _preview.getColumnConstraints().add(cs);

        _preview.add(new HBox(), 1, 0, 1, 2);

        for(int i = 1, j = 1; i <= Trigram.COUNT; ++i, ++j)
        {
            if(i == 5) j = 6;

            VBox trigBox = new VBox();
            Trigram trig = new Trigram(Trigram.getNameFromLaterHeavenValue(j));
            trigBox.getChildren().add(new TrigramRegion(trig));
            trigBox.getStyleClass().add(TRIGRAM_RAISED_CLASS);

            trigBox.setOnDragDetected(e ->
            {
                Dragboard db = trigBox.startDragAndDrop(TransferMode.ANY);
                SnapshotParameters params = new SnapshotParameters();
                params.setTransform(params.getTransform().scale(0.75, 0.75));
                WritableImage snapshot = trigBox.snapshot(params, null);

                ClipboardContent content = new ClipboardContent();
                content.putString(trig.getName().toString());
                db.setContent(content);

                double w = snapshot.getWidth() / 2.0;
                double h = snapshot.getHeight() / 2.0;
                db.setDragView(snapshot, w, h);

                trigamBoxUpper.getStyleClass().remove(TRIGRAM_SUNKEN_CLASS);
                trigamBoxUpper.getStyleClass().add(TRIGRAM_SUNKEN_HL_CLASS);

                trigamBoxLower.getStyleClass().remove(TRIGRAM_SUNKEN_CLASS);
                trigamBoxLower.getStyleClass().add(TRIGRAM_SUNKEN_HL_CLASS);

                e.consume();
            });

            trigBox.setOnDragDone(e ->
            {
                trigamBoxUpper.getStyleClass().remove(TRIGRAM_SUNKEN_HL_CLASS);
                trigamBoxUpper.getStyleClass().add(TRIGRAM_SUNKEN_CLASS);

                trigamBoxLower.getStyleClass().remove(TRIGRAM_SUNKEN_HL_CLASS);
                trigamBoxLower.getStyleClass().add(TRIGRAM_SUNKEN_CLASS);

                e.consume();
            });

            Label label = new Label(String.format("%d", j));
            label.setAlignment(Pos.CENTER);
            label.setFont(new Font("Arial", 32));

            VBox group = new VBox();
            group.setAlignment(Pos.CENTER);
            group.getChildren().add(label);
            group.getChildren().add(trigBox);

            group.setOnMouseClicked(e ->
            {
            });

            _preview.add(group, (i - 1) % (Trigram.COUNT / 2) + 2, i > Trigram.COUNT / 2 ? 1 : 0);
        }
    }

    private int modulateNumber(int num, int mod) { return ((num - 1) % mod) + 1; }

    private static final String TRIGRAM_RAISED_CLASS = "trigram-raised";
    private static final String TRIGRAM_SUNKEN_CLASS = "trigram-sunken";
    private static final String TRIGRAM_SUNKEN_HL_CLASS = "trigram-sunken-highlighted";
    private static final String HEXAGRAM_CLASS = "hexagram-sunken";

    private TextFactory _textFactory = new TextFactory();
    private TrigramRegion _trigramLower = new TrigramRegion();
    private TrigramRegion _trigramUpper = new TrigramRegion();
    private Plugin _subscriber;
    private Node _node;
    private Result _result;

    @FXML private Browser _browser;
    @FXML private GridPane _preview;
}

