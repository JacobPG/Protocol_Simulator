/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.Packet;
import GUI.MyThread;
import static PROTOCOLS.EventTypeEnum.FRAME_ARRIVAL;
import java.awt.Color;
import static java.lang.Math.random;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author yeico
 */
public class Utopia extends Protocol{
    

    
    public Utopia(){
        
    }
    
    public Utopia(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }
    
    //void input
    @Override
    public void sender(){
        Frame s = new Frame();
        Packet buffer = new Packet("");
        while(running){
            System.out.println("[SENDER]");
            from_network_layer(buffer); //go get something to send
            System.out.println("[SENDER] ->" + buffer.information);
            s.info = buffer; /* copy it into s for transmission */
            to_physical_layer(s); /* send it on its way */  
            graphicThread.startMyThread();
            while(graphicThread.execute){
                try {
                    //System.out.println("no frame arrival");
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            super.eventType = EventTypeEnum.FRAME_ARRIVAL;
            
            //activar animacion
            try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(1001) + 1000;
                graphicThread.framePanel.setBackground(Color.green);
                sleep(numeroAleatorio);
                graphicThread.framePanel.setBackground(Color.BLUE);
                graphicThread.framePanel.setBounds(205, 125, 50, 25);
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo Utopia");
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    //void input
    @Override
    public void receiver(){
        Frame r = new Frame();
	EventTypeEnum event = null; /* filled in by wait, but not used here */
	while (running) {
            wait_for_event(event); /* only possibility is frame arrival */
            System.out.println("[RECIEVER]");
            r = from_physical_layer(r); /* go get the inbound frame */ //de la capa fisica nos van a pasar el frame
            System.out.println("Frame.info:" + r.info.information);
            to_network_layer(r.info); /* pass the data to the network layer */
            System.out.println("Packet: "+ super.packet.information);
	} 
    }
    
    
}
