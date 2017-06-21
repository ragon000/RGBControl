package meme.satan.RGBControl.Effects;

import meme.satan.RGBControl.RGBEffect;

import java.awt.*;
import java.io.OutputStreamWriter;

import static java.lang.Thread.sleep;

/**
 * Created by Philipp on 21.06.2017.
 */
public class Strobe extends RGBEffect {
    private OutputStreamWriter o;


    @Override
    public void run() {
        System.out.println("Rainbow");
        // long millisStart = Calendar.getInstance().getTimeInMillis();
        intensity= 20;
        while(true) {
            try {

 writeColor(new Color(255, 255, 255));
                    sleep(intensity);
                writeColor(new Color(0, 0, 0));

                sleep(intensity);

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}