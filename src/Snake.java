import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

import java.awt.Color;
import java.util.*;

public class Snake extends Rectangle {

    private ArrayList<Rectangle> bodyParts;
    private Direction currentDirection;
    private Rectangle head;
    private double cS;
    private int length = 2;
    private int xLim;
    private int yLim;
    private int score;

    public Snake(int x, int y, double cS, int length, Direction currentDirection) {
        super((x / 2) * cS - cS, ((y / 2)) * cS - cS, cS, cS);
        this.cS = cS;
        this.xLim = x;
        this.yLim = y;
        this.bodyParts = new ArrayList<>();
        this.currentDirection = Direction.Up;
        this.score = 0;
    }

    public void eat(Rectangle food) {
        System.out.println("Nom nom nom");
        Rectangle body = lastBody();
        food.setX(getX());
        food.setY(getY());
        bodyParts.add(getLen(),food);
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

    public int getLen(){
        return this.bodyParts.size();
    }

    public void update(Direction direction) {
        for (int i = getLen() - 1; i >= 0; i--) {
            if (i == 0) {
                bodyParts.get(i).setX(getX());
                bodyParts.get(i).setY(getY());
            } else {
                bodyParts.get(i).setX(bodyParts.get(i - 1).getX());
                bodyParts.get(i).setX(bodyParts.get(i - 1).getY());
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

    public void moveDelay(){
        Platform.runLater(()->{
            update(currentDirection);
        });
    }

    private Rectangle lastBody(){
        if(getLen()==0){
            return this;
        }else{
            return bodyParts.get(getLen());
        }
    }



}
