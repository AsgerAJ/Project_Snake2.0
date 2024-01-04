
//javafx import

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.util.*;

public class Model extends Application {

    private Pane root;
    private Rectangle food;
    private Snake snake;
    private Direction direction;
    private Rectangle back;

    public double scalingConstant;
    public double positioncorrection;
    public double foodX;
    public double foodY;

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

    public void removeBox(Rectangle box) {
        root.getChildren().remove(box);
    }

    public void newFood(int x, int y) {
        Random rand = new Random();
        int randX = rand.nextInt(x);
        int randY = rand.nextInt(y);
        food = new Rectangle((randX / 2) * scalingConstant, ((randY / 2)) * scalingConstant, scalingConstant,
                scalingConstant);
        food.setFill(Color.RED);
        foodX = food.getX();
        foodY = food.getY();
        food.setArcHeight(scalingConstant);
        food.setArcWidth(scalingConstant);
        root.getChildren().add(food);
    }

    public void newSnake(int x, int y) {
        snake = new Snake(x, y, scalingConstant, 2, Direction.Up, 1);
        root.getChildren().add(snake);
        snake.eat(food);
        newFood(x, y);
    }

    public void moveDelay(int x, int y) {
        Platform.runLater(() -> {
            snake.update(snake.getCurrDir());
            if(collision()){
                snake.eat(food);
                newFood(x, y);
            }else if(snake.selfCollide()){
                root.getChildren().clear();
                drawGrid(x, y);
            }
        });
    }

    private boolean collision() {
        return (foodX == snake.getX() && foodY == snake.getY());
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Scanner console = new Scanner(System.in);

        // System.out.println("Please enter the dimensions of the snake game: (n x m)");
        // System.out.print("n = ");
        int n = 19; // console.nextInt();
        // System.out.println();
        // System.out.print("m = ");
        int m = 19; // console.nextInt();
        int radius = 10;
        int step = 10;

        root = new Pane();
        root.setPrefSize(n, m);
        direction = Direction.Left;

        drawGrid(n, m);
        newFood(n, m);
        newSnake(n, m);

        /*
         * Debug variables
         */
        // System.out.println(positioncorrection);
        // System.out.println(scalingConstant);

        /*
         * Movement thread
         */
        Runnable game = () -> {

            try {
                while (true) {
                    moveDelay(n, m);
                    Thread.sleep(50);
                }
            } catch (InterruptedException ie) {
            }
        };

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
                    break;
                default:
                    break;
            }
        });

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Thread gameThread = new Thread(game);
        gameThread.setDaemon(true);
        gameThread.start();
    }
}