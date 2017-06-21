package meme.satan.RGBControl; /**
 * Created by Philipp on 17.06.2017.
 */
import com.brekcel.csgostate.JSON.JsonResponse;
import com.brekcel.csgostate.post.PostHandlerAdapter;
import javafx.scene.effect.Effect;
import meme.satan.RGBControl.Effects.BombPlanted;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PostHandlerchen extends PostHandlerAdapter {
    OutputStreamWriter o;
    RGBEffect currentEffect;
    Thread effectThread;
    public PostHandlerchen(OutputStreamWriter out){
        o=out;
    }

    private void setCurrentEffect(RGBEffect e){

        if(effectThread!=null) effectThread.interrupt();

        effectThread = new Thread(e);
        System.out.println("Starting Thread");
        effectThread.start();
    }
    public void writeColor(Color co){


        try {
            o.write(co.getRed()+","+co.getGreen()+","+co.getBlue()+"\n");
            o.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void receivedJsonResponse(JsonResponse jsonResponse) {
     //   System.out.println(jsonResponse.getAdded().toString());
    }

    @Override
    public void bombPlanted(){
        System.out.println("BombPlanted");
        RGBEffect bp = new BombPlanted();
        bp.setDuration(45);
        bp.setO(o);
        setCurrentEffect(bp);
    }



}