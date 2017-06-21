package meme.satan.RGBControl;

import com.brekcel.csgostate.Server;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static java.lang.Thread.sleep;

/**
 * Created by Philipp on 17.06.2017.
 */
public class GSClass implements Runnable{
    private OutputStreamWriter o;
    Server s=null;
    private volatile boolean shutdown;
    public GSClass(OutputStreamWriter out){
        o=out;
    }
   @Override
    public void run(){


    try {
        s = new Server(3000, new PostHandlerchen(o),true,null);
    } catch (IOException e) {
        e.printStackTrace();
    }

;
   }
    public void shutdown() {
        s.stop();

    }

}
