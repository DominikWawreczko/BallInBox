package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Runnable, Drawable, Stoppable  {

    Color color;
    volatile boolean running=true;
    private final int r;
    public volatile Point position, oldPosition;
    private volatile Speed speed;
    private final Box box;
    private final int screenSize = 512;
    /*
    WARNING program is not using RWD technics for balls border because the purpose of this program is to show Java back-end
    not how to implement interface
     */
    public Ball( Point position, Speed speed, Box box){
        this.r = 20;
        this.position = position;
        oldPosition = new Point(0,0);
        this.speed = speed;
        this.box=box;
        color =Color.AQUAMARINE;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void run() {

        while (running){
            movement();
        }
    }

    @Override
    public void stop(){

        running=false;
    }

    public void movement(){

        try {
            moveBall();

        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }

    public void moveBall() throws InterruptedException{
        actionWhenItIsEndOfScreen();
        changePosition();
        Thread.sleep(30);
        actionInBox();
    }

    private void changePosition(){
        oldPosition.setX(position.getX());
        oldPosition.setY(position.getY());
        position.setX(position.getX() + speed.getDx());
        position.setY(position.getY() + speed.getDy());

    }

    private void actionInBox(){
        if(isGoingToBox()){
            this.box.input();
        }

        if(isGoingOutsideBox()){
            this.box.output();
        }
    }

    @Override
    public void draw (GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(position.getX() - r, position.getY() - r,2*r,2*r);
    }

    public void actionWhenItIsEndOfScreen()
    {
        if(position.getX() >= screenSize || position.getX() <=0){
            speed.setDx(-speed.getDx());
        }
        if(position.getY() >=screenSize || position.getY() <=0){
            speed.setDy(-speed.getDy());
        }
    }

    public Boolean isGoingToBox(){
        return !bothParamsAreInBox(oldPosition.getX(), oldPosition.getY()) && bothParamsAreInBox(position.getX(), position.getY());
    }

    public Boolean isGoingOutsideBox(){
        return bothParamsAreInBox(oldPosition.getX(), oldPosition.getY()) && !bothParamsAreInBox(position.getX(), position.getY());
    }

    public Boolean bothParamsAreInBox(int xCoord, int yCoord){
        return xIsInBox(xCoord) && yIsInBox(yCoord);
    }

    public Boolean xIsInBox(int coords){
        return isInBox(coords, box.getLeftUpperXParam(), box.getLeftUpperXParam()+box.getWidth());
    }

    public Boolean yIsInBox(int coords){
        return isInBox(coords, box.getLeftUpperYParam(), box.getLeftUpperYParam()+box.getHeight());
    }

    public Boolean isInBox(int coords, double leftBound, double rightBound){
        if((coords>= leftBound)&&(coords<= rightBound)){
            return true;
        }
        else return false;
    }


}
