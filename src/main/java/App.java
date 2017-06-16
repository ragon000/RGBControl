import gnu.io.*;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
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
    ColorChooser();
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

    public void ColorChooser(){
        JFrame frame = new JFrame("JColorChooser Popup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        final JColorChooser colorChooser = new JColorChooser();

        ColorSelectionModel model = colorChooser.getSelectionModel();
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                Color newForegroundColor = colorChooser.getColor();
                try {
                    System.out.print(newForegroundColor.getRed()+","+newForegroundColor.getGreen()+","+newForegroundColor.getBlue()+"\n");
                    o.write(newForegroundColor.getRed()+","+newForegroundColor.getGreen()+","+newForegroundColor.getBlue()+"\n");
System.out.println(c.getOutputBufferSize());
    o.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        model.addChangeListener(changeListener);

        frame.add(colorChooser, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
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
