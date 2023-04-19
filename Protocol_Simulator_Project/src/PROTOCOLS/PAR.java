
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.FrameKindEnum;
import CONTEXT.Packet;
import GUI.MyThread;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author yeico
 */
public class PAR extends Protocol{
    
    private EventTypeEnum eventForReceiver;
    private EventTypeEnum eventForSender;
    
                    
    public PAR(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }
    public PAR(){
    }
    
    //void input
    @Override
    public void sender(){
        Frame s = new Frame();
        Packet buffer = new Packet("");
        EventTypeEnum event = null;
        
        int next_frame_to_send = 0;
        from_network_layer(buffer); //go get something to send
        
        long wait_for_frame;
        JLabel text = new JLabel(String.valueOf(s.seq));
        while(running){
            s = new Frame();
            /* copy it into s for transmission */
            s.info = buffer;
            s.seq = next_frame_to_send;
            s.hasAnError = hasAnError();
            
            to_physical_layer(s); /* se way nd it on its*/
            System.out.println("[SENDER] -> " + buffer.information + " - Seq: "+s.seq);          
            //star_timer(s.ack)
            wait_for_frame = System.nanoTime(); //star_timer
            System.out.println("[SENDER] " + "Sender is waitting for an event...");
            
            
            graphicThread.estado = "arriba";
            graphicThread.framePanel.removeAll();
            text.setText(String.valueOf(s.seq));
            text.setBounds(0, 0, 40, 20);
            Random r = new Random();
            graphicThread.speed = r.nextInt(100)+20;
            graphicThread.framePanel.add(text);
            graphicThread.framePanel.setBackground(Color.BLUE);
            graphicThread.startMyThread();
            while(graphicThread.execute){
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo PAR");            
            try {
                graphicThread.framePanel.setBackground(Color.GREEN); 
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            } 
            eventForSender = null;
            this.eventForReceiver = EventTypeEnum.FRAME_ARRIVAL;//set the frame arrival for receiver
            
            event=null;
            while(event==null){
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                event = wait_for_event_S(event, wait_for_frame); /* only possibility is frame arrival */
            }            
            if (event.equals(EventTypeEnum.FRAME_ARRIVAL)){
                eventForSender = null;
                s = from_physical_layer(s);
                if(s.ack == next_frame_to_send){
                    System.out.println("[SENDER] " + "Frame Arrival Expected in Sender");
                    wait_for_frame=0;//stop_timer(s.ack)
                    from_network_layer(buffer); //go get something to send
                    next_frame_to_send = (next_frame_to_send==1) ? 0 : 1;//inc
                }
                else{
                    System.out.println("[SENDER] " + "Frame Arrival Unexpected in Sender");
                }
            }
            else if(event.equals(EventTypeEnum.CKSUM_ERR)){
                eventForSender = null;
                graphicThread.finish();
                graphicThread.framePanel.setBackground(Color.yellow);
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread.framePanel.setVisible(false);
                try {
                    sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread.framePanel.setBounds(205, 125, 50, 25);
                graphicThread.framePanel.setVisible(true);
                try {
                    graphicThread.framePanel.setBackground(Color.RED);
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo PAR");
            }
            else if(event.equals(EventTypeEnum.TIMEOUT)){
                eventForSender = null;
                graphicThread.finish();
                graphicThread.framePanel.setVisible(false);
                try {
                    sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread.framePanel.setBounds(205, 125, 50, 25);
                graphicThread.framePanel.setVisible(true);
                try {
                    graphicThread.framePanel.setBackground(Color.RED);
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo PAR");            
                
            }

        }
    }
    //void input
    @Override
    public void receiver(){
        
        Frame r, s;
        r = new Frame();
        s = new Frame();
        
        int frame_expected = 0;
  
	EventTypeEnum event = null; /* filled in by wait, but not used here */
        JLabel text = new JLabel(String.valueOf(s.ack));
        System.out.println("[RECEIVER] " + "Receiver is waitting for an event...");
	while (running) {
            
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            event = null;
            event = wait_for_event_R(event); /* only possibility is frame arrival */
            if(event.equals(EventTypeEnum.FRAME_ARRIVAL)){
                eventForReceiver = null;
                r = from_physical_layer(r); /* go get the inbound frame */ //de la capa fisica nos van a pasar el frame
                if(r.info == null){
                    continue;
                }
                if (r.seq==frame_expected){
                    System.out.println("[RECEIVER] " + "Frame Arrival Expected in Receiver");
                    System.out.println("[RECEIVER] " + "Frame.info: " + r.info.information);
                    to_network_layer(r.info); /* pass the data to the network layer */
                    frame_expected = (frame_expected == 1) ? 0 : 1;//inc
                }else{
                    System.out.println("[RECEIVER] " + "Frame Arrival Unexpected in Receiver");
                    System.out.println("Frame: "+r.info.information);
                }
                
                
                s = new Frame();
                s.hasAnError = hasAnError();
                s.ack = 1-frame_expected;
                to_physical_layer(s); /* send a dummy frame to awaken sender */
                
                
                
                System.out.println("[RECIEVER] -> Send a frame to confirm"+" - Ack: "+s.ack);
                System.out.println("[RECEIVER] " + "Receiver is waitting for an event...");
                
                graphicThread.estado = "abajo";
                graphicThread.framePanel.removeAll();
                text.setText(String.valueOf(s.ack));
                text.setBounds(0, 0, 40, 20);
                graphicThread.framePanel.add(text);
                graphicThread.framePanel.setBackground(new Color(64,207,255));
                graphicThread.startMyThread();
                while(graphicThread.execute){
                    try {
                        sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo PAR");            
                eventForReceiver = null;
                this.eventForSender = EventTypeEnum.FRAME_ARRIVAL;//Set the frame arrival for sender
            }
            if(this.eventForReceiver == EventTypeEnum.CKSUM_ERR){
                if(graphicThread.framePanel.getBackground().equals(Color.GREEN)){
                    graphicThread.framePanel.setBackground(Color.yellow);
                }
            }
            /*else if(event.equals(EventTypeEnum.CKSUM_ERR)){
                System.out.println("este es el cksum error que le llega al receptor");
            }*/
	} 
    }
    
    public EventTypeEnum wait_for_event_S(EventTypeEnum event, long initTime) {
        // implementación de la función   
        
        String author = "Sender";
        while(event==null){
            
            
            if ( (System.nanoTime()-initTime)/1000000 >= waitTime ){
                System.out.println("Diferencia: "+(System.nanoTime()-initTime)/1000000);
                if (this.eventForSender!=null )System.out.println("eventType:"+this.eventForSender.name());
                break;
            }
            
            if ( eventForSender!=EventTypeEnum.FRAME_ARRIVAL ){
                return null;
            }
                        
            if( eventForSender.equals(EventTypeEnum.FRAME_ARRIVAL) && this.frame.info==null ){
                
                if(this.frame.hasAnError){
                    System.out.println("CKSUM_ERR in the frame sent to "+author);
                    this.eventForSender = EventTypeEnum.CKSUM_ERR;
                    return EventTypeEnum.CKSUM_ERR;
                    
                }else{
                    System.out.println("\nFRAME ARRIVAL to "+author+"\n");                    
                    this.eventForSender = EventTypeEnum.FRAME_ARRIVAL;                    
                    return EventTypeEnum.FRAME_ARRIVAL;
                }
            }else if (this.frame.info!=null){
                return null;
            }
        }
        
        System.out.println("TIMEOUT in the frame sent by "+author);
        this.eventForSender = EventTypeEnum.TIMEOUT;
        return EventTypeEnum.TIMEOUT;
    }
    
    public EventTypeEnum wait_for_event_R(EventTypeEnum event) {
        // implementación de la función   
        
        String author = "Receiver";
        while(event==null){
                        
            if(this.eventForReceiver!=EventTypeEnum.FRAME_ARRIVAL){
                return EventTypeEnum.CKSUM_ERR;
            }
            
            if(eventForReceiver.equals(EventTypeEnum.FRAME_ARRIVAL) && this.frame.info!=null){
                
                if(this.frame.hasAnError){
                    System.out.println("CKSUM_ERR in the frame sent to "+author);
                    
                    this.eventForReceiver = EventTypeEnum.CKSUM_ERR;
                    
                    return EventTypeEnum.CKSUM_ERR;
                }else{
                    System.out.println("\nFRAME ARRIVAL to "+author+"\n");
                    
                    this.eventForReceiver = EventTypeEnum.CKSUM_ERR;
                    
                    return EventTypeEnum.FRAME_ARRIVAL;
                }
            }
        }
        return EventTypeEnum.CKSUM_ERR;
    }
    
    @Override
    public void to_physical_layer(Frame s) {
        s.frameKind = FrameKindEnum.DATA;
        this.frame = s;
    }
    
    
}
