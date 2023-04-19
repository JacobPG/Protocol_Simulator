/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package GUI;


import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;



/**
 *
 * @author yeico
 */
//Instance manner MyThread h = new MyThread(100); 
public class MyThread extends Thread{
    public String estado;
    public int speed;
    public boolean execute = false;
    private boolean paused = false;
    public JPanel protocolPanel;
    public JPanel framePanel;
    public String protocolName;
    

    public MyThread(int speed, JPanel protocolPanel, JPanel framePanel, String protocolName) {
        this.speed = speed;
        this.protocolPanel = protocolPanel;
        this.framePanel = framePanel;
        this.protocolName = protocolName;
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
                switch(protocolName){
                    case "Protocolo Utopia":
                        protocolPanel.remove(framePanel);
                        framePanel.setBounds(framePanel.getX()+10, framePanel.getY(), framePanel.getWidth(), framePanel.getHeight());
                        protocolPanel.add(framePanel,0);
                        protocolPanel.updateUI();
                        if(framePanel.getX()>=725){
                            execute=false;
                        }
                        break;
                    case "Protocolo StopAndWait":
                        protocolPanel.remove(framePanel);
                        moverFrame();
                        protocolPanel.add(framePanel,0);
                        protocolPanel.updateUI();
                        break;
                    case "Protocolo PAR":
                        protocolPanel.remove(framePanel);
                        moverFrame();
                        protocolPanel.add(framePanel,0);
                        protocolPanel.updateUI();
                        break;
                    case "Protocolo SlidingWindow":
                        protocolPanel.remove(framePanel);
                        moverFrame();
                        protocolPanel.add(framePanel,0);
                        protocolPanel.updateUI();
                        break;
                    case "Protocolo GOBACK":

                        break;
                    case "Protocolo SelectiveRepeat":

                        break;
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
            System.out.println(e.getMessage());
        }
        
    }
    
    public void moverFrame(){
        switch(estado){
            case "arriba":
                framePanel.setBounds(framePanel.getX()+10, framePanel.getY(), framePanel.getWidth(), framePanel.getHeight());
                if(framePanel.getX()>=725){
                    finish();
                }
                break;
            case "abajo":
                framePanel.setBounds(framePanel.getX()-10, framePanel.getY(), framePanel.getWidth(), framePanel.getHeight());
                if(framePanel.getX()<=205){
                    finish();
                }
                break;         
        }
        
    }
    public void startMyThread(){
        if (!execute) {
            execute = true;
            start(); //thread method 
        }
    }
    
    public void finish(){
        if(execute){
            execute = false;
        }
    }
    
    
}
