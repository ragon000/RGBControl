package meme.satan.RGBControl.Effects;

import meme.satan.RGBControl.RGBEffect;

import java.awt.*;
import java.io.OutputStreamWriter;
import java.util.Calendar;


/**
 * Created by Philipp on 17.06.2017.
 */
public class BombPlanted extends RGBEffect{
    private OutputStreamWriter o;


    @Override
    public void run(){
System.out.println("run");
       // long millisStart = Calendar.getInstance().getTimeInMillis();
        long millisVergangen=0;
        int i=0;
        System.out.println(duration);
while(millisVergangen<duration*1000){
System.out.println("suicide");
            writeColor(new Color(255,0,0));
        System.out.println("red");

            try {
                Thread.sleep(200);
                System.out.println("black");
                millisVergangen+=200;
                writeColor(new Color(0,0,0));
                double sleepTime = (1-(i/duration))*1000;
                millisVergangen+=(1-(i/duration))*1000;
                Thread.sleep((long) sleepTime);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

}
