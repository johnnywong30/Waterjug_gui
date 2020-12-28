import javafx.application.Application;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.util.*;
import javafx.scene.control.Alert.AlertType; 
import java.util.*;

public class Waterjug extends Application {
    private int width;
    private int height;
    private double lineWidth;
    private int rectX;  // top left of rectangle x coord
    private int rectY;  // top left of rectangle y coord
    private int rectMaxX;
    private int rectMaxY;
    private int[] caps;
    private int[] goals;
    private StackPane root;
    private Puzzle solver;
    private AnimationTimer loop;

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

    public void clearJugs(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 70, width, height - 70);
    }

    public Canvas drawHome() {
        Canvas canvas = new Canvas(width, height);
        drawTitle(canvas);
        drawJugs(canvas);
        fillJug(canvas, 1, 4, 8);   // test
        // clearJugs(canvas);
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

    public Label makeAuthor() {
        String tag = "By Johnny Wong";
        Label label = new Label(tag);
        label.setFont(new Font("Gill Sans", 16));
        label.setTranslateY(-250);
        return label;
    }

    public Label makeMoveLabel() {
        String tag = "";
        Label label = new Label(tag);
        label.setFont(new Font("Gill Sans", 32));
        label.setTranslateY(-150);
        return label;
    }

    public TextField[] getFields() {
        TextField[] fields = new TextField[6];
        int[] defaults = {3, 5, 8, 0, 4, 4};
        for (int i = 0; i < 3; ++i) {
            int x = i * (rectMaxX + 50);
            Label capacity = makeLabel(x, -50, i, "Capacity for Jug ");
            fields[i] = makeField(x, -50);  
            fields[i].setText(String.valueOf(defaults[i]));
            Label goal = makeLabel(x, 250, i, "Goal for Jug ");
            fields[i + 3] = makeField(x, 250);  
            fields[i + 3].setText(String.valueOf(defaults[i + 3]));
            // add event handlers for these
            root.getChildren().addAll(capacity, fields[i], goal, fields[i + 3]);
        }
        // shouldn't have to do this if submit event handler works...
        // caps[0] = defaults[0];
        // caps[1] = defaults[1];
        // caps[2] = defaults[2];
        // goals[0] = defaults[3];
        // goals[1] = defaults[4];
        // goals[2] = defaults[5];
        return fields;
    } 
    

    public void animate(Canvas canvas, Stack<State> stack, Label move) {
        startAnimation(canvas, stack, move);
    }

    private void startAnimation(Canvas canvas, Stack<State> stack, Label move) {
        loop = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1024_000_000) {
                    State top = stack.pop();
                    int[] jugs = top.getVals();
                    clearJugs(canvas);
                    drawJugs(canvas);
                    for (int i = 0; i < 3; ++i) {
                        fillJug(canvas, i, jugs[i], caps[i]);
                        move.setText(top.getDirections() + "\n" + top.to_String());
                    }
                    lastUpdate = now;
                }
                if (stack.isEmpty()) {
                    loop.stop();
                }
            }
        };
        loop.start();
    }

    // submit caps and goals
    // will send warnings if there are any errors in the fields...
    public Button getSubmitButton(Canvas canvas, TextField[] fields, Label move) {
        Button submit = new Button("Submit");
        submit.setTranslateX(2 * (rectMaxX + 50));
        submit.setTranslateY(-50);
        Tooltip t = new Tooltip("Click to submit capacities and goals for the jugs!");
        t.setShowDelay(Duration.seconds(0.25));
        Tooltip.install(submit, t);
        // event handler stuff
        EventHandler<ActionEvent> event = new EventHandler<>() {
            public void handle(ActionEvent e) {
                int maxCap = Integer.MIN_VALUE;
                for (int i = 0; i < 3; ++i) {
                    try {
                        caps[i] = Integer.parseInt(fields[i].getText());
                        if (i < 2 && caps[i] > maxCap) {
                            maxCap = caps[i];
                        }
                        else if (caps[i] < maxCap) {
                            System.out.println("here");
                            throw new Exception("Invalid capacity for jug C.");
                        }
                        goals[i] = Integer.parseInt(fields[i + 3].getText());
                        if (goals[i] > caps[i]) {
                            System.out.println("what");
                            throw new Exception("Invalid goal.");
                        }
                    } catch (Exception ex) {
                        Alert a = new Alert(AlertType.ERROR);
                        String text = "Please insert correct capacities and goals.\nCapacities must be valid integers.\nJug C must have the largest capacity.\nGoals must be valid integers and cannot be greater than their respective jug capacity.";
                        a.setContentText(text); 
                        a.show();
                        return;
                    }
                    
                }
                solver = new Puzzle(caps[0], caps[1], caps[2], goals[0], goals[1], goals[2]);
                // work on getting an array of the states so we can convert draw them onto the canvas
                solver.getSolution();
                animate(canvas, solver.getSolutionStack(), move);
                // System.out.println("\n");
            }
        };
        submit.setOnAction(event);
        return submit;
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
        caps = new int[3];
        goals = new int[3];
        root = new StackPane();
        Canvas canvas = drawHome();
        root.getChildren().add(canvas);
        TextField[] fields = getFields();
        Label move = makeMoveLabel();
        root.getChildren().addAll(makeAuthor(), move);
        root.getChildren().add(getSubmitButton(canvas, fields, move));
        
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle("Waterjug Puzzle");
        stage.show();
    }

    

    public static void main(String[] args) {
        launch();
    }

}