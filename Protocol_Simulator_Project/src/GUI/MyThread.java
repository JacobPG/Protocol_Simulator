/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package GUI;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author yeico
 */
//Instance manner MyThread h = new MyThread(100); 
public class MyThread extends Thread{
    private int speed;
    private boolean execute = false;
    public JPanel mainPanel;
    public JPanel framePanel;
    public JFrameMain mainFrame;

    public MyThread(int speed, JPanel mainPanel, JPanel framePanel, JFrameMain mainFrame) {
        this.speed = speed;
        this.mainPanel = mainPanel;
        this.framePanel = framePanel;
        this.mainFrame = mainFrame;
    }
    
    @Override
    public void run(){
        try{
            while(execute){
                System.out.println("animacion");
//                int x = framePanel.getBounds().x;
//                framePanel.setBounds(x+10, framePanel.getBounds().y, framePanel.getBounds().width, framePanel.getBounds().height);
//                mainPanel.updateUI();
                mainFrame.setBackground(Color.yellow);
                mainFrame.repaint();
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
