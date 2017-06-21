package meme.satan.RGBControl.Effects;

import meme.satan.RGBControl.RGBEffect;

import java.awt.*;
import java.io.OutputStreamWriter;

import static java.lang.Thread.*;

/**
 * Created by Philipp on 21.06.2017.
 */


public class Rainbow extends RGBEffect {
    private OutputStreamWriter o;


    @Override
    public void run() {
        System.out.println("Rainbow");
        // long millisStart = Calendar.getInstance().getTimeInMillis();
        intensity= 20;
        while(true) {
            try {
                System.out.println(intensity);
                for (int r = 0; r < 100; r++){ writeColor(new Color(r * 255 / 100, 255, 0));
                    sleep(intensity);
                }

                for (int g = 100; g > 0; g--) {writeColor(new Color(255, g * 255 / 100, 0));
                    sleep(intensity);
                }
                for (int b = 0; b < 100; b++){ writeColor(new Color(255, 0, b * 255 / 100));
                    sleep(intensity);
                }
                for (int r = 100; r > 0; r--){ writeColor(new Color(r * 255 / 100, 0, 255));
                    sleep(intensity);
                }
                for (int g = 0; g < 100; g++){ writeColor(new Color(0, g * 255 / 100, 255));
                    sleep(intensity);
                }
                for (int b = 100; b > 0; b--){ writeColor(new Color(0, 255, b * 255 / 100));
                    sleep(intensity);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}
