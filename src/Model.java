
//javafx import

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    private Snake snake;
    private Direction direction;
    private Rectangle back;

    public double scalingConstant;
    public double positioncorrection;

    public static void main(String[] args) {
        launch(args);
    }

    public void drawGrid(int x, int y) { // Colours background
        if (x > y) {
            scalingConstant = 500 / (x);
        } else {
            scalingConstant = 500 / (y);
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Rectangle back = new Rectangle(i * scalingConstant, j * scalingConstant, scalingConstant,
                        scalingConstant);
                if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))) {
                    back.setFill(Color.YELLOWGREEN);
                } else {
                    back.setFill(Color.DARKSEAGREEN);
                }
                root.getChildren().add(back);
            }
        }
    }

    public void newFood(double x, double y) {
        System.out.println(x + ", " + y);
        Circle food = new Circle(x * scalingConstant - scalingConstant / 2, y * scalingConstant - scalingConstant / 2,
                scalingConstant / 2);
        food.setFill(Color.RED);
        root.getChildren().add(food);
    }

    public void newSnake(int x, int y) {
        System.out.println();
        snake = new Snake(x, y, scalingConstant, 2, Direction.Up);
        root.getChildren().add(snake);
        snake.eat();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner console = new Scanner(System.in);

        // System.out.println("Please enter the dimensions of the snake game: (n x m)");
        // System.out.print("n = ");
        int n = 50; // console.nextInt();
        // System.out.println();
        // System.out.print("m = ");
        int m = 50; // console.nextInt();
        int radius = 10;
        int step = 10;

        root = new Pane();
        root.setPrefSize(n, m);
        Random rand = new Random();
        direction = Direction.Left;

        drawGrid(n, m);
        System.out.println(scalingConstant);
        newFood(2, 2);
        System.out.println(positioncorrection);
        newSnake(n, m);

        Scene scene = new Scene(root, 500, 500);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case UP:
                    if (snake.getCurrDir() != Direction.Down) {
                        snake.setCurrentDirection(Direction.Up);

                    }
                    break;
                case DOWN:
                    if (snake.getCurrDir() != Direction.Up) {
                        snake.setCurrentDirection(Direction.Down);
                    }
                    break;
                case LEFT:
                    if (snake.getCurrDir() != Direction.Right) {
                        snake.setCurrentDirection(Direction.Left);
                    }
                    break;
                case RIGHT:
                    if (snake.getCurrDir() != Direction.Left) {
                        snake.setCurrentDirection(Direction.Right);
                    }
                    break;

                case SPACE:
                    snake.update(snake.getCurrDir());
                    System.out.println(snake.getCurrDir());
                        break;
                default:
                    break;
            }
        });

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}