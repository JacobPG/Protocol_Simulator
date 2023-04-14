/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.Packet;
import static PROTOCOLS.EventTypeEnum.FRAME_ARRIVAL;
import static java.lang.Math.random;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeico
 */
public class Utopia extends Protocol{
    
    public Boolean running = true;
    public Utopia(){
        
    }
    
    public Utopia(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }
    
    //void input
    public void sender1(){
        Frame s = new Frame();
        Packet buffer = new Packet("");
        while(running){
            System.out.println("[SENDER]");
            from_network_layer(buffer); //go get something to send
            System.out.println("paquete a enviar: " + buffer.information);
            s.info = buffer; /* copy it into s for transmission */
            to_physical_layer(s); /* send it on its way */
            try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(5001) + 2000;
                sleep(numeroAleatorio);
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    //void input
    public void receiver1(){
        Frame r = new Frame();
	EventTypeEnum event = null; /* filled in by wait, but not used here */
	while (running) {
            wait_for_event(event); /* only possibility is frame arrival */
            System.out.println("[RECIEVER] FRAME ARRIVAL");
            from_physical_layer(r); /* go get the inbound frame */ //de la capa fisica nos van a pasar el frame
            if(r.info==null){
                System.out.println("fallo");
                return;
            }
            to_network_layer(r.info); /* pass the data to the network layer */
            System.out.println("[RECIEVER]: "+ super.packet.information);
	} 
    }
    
    
}
