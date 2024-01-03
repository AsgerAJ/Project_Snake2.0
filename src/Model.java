
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
    private Rectangle back;
    public double scalingConstant;
    public double positioncorrection;

    public static void main(String[] args) {
        launch(args);
    }

    public void drawGrid(int x, int y) {
        if(x>y){
            scalingConstant = 500/(x/10);
        }else{
            scalingConstant = 500/(y/10);
        }

        for (int i = 0; i < x / 10; i++) {
            for (int j = 0; j < y / 10; j++) {
                Rectangle back = new Rectangle(i*scalingConstant, j*scalingConstant, scalingConstant, scalingConstant);
                if (((i % 2 == 0) && (j % 2 == 0)) || ((i % 2 != 0) && (j % 2 != 0))) {
                    back.setFill(Color.LIGHTGREEN);
                } else {
                    back.setFill(Color.GREEN);
                }
                root.getChildren().add(back);
            }
        }
    }

    public void newFood(double x, double y) {
        System.out.println(x + ", " + y);
        x = x * 10-(scalingConstant/10);
        y = y * 10-(scalingConstant/10);
        
        positioncorrection = (scalingConstant/(scalingConstant/(scalingConstant/10)));
        Circle food = new Circle(x*positioncorrection, y*positioncorrection, scalingConstant/2);
        food.setFill(Color.RED);
        root.getChildren().add(food);
    }

    public void newSnake(int x, int y) {
        x = x / 2;
        y = y / 2;
        snake = new Rectangle(x, y, 10, 10);
        snake.setFill(Color.BLACK);
        root.getChildren().add(snake);
        // ArrayList<Rectangle> snake = new ArrayList<>();
        // snake.add(new Rectangle(x, y, 10, 10));
        // snake.add(new Rectangle(x, y + 10, 10, 10));
        // for (int i = 0; i < 2; i++) {
        // snake.get(i).setFill(Color.BLACK);
        // root.getChildren().add(snake.get(i));
        // }
    }

    public void updateSnake() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner console = new Scanner(System.in);

        //System.out.println("Please enter the dimensions of the snake game: (n x m)");
        //System.out.print("n = ");
        int n = 9 * 10; // console.nextInt() * 10;
        //System.out.println();
        //System.out.print("m = ");
        int m = 9 * 10; //console.nextInt() * 10;
        int radius = 10;
        int step = 10;

        root = new Pane();
        root.setPrefSize(n, m);
        Random rand = new Random();
        direction = Direction.Left;

        drawGrid(n, m);
        System.out.println(scalingConstant);
        newFood(rand.nextInt(n/10)+1, rand.nextInt(m/10)+1);
        System.out.println(positioncorrection);
        // newSnake(n, m);
        Scene scene = new Scene(root, 500, 500);

        // scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
        // KeyCode code = event.getCode();
        // switch (code) {
        // case UP:
        // if (snake.getY() - step < 0) {
        // snake.setY(m-10);
        // } else {
        // snake.setY(snake.getY() - step);
        // }

        // break;

        // case LEFT:
        // if (snake.getX() - step < 0) {
        // snake.setX(n-10);
        // } else {
        // snake.setX(snake.getX() - step);
        // }
        // break;

        // case RIGHT:
        // if (snake.getX() + step > n-10) {
        // snake.setX(0);
        // } else {
        // snake.setX(snake.getX() + step);
        // }
        // break;

        // case DOWN:
        // if (snake.getY() + step > m-10) {
        // snake.setY(0);
        // } else {
        // snake.setY(snake.getY() + step);
        // }
        // break;

        // default:
        // break;
        // }
        // });

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}