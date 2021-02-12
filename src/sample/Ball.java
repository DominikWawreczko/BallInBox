package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Runnable, Drawable, Stoppable  {

    Color color;
    volatile boolean running=true;
    private final int r;
    private volatile int x,y,dx,dy, oldX, oldY;
    private final Box box;
    private final int screenSize;
    /*
    WARNING program is not using RWD technics for balls border because the purpose of this program is to show Java back-end
    not how to implement interface
     */
    public Ball( int dx , int dy, Box box,Color color){
        screenSize =512;
        this.x=20;
        this.y=20;
        this.r=20;
        this.dx=dx;
        this.dy=dy;
        this.color=color;
        this.box=box;
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
        oldX = x;
        oldY = y;
        x = x+dx;
        y = y+dy;
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
        gc.fillOval(x-r,y-r,2*r,2*r);
    }

    public void actionWhenItIsEndOfScreen()
    {
        if(x>=screenSize||x<=0){
            dx=-dx;
        }
        if(y>=screenSize||y<=0){
            dy=-dy;
        }
    }
    public Boolean isGoingToBox(){
        return (!(xIsInBox(oldX) && yIsInBox(oldY)))&&((xIsInBox(x) && yIsInBox(y)));
    }
    public Boolean isGoingOutsideBox(){
        return ((xIsInBox(oldX) && yIsInBox(oldY)))&&(!(xIsInBox(x) && yIsInBox(y)));
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
