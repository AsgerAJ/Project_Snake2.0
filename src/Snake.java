import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.util.*;

public class Snake extends Rectangle {

    private ArrayList<Rectangle> bodyParts;
    private Direction currentDirection;
    private Rectangle head;
    private double cS;
    private int length = 0;
    private int xLim;
    private int yLim;
    private int score;
    private int playerNumber;

    public Snake(int x, int y, double cS, int length, Direction currentDirection, int playerNumber) {
        super((x / 2) * cS - cS, ((y / 2)) * cS - cS, cS, cS);
        this.cS = cS;
        this.xLim = x;
        this.yLim = y;
        //.println(xLim * cS + " ; " + yLim * cS);
        this.bodyParts = new ArrayList<>();
        this.currentDirection = Direction.Up;
        this.score = 1;
        this.playerNumber = playerNumber;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrDir() {
        return currentDirection;
    }

    public int getXLim() {
        return this.xLim;
    }

    public int getYLim() {
        return this.yLim;
    }

    public int getLen() {
        return this.bodyParts.size();
    }

    public void setScore(){
        this.score = score+1;
    }

    public boolean selfCollide(){
        boolean dead = false;
        for (int i = 0; i < length; i++){
            if((bodyParts.get(i).getX()==getX()) && (bodyParts.get(i).getY()==getY()) ){
                return dead = true;
            }
        }
        return dead;
    }

    public void update(Direction direction) {
        for (int i = length - 1; i >= 0; i--) {
            if (i == 0) {
                bodyParts.get(i).setX(getX());
                bodyParts.get(i).setY(getY());
            } else {
                bodyParts.get(i).setX(bodyParts.get(i - 1).getX());
                bodyParts.get(i).setY(bodyParts.get(i - 1).getY());
            }
        }

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
                if (getX() + cS > getXLim() * cS - cS) {
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



    private Rectangle lastBody() {
        if (length == 0) {
            return this;
        } else {
            return bodyParts.get(length - 1);

        }
    }

    public void eat(Rectangle food) {
        System.out.println("Nom nom nom");
        Rectangle body = lastBody();
        food.setX(getX());
        food.setY(getY());
        food.setArcHeight(0);
        food.setArcWidth(0);
        food.setFill(Color.BLACK);
        bodyParts.add(length++, food);
    }
}
