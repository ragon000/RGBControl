package meme.satan.RGBControl;

import gnu.io.*;
import meme.satan.RGBControl.Effects.Rainbow;
import meme.satan.RGBControl.Effects.Strobe;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Philipp on 13.06.2017.
 */
public class App {
    OutputStreamWriter o;
    SerialPort c=null;
    GSClass gs;
    RGBEffect currentEffect;
    Thread effectThread;
    public App(){
        Iterator i = getAvailableSerialPorts().iterator();
        CommPortIdentifier ci = (CommPortIdentifier) i.next();

        try {
                        c= (SerialPort) ci.open("Satan",50000);
                       setSerialPortParameters();
        //               System.out.println(c.getBaudBase());
                  //  c.setOutputBufferSize(64);

        } catch (PortInUseException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }// catch (UnsupportedCommOperationException e) {
 //           e.printStackTrace();
  //      }
        try {
            o = new OutputStreamWriter(c.getOutputStream(), Charset.forName("ASCII"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    StartFrame();
    }
    private void setSerialPortParameters() throws IOException {
        int baudRate = 9600; // 57600bps

        try {
            // Set serial port to 57600bps-8N1..my favourite
            c.setSerialPortParams(
                    baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);


        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
public void writeColor(Color co){


    try {
        o.write(co.getRed()+","+co.getGreen()+","+co.getBlue()+"\n");
        o.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
    public void StartFrame(){
        JFrame frame = new JFrame("RGBControl");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        //JRadioButtons werden erstellt
        JRadioButton auswahl1 = new JRadioButton("CSGO GameState Intigration");
        JRadioButton auswahl2 = new JRadioButton("ColorChooser");
        JRadioButton auswahl3 = new JRadioButton("Animations");
        //ButtonGroup wird erstellt
        ButtonGroup gruppe = new ButtonGroup();

        //JRadioButtons werden zur ButtonGroup hinzugefügt
        gruppe.add(auswahl1);
        gruppe.add(auswahl2);
        gruppe.add(auswahl3);
        //JRadioButtons werden Panel hinzugefügt
        panel.add(auswahl1);
        panel.add(auswahl2);
        panel.add(auswahl3);

        frame.add(panel, BorderLayout.BEFORE_FIRST_LINE);

        final JColorChooser colorChooser = new JColorChooser();
        AbstractColorChooserPanel[] panels=colorChooser.getChooserPanels();
        for(AbstractColorChooserPanel p:panels){
            String displayName=p.getDisplayName();
            switch (displayName) {
                case "HSV":
                    colorChooser.removeChooserPanel(p);
                    break;
                case "HSL":
                    colorChooser.removeChooserPanel(p);
                    break;
                case "CMYK":
                    colorChooser.removeChooserPanel(p);
                    break;
                case "Swatches":
                    colorChooser.removeChooserPanel(p);
                    break;
            }
        }
        ColorSelectionModel model = colorChooser.getSelectionModel();
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {

                    writeColor(colorChooser.getColor());

            }



        };

        model.addChangeListener(changeListener);

        //frame.add(colorChooser, BorderLayout.CENTER);

        JPanel animations = new JPanel();

        JButton rainbow = new JButton();
        rainbow.setText("Rainbow");

        rainbow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (effectThread!=null)effectThread.interrupt();
                currentEffect = new Rainbow();
                currentEffect.setO(o);
                effectThread = new Thread(currentEffect);
                effectThread.start();
            }
        });
        animations.add(rainbow);
        JButton Strobe = new JButton();
        Strobe.setText("Strobe");

        Strobe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentEffect!=null)effectThread.interrupt();
                currentEffect = new Strobe();
                currentEffect.setO(o);
                effectThread = new Thread(currentEffect);
                effectThread.start();
            }
        });

        animations.add(Strobe);




        JSlider speed = new JSlider(0,100);

        speed.setValue(10);
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                JSlider source = (JSlider) e.getSource();
                currentEffect.setIntensity(source.getValue());
            }
        });
        animations.add(speed);


        frame.pack();
        frame.setVisible(true);
        ActionListener sliceActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                Thread t=null;
                if(aButton.getText().equals("CSGO GameState Intigration")){
                    frame.remove(colorChooser);
                    frame.remove(animations);
                    frame.pack();
                    gs = new GSClass(o);
                    t = new Thread(gs);
                    t.start();
                }
                else if(aButton.getText().equals("ColorChooser")){
                    frame.add(colorChooser, BorderLayout.CENTER);
                    frame.pack();
                    System.out.println("stopping");
            if(gs!=null)gs.shutdown();
            gs=null;
                }
                else{
                    frame.remove(colorChooser);
                    if(gs!=null)gs.shutdown();
                    gs=null;
                    frame.add(animations,BorderLayout.CENTER);
                    frame.pack();

                }
            }
        };
        auswahl1.addActionListener(sliceActionListener);
        auswahl2.addActionListener(sliceActionListener);
        auswahl3.addActionListener(sliceActionListener);
    }


    private void setCurrentEffect(RGBEffect e){

        if(effectThread!=null) effectThread.interrupt();

        effectThread = new Thread(e);
        System.out.println("Starting Thread");
        effectThread.start();
    }
    public static HashSet<CommPortIdentifier> getAvailableSerialPorts() {
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    try {
                        CommPort thePort = com.open("CommUtil", 50);
                        System.out.println(thePort.getName());
                        thePort.close();
                        h.add(com);
                    } catch (PortInUseException e) {
                        System.out.println("Port, "  + com.getName() + ", is in use.");
                    } catch (Exception e) {
                        System.err.println("Failed to open port " +  com.getName());
                        e.printStackTrace();
                    }
            }
        }
        return h;
    }

    public static void main(String args[]){
        new App();
    }
}
