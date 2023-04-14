package CONTEXT;
import GUI.JFrameMain;
import PROTOCOLS.Protocol;
import PROTOCOLS.ProtocolThread;
import PROTOCOLS.Utopia;
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
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            //Machine mA = new Machine("Maquina A", 0,0);
            //Machine mB = new Machine("Maquina B", 0,0);
            
            Utopia utopia = new Utopia();
            
            ProtocolThread utopiaProtocolSender = new ProtocolThread(100, utopia,0);
            
            utopiaProtocolSender.startProtocol();
            
            ProtocolThread utopiaProtocolReciever = new ProtocolThread(100, utopia,1);
            utopiaProtocolReciever.startProtocol();
            
            //p.sender1();
            //p.receiver1();
           
            //JFrameMain windowProtocolSimulator = new JFrameMain();
            //windowProtocolSimulator.setVisible(true);
        } catch (SecurityException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
