/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.Packet;

/**
 *
 * @author yeico
 */
public class Utopia extends Protocol{
    
    public Utopia(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }
    //void input
    public  void sender1(){
        Frame s = new Frame();
        Packet buffer = new Packet();
        while(true){
            from_network_layer(buffer); //go get something to send
            s.info = packet; /* copy it into s for transmission */
            to_physical_layer(s); /* send it on its way */
        }
    }
    //void input
    public void receiver1(){
        
    }
    
    
}
