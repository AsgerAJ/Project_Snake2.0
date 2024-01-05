import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GameRunner extends Application {

    // Public variables
    public double scalingConstant;
    public double height;
    public double width;
    public int n;
    public int m;

    // Private variables
    private Pane root;
    private Food food;
    private Snake snake;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Welcome to the snake game");
        Scanner console = new Scanner(System.in);

        boolean nSet = false;
        while (!nSet) {
            if (n > 5 && n < 100) {
                nSet = true;
                break;
            } else {
                System.out.print("Please enter the size of the n axis (greater than 5 & lesser than 100): ");
            }
            String gridsizeString = console.next();
            try {
                n = Integer.valueOf(gridsizeString) * 1;
            } catch (NumberFormatException e) {
                n = 1;
            }

        }
        boolean mSet = false;
        while (!mSet) {
            if (m > 5 && m < 100) {
                nSet = true;
                break;
            } else {
                System.out.print("Please enter the size of the m axis (greater than 5 & lesser than 100): ");
            }
            String gridsizeString = console.next();
            try {
                m = Integer.valueOf(gridsizeString) * 1;
            } catch (NumberFormatException e) {
                m = 1;
            }

        }

        if (n > m) {
            scalingConstant = 500 / (n);
        } else {
            scalingConstant = 500 / (m);
        }

        root = new Pane();
        root.setPrefSize(n, m);
        Random foodCord = new Random();

        // Skal skrives ting til random koordinater
        food = new Food(2, 2, scalingConstant);
        drawFood(food);
        snake = new Snake(n, m, scalingConstant, Direction.Stop, 0, 2);
        drawSnake(snake);

        width = scalingConstant * n;
        height = scalingConstant * m;
        Scene scene = new Scene(root, width, height);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            KeyCode last = code;
            KeyCode last2 = last;
            switch (code) {
                case UP:
                    if (snake.getDirr() != Direction.Down && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Up);
                        stepHandler(snake);
                        Collections.rotate(snake, 1);
                    }
                    break;
                case DOWN:
                    if (snake.getDirr() != Direction.Up && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Down);
                        stepHandler(snake);
                        Collections.rotate(snake, 1);
                    }
                    break;
                case LEFT:
                    if (snake.getDirr() != Direction.Right && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Left);
                        stepHandler(snake);
                        Collections.rotate(snake, 1);
                    }
                    break;
                case RIGHT:
                    if (snake.getDirr() != Direction.Left && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Right);
                        stepHandler(snake);
                        Collections.rotate(snake, 1);
                    }
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

    public void drawSnake(Snake snake) {
        root.getChildren().addAll(snake);
    }

    public void drawFood(Food food) {
        root.getChildren().add(food);
    }

    public void stepHandler(Snake snake) {
        Random rand = new Random();
        if (snake.selfCollide()) {
            snake.setAlive(false);
            snake.setCurrentDirection(Direction.Stop);
            System.out.println("You are now dead your score was: " + snake.getScore());
        }
        if (snake.foodCollision(food)) {
            boolean validSpawn = false;
            int randX = 0;
            int randY = 0;
            while (!validSpawn) {
                randX = rand.nextInt(n) + 1;
                randY = rand.nextInt(m) + 1;
                for (int i = 0; i < snake.getLength(); i++) {
                    if (snake.get(i).getX() / scalingConstant != randX
                            && snake.get(i).getY() / scalingConstant != randY) {
                        validSpawn = true;
                    }
                }
            }
            food.setXY(randX, randY);
            eat();
        }
        snake.moveSnake(snake.getDirr());
    }

    public void eat() {
        snake.Grow();
        root.getChildren().add(snake.get(snake.getLength() - 1));
    }
}