
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
    private Rectangle snake;
    private Direction direction;
    private Line gridLine;
    private Rectangle bg;

    public static int step = 10;
    public static int n = 0;
    public static int m = 0;
    public static Scene scene;

    public void main(String[] args) {
        launch(args);
    }

    public void drawGrid(int x, int y) {
        for (int i = 0; i < x / 10; i++) {
            for (int j = 0; j < y; j++) {
                Rectangle back = new Rectangle(i * 10, j * 10, 10, 10);
                if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))) {
                    back.setFill(Color.LIGHTGREEN);
                } else {
                    back.setFill(Color.GREEN);
                }
                root.getChildren().add(back);
            }
        }
    }

    public void newFood(int x, int y) {
        Circle food = new Circle(x - 5, y - 5, 5);
        food.setFill(Color.RED);
        root.getChildren().add(food);
    }

    public void newSnake(int x, int y, int length) {
        x = x / 2;
        y = y / 2;
        ArrayList<Rectangle> snake = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            snake.add(new Rectangle(x, y + (i * 10), 10, 10));
            snake.get(i).setFill(Color.BLACK);
            root.getChildren().add(snake.get(i));
        }
    }

    public void updateSnake(Direction direction) {
        switch (direction) {
            case Up:
                if (snake.getY() - step < 0) {
                    snake.setY(m - 10);
                } else {
                    snake.setY(snake.getY() - step);
                }

                break;

            case Left:
                if (snake.getX() - step < 0) {
                    snake.setX(n - 10);
                } else {
                    snake.setX(snake.getX() - step);
                }
                break;

            case Right:
                if (snake.getX() + step > n - 10) {
                    snake.setX(0);
                } else {
                    snake.setX(snake.getX() + step);
                }
                break;

            case Down:
                if (snake.getY() + step > m - 10) {
                    snake.setY(0);
                } else {
                    snake.setY(snake.getY() + step);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner console = new Scanner(System.in);

        System.out.println("Please enter the dimensions of the snake game: (n x m)");
        System.out.print("n = ");
        int n = 500;// console.nextInt()*10;
        System.out.println();
        System.out.print("m = ");
        int m = 500;// console.nextInt()*10;
        int radius = 10;

        root = new Pane();
        root.setPrefSize(n, m);
        Random rand = new Random();
        direction = Direction.Left;

        drawGrid(n, m);
        newFood(20, 20);
        newSnake(n, m, 1);

        Runnable r = () -> {
            try {
                for (;;) {
                    updateSnake(direction);
                    Thread.sleep(100);
                }
            } catch (InterruptedException ie) {
            }
        };



        Scene scene = new Scene(root, 500, 500);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyCode input = event.getCode();
            switch (input) {
                case W:
                    direction = Direction.Up;
                    break;
                case A:
                    direction = Direction.Left;
                    break;
                case S:
                    direction = Direction.Down;
                    break;
                case D:
                    direction = Direction.Right;
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