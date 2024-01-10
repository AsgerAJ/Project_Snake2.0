import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        food = new Food(foodCord.nextInt(n) + 1, foodCord.nextInt(m) + 1, scalingConstant);
        drawFood(food);
        snake = new Snake(n, m, scalingConstant, Direction.Stop, 0, 2);
        drawSnake(snake);

        width = scalingConstant * n;
        height = scalingConstant * m;
        Scene scene = new Scene(root, width, height);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            snake.setTailCoords();
            switch (code) {
                case UP:
                    if (snake.getDirr() != Direction.Down && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Up);
                        stepHandler(snake);
                    }
                    break;
                case DOWN:
                    if (snake.getDirr() != Direction.Up && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Down);
                        stepHandler(snake);
                    }
                    break;
                case LEFT:
                    if (snake.getDirr() != Direction.Right && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Left);
                        stepHandler(snake);
                    }
                    break;
                case RIGHT:
                    if (snake.getDirr() != Direction.Left && snake.getAlive()) {
                        snake.setCurrentDirection(Direction.Right);
                        stepHandler(snake);
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
        console.close();
    }

    public void drawSnake(Snake snake) {
        root.getChildren().addAll(snake);
    }

    public void drawFood(Food food) {
        root.getChildren().add(food);
    }

    public void stepHandler(Snake snake) {
        Random rand = new Random();
        snake.moveSnake(snake.getDirr());
        Collections.rotate(snake, 1);
        if (snake.selfCollide()) {
            snake.setAlive(false);
            snake.setCurrentDirection(Direction.Stop);
            System.out.println("You are now dead your score was: " + snake.getScore());
        }else if (snake.foodCollision(food)) {
            boolean validSpawn = false;
            int randX = rand.nextInt(n);
            int randY = rand.nextInt(m);
            while (!validSpawn) {
                validSpawn = true;
                randX = rand.nextInt(n);
                randY = rand.nextInt(m);
                for (int i = 0; i < snake.getLength(); i++) {
                    if (snake.get(i).getX() / scalingConstant == randX
                    && snake.get(i).getY() / scalingConstant == randY) {
                        validSpawn = false;
                    }
                }
            }
            food.setXY(randX + 1, randY + 1);
            eat();
            root.getChildren().add(snake.get(snake.size()-1));
        }
    }

    public void eat() {
        snake.Grow();
    }
}