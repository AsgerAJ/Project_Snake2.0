import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.*;

public class Snake extends ArrayList<Rectangle> {

    private Direction direction;
    public int playerNumber = 0;
    private int x;
    private int y;
    private int score;
    private double sC;
    private boolean alive;
    private double tailX;
    private double tailY;

    public Snake(int x, int y, double sC, Direction direction, int score, int startLength) {
        this.x = x;
        this.y = y;
        this.sC = sC;
        this.score = score;
        for(int i = 0; i < startLength; i++){
            super.add(new Rectangle((x / 2+i) * sC, (y / 2) * sC, sC, sC));
        }
        this.direction = direction;
        //playerNumber++;
        // this.playerNumber = playerNumber;
        this.alive = true;
        setTailCoords();
    }

    public void moveSnake(Direction newDirection) {
        switch (newDirection) {

            case Up:
                if (get(0).getY() - getSC() < 0) {
                    get(size() - 1).setY(getYlim() * getSC() - getSC());
                    get(size() - 1).setX(get(0).getX());
                } else {
                    get(size() - 1).setY(get(0).getY() - getSC());
                    get(size() - 1).setX(get(0).getX());
                }
                break;

            case Down:
                if (get(0).getY() + getSC() > getYlim() * getSC()- sC) {
                    get(size() - 1).setY(0);
                    get(size() - 1).setX(get(0).getX());
                } else {
                    get(size() - 1).setY(get(0).getY() + getSC());
                    get(size() - 1).setX(get(0).getX());
                }
                break;

            case Left:
                if (get(0).getX() - getSC() < 0) {
                    get(size() - 1).setX(getXlim() * getSC() - getSC());
                    get(size() - 1).setY(get(0).getY());
                } else {
                    get(size() - 1).setX(get(0).getX() - getSC());
                    get(size() - 1).setY(get(0).getY());
                }
                break;

            case Right:
                if (get(0).getX() + getSC() > getXlim() * getSC()-sC) {
                    get(size() - 1).setX(0);
                    get(size() - 1).setY(get(0).getY());
                } else {
                    get(size() - 1).setX(get(0).getX() + getSC());
                    get(size() - 1).setY(get(0).getY());
                }

                break;

            case Stop:
                break;
            default:
                break;
        }
    }

    public void Grow(){
        super.add(new Rectangle(getTailCoordX(),getTailCoordY(),getSC(),getSC()));
        this.score++;
        System.out.println("Score: " + getScore());
    }

    public boolean foodCollision(Rectangle food) {
        if (food.getX()==get(0).getX() && food.getY() == get(0).getY()) {
            return true;
        }
        return false;
    }

    public boolean selfCollide(){
        boolean dead = false;
        for (int i = 1; i < getLength(); i++){
            if((get(i).getX() == get(0).getX()) && (get(i).getY() == get(0).getY()) ){
                return dead = true;
            }
        }
        return dead;
    }

    public boolean getAlive(){
        return this.alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public Direction getDirr() {
        return this.direction;
    }

    public int getScore() {
        return this.score;
    }

    public void setCurrentDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public int getXlim() {
        return this.x;
    }

    public int getYlim() {
        return this.y;
    }

    public double getSC() {
        return this.sC;
    }

    public int getLength() {
        return super.size();
    }

    public double getTailCoordX(){
        return this.tailX;
    }

    public double getTailCoordY(){
        return this.tailY;
    }

    public void setTailCoords(){
        this.tailX = super.get(super.size()-1).getX();
        this.tailY = super.get(super.size()-1).getY();
    }

}