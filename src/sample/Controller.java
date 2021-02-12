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
    ArrayList<Drawable> drawables;
    ArrayList<Stoppable> end;
    Box box;
    private AnimationTimer timer;

    public void initialize(){
        drawables =new ArrayList<Drawable>();
        end =new ArrayList<Stoppable>();
        box = new Box(200,200,150,150);
        drawables.add(box);
        timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, 512, 512);
               for(Drawable b: drawables)
               {
                    b.draw(gc);
              }
            }

        };
        timer.start();
       }

    public void addBall(ActionEvent actionEvent) {
       Ball b = new Ball(new Point(20, 20),new Speed(generateRandom(), generateRandom()),box);
       b.setColor(chooseColor());
       drawables.add(b);
       end.add(b);
       Thread t1 = new Thread(b);
       t1.start();
    }

    public Color chooseColor(){
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

    public int generateRandom(){
        double s;
        s = Math.random();
        s=s*10;
        if(s>=7||s<=1){
            s=2+3*Math.random();
        }
        return (int)s;
    }


    public void stopWorkingProgram(ActionEvent actionEvent) {
        for(Stoppable b: end)
        {
            b.stop();
        }
        timer.stop();
    }
}
