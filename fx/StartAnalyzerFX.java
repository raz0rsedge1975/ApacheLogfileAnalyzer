package fx;

import cons.DateiVerbindung;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartAnalyzerFX extends Application {

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws IOException {
        window.setTitle("HTTP(S) LogFile Analyzer");
        final FlowPane root = new FlowPane(Orientation.HORIZONTAL);
        try {
            File imageFile = new File(DateiVerbindung.liesFileVomJARpfad("solar.png").getPath()); //<---
            Image iconImage = new Image(imageFile.toURI().toString());
            window.getIcons().add(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.isAlwaysOnTop();
        //Earlier Alignment is ignored
        window.setOpacity(0.95);
        window.setResizable(false);
        DropShadow shadow = new DropShadow();
        Text spacer0 = new Text(" ");
        Text tmpText = new Text("Click \"Open LogFile\" Button to open Log from File..."+'\n');
        Font font1 = Font.font("Verdana", FontWeight.LIGHT, 14);
        tmpText.setFont(font1);
        TextFlow textFlow = new TextFlow(tmpText);
        textFlow.setPrefSize(770.,550.);
        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefSize(790.,550.);
        scrollPane.isMouseTransparent();

        Button button = new Button("Open LogFile");
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-font-weight: bold;-fx-font-size: 14;-fx-border-radius: 10px");
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.isFocused();
                button.setEffect(shadow);
            }
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        button.disarm();
                        button.setEffect(null);
                    }
        });
        button.setOnAction(new ButtonHandler(textFlow, window) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
        });

        Text spacer1 = new Text("\n");
        Text spacer2 = new Text("\n");

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(spacer0, scrollPane, button, spacer1, spacer2);
        root.setStyle("-fx-border-color: darkgrey;-fx-border-size: 3px");
        textFlow.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setBorder(new Border(new BorderStroke(Color.GRAY,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        textFlow.setTextAlignment(TextAlignment.LEFT);
        textFlow.setOnScroll(Event::consume);
        root.setPadding(new Insets(10.));
        root.setPadding(Insets.EMPTY);
        window.setScene(new Scene(root, 800, 600));
        window.sizeToScene();
        window.isAlwaysOnTop();
        window.show();
    }
}
