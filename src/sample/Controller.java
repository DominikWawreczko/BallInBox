package sample;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.lang.Math;




public class Controller {
    public Canvas canvas;
    ArrayList<Drawable>bs;
    ArrayList<Stoppable>koniec;
    Ball b;
    Box box;
    private AnimationTimer timer;

    public void initialize(){
        bs=new ArrayList<Drawable>();//nie zapomnieć !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        koniec=new ArrayList<Stoppable>();
        box = new Box(200,200,150,150);//trzeba uzupełnić na koniec
        bs.add(box);

        //

     timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, 512, 512);
               // b.draw(gc);
               for(Drawable b: bs)
               {
                    b.draw(gc);
              }

            }

        };
        timer.start();
        //



    }


    public void dodajpilke(ActionEvent actionEvent) {
       Ball b = new Ball(losulosu(), losulosu(),box, wybierzkolor());
       bs.add(b);
       koniec.add(b);
        Thread t1 = new Thread(b);
        t1.start();
       



    }

    public Color wybierzkolor(){
        double s;
        s = Math.random();
        if(s<=0.3333){
            return Color.AQUAMARINE;
        }
        else  if (s<=0.6666){
            return Color.BLUEVIOLET;
        } else {
            return Color.GOLD;
        }

    }
    public int losulosu(){
        double s;
        s = Math.random();

        s=s*10;
        if(s>=7||s<=1){
            s=2+3*Math.random();
        }
        return (int)s;
    }


    public void koniecDzialaniaProgramu(ActionEvent actionEvent) {
        for(Stoppable b: koniec)
        {
            b.stop();
        }
        timer.stop();
    }
}
