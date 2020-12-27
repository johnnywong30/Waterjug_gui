import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import java.util.*;

public class Waterjug extends Application {
    private int width;
    private int height;
    private double lineWidth;
    private int rectX;  // top left of rectangle x coord
    private int rectY;  // top left of rectangle y coord
    private int rectMaxX;
    private int rectMaxY;
    private int capA;
    private int capB; 
    private int capC;
    private int goalA;
    private int goalB;
    private int goalC;
    private StackPane root;

    public void drawTitle(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setStroke(Color.BLUE);
        gc.setFont(new Font("Consolas", 64));
        gc.strokeText("Waterjug Puzzle Solver", width / 5, 65);
    }


    public void drawJugs(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(rectX, rectY, rectMaxX, rectMaxY);   
        gc.strokeRect(rectX + 150, rectY, rectMaxX, rectMaxY);   
        gc.strokeRect(rectX + 300, rectY, rectMaxX, rectMaxY);   
    }

    // jug is 0, 1, or 2
    // amount is water to fill
    // cap is cap of the jug
    public void fillJug(Canvas canvas, int jug, int amount, int cap) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.CYAN);
        // x and y are bottom left coordinates of the jug
        int x = rectX + jug * 150;
        int y = rectY + rectMaxY; 
        int fill = amount * rectMaxY / cap;
        gc.fillRect(x, y - fill, rectMaxX, fill);        
    }

    public Canvas drawHome() {
        Canvas canvas = new Canvas(width, height);
        drawTitle(canvas);
        drawJugs(canvas);
        fillJug(canvas, 1, 4, 8);   // test
        return canvas;
    }

    // translate away from the jug rectangles
    public TextField makeField(int translateX, int translateY) {
        TextField field = new TextField();
        field.setMaxWidth(100);
        field.setTranslateX(rectMaxX * -1.66 + translateX);
        field.setTranslateY(translateY);
        return field;
    }

    // jug is 0, 1, 2
    public Label makeLabel(int translateX, int translateY, int jug, String purpose) {
        String[] jugs = {"A", "B", "C"};
        String tag = purpose + jugs[jug];
        Label label = new Label(tag);
        label.setTranslateX(rectMaxX * -1.66 + translateX);
        label.setTranslateY(translateY - 25);
        return label;
    }

    public TextField[] getFields() {
        TextField[] fields = new TextField[6];
        for (int i = 0; i < 3; ++i) {
            int x = i * (rectMaxX + 50);
            Label capacity = makeLabel(x, -50, i, "Capacity for Jug ");
            fields[i] = makeField(x, -50);  // add default capacity
            Label goal = makeLabel(x, 250, i, "Goal for Jug ");
            fields[i + 3] = makeField(x, 250);  // add default goal
            // add event handlers for these
            root.getChildren().addAll(capacity, fields[i], goal, fields[i + 3]);
        }
        return fields;
    } 

    @Override
    public void start(Stage stage) {
        // set attributes
        width = 1280;
        height = 720;
        lineWidth = 1.0;
        rectX = width / 3;
        rectY = height / 2;
        rectMaxX = 100;
        rectMaxY = 200;
        root = new StackPane();
        Canvas canvas = drawHome();
        // TextField field = makeField(0, 0);
        // field.setText("Icoboob got big brain");
        root.getChildren().add(canvas);
        getFields();
        // for (TextField field: getFields()) {
        //     root.getChildren().add(field);
        // }
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Waterjug Puzzle");
        stage.show();
    }

    

    public static void main(String[] args) {
        launch();
    }

}