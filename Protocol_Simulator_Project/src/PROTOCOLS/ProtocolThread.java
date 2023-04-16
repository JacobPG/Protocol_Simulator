/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

/**
 *
 * @author yeico
 */
public class ProtocolThread extends Thread{
    private int speed;
    private boolean execute = false;
    private Protocol protocol;
    private int typeCommunication;

    public ProtocolThread(int speed, Protocol protocol, int typeCommunication) {
        this.speed = speed;
        this.protocol = protocol;
        this.typeCommunication = typeCommunication;
    }
    
    @Override
    public void run(){
        try{
            while(execute){
                if(typeCommunication==0){
                    protocol.sender();
                }
                else{
                    protocol.receiver();
                }
                sleep(speed);
            }
        }
        catch(InterruptedException e){
            System.out.println("Thread ERROR: " + e.getMessage());
        }
        
    }
    public void startProtocol(){
        if (!execute) {
            execute = true;
            start(); //thread method 
        }
    }
    public void finishProtocol(){
        if(execute){
            execute = false;
        }
    }
    
}
