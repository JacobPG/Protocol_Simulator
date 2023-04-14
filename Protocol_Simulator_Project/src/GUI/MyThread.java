/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package GUI;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;



/**
 *
 * @author yeico
 */
//Instance manner MyThread h = new MyThread(100); 
public class MyThread extends Thread{
    private int speed;
    private boolean execute = false;
    private JPanel protocolPanel;
    private JPanel framePanel;
    private JButton startButton;

    public MyThread(int speed, JPanel protocolPanel, JPanel framePanel, JButton startButton) {
        this.speed = speed;
        this.protocolPanel = protocolPanel;
        this.framePanel = framePanel;
        this.startButton = startButton;
    }
    
    @Override
    public void run(){
        try{
            while(execute){
                protocolPanel.remove(framePanel);
                framePanel.setBounds(framePanel.getX()+10, framePanel.getY(), framePanel.getWidth(), framePanel.getHeight());
                protocolPanel.add(framePanel,0);
                protocolPanel.updateUI();
                if(framePanel.getX()>=725){
                    execute=false;
                    startButton.setEnabled(true);
                }
                sleep(speed);
            }
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
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
