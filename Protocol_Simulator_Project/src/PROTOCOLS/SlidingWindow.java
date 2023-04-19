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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author yeico
 */
public class SlidingWindow extends Protocol{
    private Frame frameA;
    private Frame frameB;
    
    private EventTypeEnum eventForMachineB;
    private EventTypeEnum eventForMachineA;
    
    public SlidingWindow(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        super(frame, MAX_SEQ, eventType, seq_nr, packet);
    }

    public SlidingWindow() {
    }
    
    @Override
    public void sender(){
        Frame s, r;
        Packet buffer = new Packet("");
        EventTypeEnum event = null;
        long wait_for_frame;
        
        int next_frame_to_send = 0;
        int frame_expected = 0;
        from_network_layer(buffer); //go get something to send
        
        
        s = new Frame();
        s.info = buffer;
        s.seq = next_frame_to_send;
        s.ack = 1 - frame_expected;
        s.hasAnError = hasAnError();
        
        to_physical_layer(s, true);
        System.out.println("[MACHINE A] -> " + buffer.information + " - Seq: "+s.seq + " - ACK: "+s.ack);
        
        wait_for_frame = System.nanoTime(); //star_timer
        System.out.println("[MACHINE A] " + "MACHINE A is waitting for an event...");
        
        /*
        try {
            Random random = new Random();
            int numeroAleatorio = random.nextInt(15001) + 5000;
            sleep(numeroAleatorio);
        } catch (InterruptedException ex) {
            Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        JLabel text = new JLabel(String.valueOf(s.seq));
        graphicThread.estado = "arriba";
        graphicThread.framePanel.removeAll();
        text.setText(String.valueOf(s.seq + "-" + s.ack));
        text.setBounds(0, 0, 40, 20);
        Random ra = new Random();
        graphicThread.speed = ra.nextInt(100)+20;
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
        try {
            graphicThread.framePanel.setBackground(Color.GREEN); 
            sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
        }
        graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo SlidingWindow");            
        
        
        this.eventForMachineA = null;
        this.eventForMachineB = EventTypeEnum.FRAME_ARRIVAL;//set the frame arrival for receiver
        
        while(running){
            
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
                eventForMachineA=null;
                r = new Frame();
                r = from_physical_layer(r, true);
                
                if (r.seq==frame_expected){
                    System.out.println("[MACHINE A] " + "Frame Arrival Expected in MACHINE A");
                    System.out.println("[MACHINE A] " + "Frame.info: " + r.info.information);
                    to_network_layer(r.info); /* pass the data to the network layer */
                    frame_expected = (frame_expected == 1) ? 0 : 1;//inc
                }else{
                    System.out.println("[MACHINE A] " + "Frame Arrival Unexpected in MACHINE A");
                    System.out.println("[MACHINE A] " + "Frame.info: " + r.info.information);
                }
                
                if(r.ack == next_frame_to_send){
                    wait_for_frame=0;//stop_timer(s.ack)
                    from_network_layer(buffer); //go get something to send
                    next_frame_to_send = (next_frame_to_send==1) ? 0 : 1;//inc
                }              
            }
            else if(event.equals(EventTypeEnum.CKSUM_ERR)){
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
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo SlidingWindow");
                
            }
            else if(event.equals(EventTypeEnum.TIMEOUT)){
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
            
            s = new Frame();
            s.info = buffer;
            s.seq = next_frame_to_send;
            s.ack = 1 - frame_expected;
            s.hasAnError = hasAnError();

            to_physical_layer(s, true); /* send a dummy frame to awaken sender */
            System.out.println("[MACHINE A] -> " + buffer.information + " - Seq: "+s.seq + " - ACK: "+s.ack);
            
            wait_for_frame = System.nanoTime(); //star_timer
            System.out.println("[MACHINE A] " + "MACHINE A is waitting for an event...");
            
            /*try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(15001) + 5000;
                sleep(numeroAleatorio);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            graphicThread.estado = "arriba";
            graphicThread.framePanel.removeAll();
            text.setText(String.valueOf(s.seq + "-" +s.ack));
            text.setBounds(0, 0, 40, 20);
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
            try {
                graphicThread.framePanel.setBackground(Color.GREEN); 
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            }
            graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo SlidingWindow");
            
            this.eventForMachineA= null;
            this.eventForMachineB = EventTypeEnum.FRAME_ARRIVAL;//set the frame arrival for receiver
        }
    }
    
    //void input
    @Override
    public void receiver(){
        Frame s, r;
        Packet buffer = new Packet("");
        EventTypeEnum event = null;
        long wait_for_frame;
        
        int next_frame_to_send = 1;
        int frame_expected = 0;
        from_network_layer(buffer); //go get something to send
        
        /*
        s = new Frame();
        s.info = buffer;
        s.seq = next_frame_to_send;
        s.ack = 1 - frame_expected;
        s.hasAnError = hasAnError();
        
        to_physical_layer(s, true);
        System.out.println("[MACHINE B] -> " + buffer.information + " - Seq: "+s.seq + " - ACK: "+s.ack);
        */
        wait_for_frame = System.nanoTime(); //star_timer
        System.out.println("[MACHINE B] " + "MACHINE B is waitting for an event...");
        
        /*
        try {
            Random random = new Random();
            int numeroAleatorio = random.nextInt(15001) + 5000;
            sleep(numeroAleatorio);
        } catch (InterruptedException ex) {
            Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.eventForMachineA = null;
        this.eventForMachineB = EventTypeEnum.FRAME_ARRIVAL;//set the frame arrival for receiver
        */
        
        while(running){
            
            
            event=null;
            while(event==null){
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                event = wait_for_event_R(event, wait_for_frame); /* only possibility is frame arrival */
            }
            
            
            if (event.equals(EventTypeEnum.FRAME_ARRIVAL)){
                eventForMachineB=null;
                r = new Frame();
                r = from_physical_layer(r, false);
                
                if (r.seq==frame_expected){
                    System.out.println("[MACHINE B] " + "Frame Arrival Expected in MACHINE B");
                    System.out.println("[MACHINE B] " + "Frame.info: " + r.info.information);
                    to_network_layer(r.info); /* pass the data to the network layer */
                    frame_expected = (frame_expected == 1) ? 0 : 1;//inc
                }else{
                    System.out.println("[MACHINE B] " + "Frame Arrival Unexpected in MACHINE B");
                    System.out.println("[MACHINE B] " + "Frame.info: " + r.info.information);
                }
                
                if(r.ack == next_frame_to_send){
                    wait_for_frame=0;//stop_timer(s.ack)
                    from_network_layer(buffer); //go get something to send
                    next_frame_to_send = (next_frame_to_send==1) ? 0 : 1;//inc
                }                  
            }
            else if(event.equals(EventTypeEnum.CKSUM_ERR)){
                //graphicThread.finish();
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
                graphicThread.framePanel.setBounds(725, 125, 50, 25);
                graphicThread.framePanel.setVisible(true);
                try {
                    graphicThread.framePanel.setBackground(Color.RED);
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                //graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo SlidingWindow");
                
            }
            else if(event.equals(EventTypeEnum.TIMEOUT)){
                graphicThread.finish();
                graphicThread.framePanel.setVisible(false);
                try {
                    sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread.framePanel.setBounds(725, 125, 50, 25);
                graphicThread.framePanel.setVisible(true);
                try {
                    graphicThread.framePanel.setBackground(Color.RED);
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
                }
                graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo PAR");
            }
            
            s = new Frame();
            s.info = buffer;
            s.seq = next_frame_to_send;
            s.ack = 1 - frame_expected;
            s.hasAnError = hasAnError();

            to_physical_layer(s, false); /* send a dummy frame to awaken sender */
            System.out.println("[MACHINE B] -> " + buffer.information + " - Seq: "+s.seq + " - ACK: "+s.ack);

            wait_for_frame = System.nanoTime(); //star_timer
            System.out.println("[MACHINE B] " + "MACHINE B is waitting for an event...");
            
            /*
            try {
                Random random = new Random();
                int numeroAleatorio = random.nextInt(15001) + 5000;
                sleep(numeroAleatorio);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            JLabel text = new JLabel(String.valueOf(s.seq));
            graphicThread.estado = "abajo";
            graphicThread.framePanel.removeAll();
            text.setText(String.valueOf(s.seq + "-" +s.ack));
            text.setBounds(0, 0, 40, 20);
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
            try {
                graphicThread.framePanel.setBackground(Color.GREEN); 
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PAR.class.getName()).log(Level.SEVERE, null, ex);
            }
            graphicThread = new MyThread(graphicThread.speed,graphicThread.protocolPanel,graphicThread.framePanel, "Protocolo SlidingWindow");
            
            this.eventForMachineB= null;
            this.eventForMachineA = EventTypeEnum.FRAME_ARRIVAL;//set the frame arrival for receiver
        }
    }
    
    public EventTypeEnum wait_for_event_S(EventTypeEnum event, long initTime) {
        // implementación de la función   
        
        String author = "Machine A";
        while(event==null){
            
            
            if ( (System.nanoTime()-initTime)/1000000 >= waitTime ){
                System.out.println("");
                if (this.eventForMachineA!=null )System.out.println("eventType:"+this.eventForMachineA.name());
                break;
            }
            
            if ( eventForMachineA!=EventTypeEnum.FRAME_ARRIVAL ){
                return null;
            }
                        
            if( eventForMachineA.equals(EventTypeEnum.FRAME_ARRIVAL)){
                
                if(this.frameB.hasAnError){
                    System.out.println("CKSUM_ERR in the frame sent to "+author);
                    this.eventForMachineA = null;
                    return EventTypeEnum.CKSUM_ERR;
                    
                }else{
                    System.out.println("\nFRAME ARRIVAL to "+author+"\n");                    
                    this.eventForMachineA = null;                    
                    return EventTypeEnum.FRAME_ARRIVAL;
                }
            }else if (this.frameB.info!=null){
                return null;
            }
        }
        
        System.out.println("TIMEOUT in the frame sent by "+author);
        this.eventForMachineA = null;
        return EventTypeEnum.TIMEOUT;
    }
    
    public EventTypeEnum wait_for_event_R(EventTypeEnum event, long initTime) {
        // implementación de la función   
        
        String author = "Machine B";
        while(event==null){
            
            
            if ( (System.nanoTime()-initTime)/1000000 >= waitTime ){
                System.out.println("Diferencia: "+(System.nanoTime()-initTime)/1000000);
                if (this.eventForMachineB!=null )System.out.println("eventType:"+this.eventForMachineB.name());
                break;
            }
            
            if ( eventForMachineB!=EventTypeEnum.FRAME_ARRIVAL ){
                return null;
            }
                        
            if( eventForMachineB.equals(EventTypeEnum.FRAME_ARRIVAL)){
                
                if(this.frameA.hasAnError){
                    System.out.println("CKSUM_ERR in the frame sent to "+author);
                    this.eventForMachineB = null;
                    return EventTypeEnum.CKSUM_ERR;
                    
                }else{
                    System.out.println("\nFRAME ARRIVAL to "+author+"\n");                    
                    this.eventForMachineB = null;                    
                    return EventTypeEnum.FRAME_ARRIVAL;
                }
            }else if (this.frameA.info!=null){
                return null;
            }
        }
        
        System.out.println("TIMEOUT in the frame sent by "+author);
        this.eventForMachineB = null;
        return EventTypeEnum.TIMEOUT;
    }
    
    
     public Frame from_physical_layer(Frame r, Boolean isMachineA) {
        //Obtenga un marco entrante de la capa física y cópielo en r.
        if(isMachineA){//if is machine A, get the frame sent by machine B in of the frameB
            r = this.frameB;
        }else{//if is machine B, get the frame sent by machine A in of the frameB
            r = this.frameA;
        }
        
        //System.out.println("From physical layer -  Frame.info:" + r.info.information);
        return r;
        
    }

    /* Pass the frame to the physical layer for transmission. */
    public void to_physical_layer(Frame s, Boolean isMachineA) {
        s.frameKind = FrameKindEnum.DATA;
        
        if(isMachineA){//if is machine A, send the frameto machine B using the frameA
            this.frameA = s;
        }else{//if is machine A, send the frameto machine B using the frameA
            this.frameB = s;
        }
        //eventType = EventTypeEnum.FRAME_ARRIVAL;
    }
    
}
