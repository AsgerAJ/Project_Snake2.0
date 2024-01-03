
//javafx import

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.util.*;

public class Model extends Application {

    private Pane root;
    private Circle food;
    private Rectangle snake;
    private Direction direction;
    private Line gridLine;

    public static void main(String[] args) {
        launch(args);
    }

    public void drawGrid(int x, int y) {
        for (int i = 0; i < x / 10; i++) {
            Line line = new Line(i * 10, 0, i * 10, y);
            line.setFill(Color.BLACK);
            root.getChildren().add(line);

            for (int j = 0; j < y; j++) {
                line = new Line(0, j * 10, y, j*10);
                line.setFill(Color.BLACK);
                root.getChildren().add(line);
            }
        }
    }

    public void drawFood(int x, int y){
        Circle food = new Circle(x, y, 5);
        food.setFill(Color.RED);
        root.getChildren().add(food);
    }

    public void drawSnake(int x, int y){
        x = x/2;
        y = y/2;
        ArrayList<Rectangle> snake = new ArrayList<>();
        snake.add(new Rectangle(x, y, 10, 10));
        snake.add(new Rectangle(x, y+10, 10,10));
        for (int i = 0; i < 2; i++){
            snake.get(i).setFill(Color.BLACK);
            root.getChildren().add(snake.get(i));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner console = new Scanner(System.in);

        System.out.println("Please enter the dimensions of the snake game: (n x m)");
        System.out.print("n = ");
        int n = 50;// console.nextInt()*10;
        System.out.println();
        System.out.print("m = ");
        int m = 40;// console.nextInt()*10;
        int radius = 10;
        int step = 10;

        root = new Pane();
        root.setPrefSize(n, m);
        Random rand = new Random();
        direction = Direction.Left;

        drawGrid(n, m);
        drawFood(20, 20);
        drawSnake(n, m);
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}