/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.Packet;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeico
 */
public class StopAndWait extends Protocol{
    
    public StopAndWait(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }
    public StopAndWait(){
        
    }
    
    //void input
    @Override
    public void sender(){
        Frame s = new Frame();
        Packet buffer = new Packet("");
        EventTypeEnum event = null;
        while(running){
            System.out.println("\t\t[SENDER]");
            
            from_network_layer(buffer); //go get something to send
            System.out.println("[SENDER] -> " + buffer.information);
            s.info = buffer; /* copy it into s for transmission */
            to_physical_layer(s); /* send it on its way */        
                       
            try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(15001) + 5000;
                sleep(numeroAleatorio);
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            wait_for_event(event, true); /* only possibility is frame arrival */
            
        }
    }
    //void input
    @Override
    public void receiver(){
        Frame r, s;
        r = new Frame();
        s = new Frame();
  
	EventTypeEnum event = null; /* filled in by wait, but not used here */
	while (running) {
            System.out.println("\t\t[RECIEVER]");
            wait_for_event(event, false); /* only possibility is frame arrival */
            r = from_physical_layer(r); /* go get the inbound frame */ //de la capa fisica nos van a pasar el frame
            if(r.info == null) continue;
            System.out.println("Frame.info: " + r.info.information);
            to_network_layer(r.info); /* pass the data to the network layer */
            System.out.println("Packet: "+ super.packet.information);
            
            to_physical_layer(s); /* send a dummy frame to awaken sender */  
            System.out.println("[RECIEVER] -> Sent a dummy frame to confirm\n");
            
            try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(15001) + 5000;
                sleep(numeroAleatorio);
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            } 
	} 
    }
    
    public void wait_for_event(EventTypeEnum event, Boolean isSender) {
        // implementación de la función
        String author = isSender ? "Sender" : "Receiver";
        System.out.println(author+" is waitting for an event...\n");
        while(event==null){
            try {
                sleep(500);
                if(eventType.equals(EventTypeEnum.FRAME_ARRIVAL) && ((isSender && this.frame.info==null)||(!isSender && this.frame.info!=null))){
                    event=eventType;
                    System.out.println("FRAME ARRIVAL to "+author);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        eventType = EventTypeEnum.TIMEOUT;
        event=null;
    }
}
