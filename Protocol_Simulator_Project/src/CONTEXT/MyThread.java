/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package CONTEXT;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yeico
 */
//Instance manner MyThread h = new MyThread(100); 
public class MyThread extends Thread{
    private int speed;
    private boolean execute = false; 

    public MyThread(int speed) {
        this.speed = speed;
    }
    @Override
    public void run(){
        try{
            while(execute){
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
