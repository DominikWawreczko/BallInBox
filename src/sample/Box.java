package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box implements Drawable{
    public int leftUpperXParam, leftUpperYParam, height, width;
    volatile int ballsCounter =0;


    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(Color.BROWN);
        gc.strokeRect(leftUpperXParam, leftUpperYParam, width, height);

    }
    public Box(int leftUpperXParam, int leftUpperYParam, int height, int width){
        this.leftUpperXParam = leftUpperXParam;
        this.leftUpperYParam = leftUpperYParam;
        this.height = height;
        this.width = width;

    }
    public synchronized void input(){

        try { while(ballsCounter >0) {
            wait();
        }
        } catch (InterruptedException e) {

            System.out.println("Interrupted");
        }
        ballsCounter++;

    }
    public synchronized void output(){
        System.out.println("Wychodze");
        ballsCounter = ballsCounter -1;
        notify();


    }
}
