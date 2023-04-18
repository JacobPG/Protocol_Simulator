package CONTEXT;
import GUI.JFrameMain;
import PROTOCOLS.Protocol;
import PROTOCOLS.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yeico
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            // TODO code application logic here
            //Machine mA = new Machine("Maquina A", 0,0);
            //Machine mB = new Machine("Maquina B", 0,0);
            /*
            StopAndWait stopAndWait = new StopAndWait();
            ProtocolThread stopAndWaitSender = new ProtocolThread(100, stopAndWait,0);
            stopAndWaitSender.startProtocol();
            ProtocolThread stopAndWaitReciever = new ProtocolThread(100, stopAndWait,1);
            stopAndWaitReciever.startProtocol();
            
//            Utopia utopia = new Utopia();
//            ProtocolThread utopiaProtocolSender = new ProtocolThread(100, utopia,0);
//            utopiaProtocolSender.startProtocol();
//            ProtocolThread utopiaProtocolReciever = new ProtocolThread(100, utopia,1);
//            utopiaProtocolReciever.startProtocol();

            
            PAR par = new PAR();
            ProtocolThread par_Sender = new ProtocolThread(100, par,0);
            par_Sender.startProtocol();
            ProtocolThread par_Reciever = new ProtocolThread(100, par,1);
            par_Reciever.startProtocol();
            */
            
            
//            PAR par = new PAR();
//            ProtocolThread par_Sender = new ProtocolThread(100, par,0);
//            par_Sender.startProtocol();
//            ProtocolThread par_Reciever = new ProtocolThread(100, par,1);
//            par_Reciever.startProtocol();

            
            //p.sender1();
            //p.receiver1();
           
            JFrameMain windowProtocolSimulator;
            try {
                windowProtocolSimulator = new JFrameMain();
                windowProtocolSimulator.setVisible(true);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SecurityException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
