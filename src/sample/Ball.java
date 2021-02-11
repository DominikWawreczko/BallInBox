package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball implements Runnable, Drawable, Stoppable  {

        Color color;
        volatile boolean running=true;//kiedy running będzie false to jest zabójca wątku

        private volatile int x,y,r,dx,dy,tmpx,tmpy;
        private Box box;


    @Override
    public void run() {
        System.out.println("Wątek się zgłasza");

        while (running){

            ruch();
           // System.out.println("Hej");


        }
    }

    @Override
    public void stop(){
    running=false;
    }

    public void ruch(){

        try {
            czyToSciana();
            tmpx=x;
            tmpy=y;
            x=x+dx;
            y=y+dy;
            Thread.sleep(30);
           // System.out.println("X: "+x+"Y:"+y);
            //Teraz czesc od Boxa
            if(((czyToWBoxie(tmpx)&&czyToWBoxie(tmpy))==false)&&((czyToWBoxie(x)&&czyToWBoxie(y))==true)){
                //wejscie
                this.box.wejscie();
            }
            if(((czyToWBoxie(tmpx)&&czyToWBoxie(tmpy))==true)&&((czyToWBoxie(x)&&czyToWBoxie(y))==false)){
                //wyjscie
                this.box.wyjscie();
            }


        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

    }
    public Ball(int x , int y , int r, int dx , int dy, Box box,Color color){
        this.x=x;
        this.y=y;
        this.r=r;
        this.dx=dx;
        this.dy=dy;
        this.color=color;
        this.box=box;

    }

    @Override
    public void draw (GraphicsContext gc) {
        //  System.out.println("test rysunku");
        gc.setFill(color);
        gc.fillOval(x-r,y-r,2*r,2*r);
    }
    public void  czyToSciana()
    {
        if(x>=512||x<=0){
            dx=-dx;
        }
        if(y>=512||y<=0){
            dy=-dy;
        }
    }

    public Boolean czyToWBoxie(int a){
        if((a>=200)&&(a<=350)){
        return true;}
        else return false;

    }

}
