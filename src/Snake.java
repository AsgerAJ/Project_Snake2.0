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

public class Snake extends Rectangle {

    private ArrayList<Rectangle> body;
    private int snakeLength = 2;
    private Direction currentDirection;
    private Rectangle head;
    private double cS;
    private int xLim;
    private int yLim;

    public Snake(int x, int y, double cS, int snakeLength, Direction currentDirection) {
        super((x / 2) * cS - cS, ((y / 2)) * cS - cS, cS, cS);
        this.cS = cS;
        this.xLim = x;
        this.yLim = y;
        this.body = new ArrayList<>();
        this.currentDirection = Direction.Up;
    }

    public void eat() {
        System.out.println("Nom nom nom");
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrDir() {
        return this.currentDirection;
    }

    public int getXLim() {
        return this.xLim;
    }

    public int getYLim() {
        return this.yLim;
    }

    public void update(Direction direction) {
        switch (direction) {
            case Up:
                if (getY() - cS < 0) {
                    setY(getYLim() * cS - cS);
                } else {
                    setY(getY() - cS);
                }
                break;

            case Left:
                if (getX() - cS < 0) {
                    setX(getXLim() * cS - cS);
                } else {
                    setX(getX() - cS);
                }
                break;

            case Right:
                if (getX() + cS > getYLim() * cS - cS) {
                    setX(0);
                } else {
                    setX(getX() + cS);
                }
                break;

            case Down:
                if (getY() + cS > getYLim() * cS - cS) {
                    setY(0);
                } else {
                    setY(getY() + cS);
                }
                break;
            default:
                break;
        }
    }

}
