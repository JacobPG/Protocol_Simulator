/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.FrameKindEnum;
import CONTEXT.Packet;
import static PROTOCOLS.EventTypeEnum.FRAME_ARRIVAL;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeico
 */
public class Protocol extends Thread{
    public Boolean running = true;
    
    Frame frame;
    int MAX_SEQ;
    EventTypeEnum eventType;
    int seq_nr;
    Packet packet;
    
    float errorRate = 0; //from 0 to 1, where 1 all frames will have an error.
    /* time to wait for the confirmation frame from Receiver [in nano-seconds]*/
    int waitTime = 16000; 
   
    public Protocol(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        this.frame = frame;
        this.MAX_SEQ = MAX_SEQ;
        this.eventType = eventType;
        this.seq_nr = seq_nr;
        this.packet = packet;
    }
    public Protocol(){
        
    }
    
    /* Wait for an event to happen; return its type in event. */
    public void wait_for_event(EventTypeEnum event) {
        // implementación de la función
        System.out.println("Receiver wait event...");
        while(event==null){
            try {
                sleep(500);
                if(eventType!=null){
                    if(eventType.equals(EventTypeEnum.FRAME_ARRIVAL)){
                        event=eventType;
                        System.out.println("FRAME ARRIVAL");
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        eventType = EventTypeEnum.TIMEOUT;
        event=null;
    }

    /* Fetch a packet from the network layer for transmission on the channel. */
    public void from_network_layer(Packet p) {
        //p = new Packet(randomWordGenerate());
        p.information = randomWordGeneration();
        //p.information = packet.information;
    }

    /* Deliver information from an inbound frame to the network layer. */
    public void to_network_layer(Packet p) {
        //System.out.println("information:"+p.information);
        packet = p;
        
    }

    /* Go get an inbound frame from the physical layer and copy it to r. */
    public Frame from_physical_layer(Frame r) {
        //Obtenga un marco entrante de la capa física y cópielo en r.
        r = this.frame;
        //System.out.println("From physical layer -  Frame.info:" + r.info.information);
        return this.frame;
        
    }

    /* Pass the frame to the physical layer for transmission. */
    public void to_physical_layer(Frame s) {
        s.frameKind = FrameKindEnum.DATA;
        this.frame = s;
        //eventType = EventTypeEnum.FRAME_ARRIVAL;
    }

    /* Start the clock running and enable the timeout event. */
    public void start_timer(int k) { //seq_nr
        // implementación de la función
    }

    /* Stop the clock and disable the timeout event. */
    public void stop_timer(int k) { //seq_nr
        // implementación de la función
    }

    /* Start an auxiliary timer and enable the ack timeout event. */
    public void start_ack_timer() {
        // implementación de la función
    }

    /* Stop the auxiliary timer and disable the ack timeout event. */
    public void stop_ack_timer() {
        // implementación de la función
    }

    /* Allow the network layer to cause a network layer ready event. */
    public void enable_network_layer() {
        // implementación de la función
    }

    /* Forbid the network layer from causing a network layer ready event. */
    public void disable_network_layer() {
        // implementación de la función
    }
    
    public String randomWordGeneration(){
        int longitud = 7; // Longitud del String aleatorio a generar
        String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Caracteres permitidos para el String

        Random random = new Random();
        StringBuilder sb = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            char caracterAleatorio = caracteres.charAt(indice);
            sb.append(caracterAleatorio);
        }

        String stringAleatorio = sb.toString();
        return stringAleatorio;
    }

    public void setErrorRate(float errorRate) {
        this.errorRate = (float)errorRate;
    }
    
    public Boolean hasAnError(){        
        Random random = new Random();
        return random.nextFloat() < this.errorRate;
    }
    
    public void sender(){} //method to sender packets for subprotocols that extend this class
    public void receiver(){} //method to receive packets for subprotocols that extend this class
}
