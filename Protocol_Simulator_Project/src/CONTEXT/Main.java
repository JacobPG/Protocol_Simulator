package CONTEXT;
import GUI.JFrameMain;

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
        // TODO code application logic here
        Machine mA = new Machine("Maquina A", 0,0);
        Machine mB = new Machine("Maquina B", 0,0);
        
        JFrameMain windowProtocolSimulator = new JFrameMain();
        windowProtocolSimulator.setVisible(true);
        
    }
    
}
