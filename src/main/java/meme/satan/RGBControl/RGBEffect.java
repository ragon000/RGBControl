package meme.satan.RGBControl;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Philipp on 17.06.2017.
 */
public abstract class RGBEffect implements Runnable{
    protected OutputStreamWriter o;
    protected int duration;
    protected int intensity;

    public OutputStreamWriter getO() {
        return o;
    }

    public int getDuration() {
        return duration;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }



    public void setO(OutputStreamWriter o) {
        this.o = o;
    }
    protected void writeColor(Color co){


        try {
            o.write(co.getRed()+","+co.getGreen()+","+co.getBlue()+"\n");
            o.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
