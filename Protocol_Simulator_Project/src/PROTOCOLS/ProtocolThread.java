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
    private boolean paused = false;
    private Utopia protocol;
    private int typeCommunication;

    public ProtocolThread(int speed, Utopia protocol, int typeCommunication) {
        this.speed = speed;
        this.protocol = protocol;
        this.typeCommunication = typeCommunication;
    }
    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resumeThread() {
        paused = false;
        notify();
    }
    @Override
    public void run(){
        try{
            while(execute){
                if(typeCommunication==0){
                    protocol.sender1();
                }
                else{
                    protocol.receiver1();
                }
                sleep(speed);
                synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    }
                }
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
