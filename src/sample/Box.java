package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Box implements Drawable{
    public int koordyLewyGornyRogX, koordyLewyGornyRogY, wysokosc, szerokosc;
    GraphicsContext gc;
    volatile int licznikPilek=0;


    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(Color.BROWN);
        gc.strokeRect(koordyLewyGornyRogX,koordyLewyGornyRogY,szerokosc,wysokosc);

    }
    public Box(int koordyLewyGornyRogX,int koordyLewyGornyRogY,int wysokosc,int szerokosc){
        this.koordyLewyGornyRogX=koordyLewyGornyRogX;
        this.koordyLewyGornyRogY=koordyLewyGornyRogY;
        this.wysokosc=wysokosc;
        this.szerokosc=szerokosc;

    }
    public synchronized void wejscie(){

        try { while(licznikPilek>0) {

            System.out.println("czekam");

            wait();

        }
        } catch (InterruptedException e) { }
        licznikPilek++;

    }
    public synchronized void wyjscie(){
        System.out.println("Wychodze");
        licznikPilek=licznikPilek-1;
        notify();


    }
}
