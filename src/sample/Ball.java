package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Runnable, Drawable, Stoppable  {

    Color color;
    volatile boolean running=true;
    private final int r;
    private volatile int x,y,dx,dy, oldX, oldY;
    private final Box box;

    public Ball( int dx , int dy, Box box,Color color){

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
        oldX =x;
        oldY =y;
        x=x+dx;
        y=y+dy;
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
        //  System.out.println("test rysunku");
        gc.setFill(color);
        gc.fillOval(x-r,y-r,2*r,2*r);
    }

    public void actionWhenItIsEndOfScreen()
    {
        if(x>=512||x<=0){
            dx=-dx;
        }
        if(y>=512||y<=0){
            dy=-dy;
        }
    }

    public Boolean isInBox(int a){
        if((a>=200)&&(a<=350)){
        return true;}
        else return false;

    }
    public Boolean isGoingToBox(){
        return ((isInBox(oldX)&& isInBox(oldY))==false)&&((isInBox(x)&& isInBox(y))==true);
    }
    public Boolean isGoingOutsideBox(){
        return ((isInBox(oldX)&& isInBox(oldY))==true)&&((isInBox(x)&& isInBox(y))==false);
    }

}
