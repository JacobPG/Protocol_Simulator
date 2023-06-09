/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import PROTOCOLS.GoBack;
import PROTOCOLS.PAR;
import PROTOCOLS.Protocol;
import PROTOCOLS.ProtocolThread;
import PROTOCOLS.SelectiveRepeat;
import PROTOCOLS.SlidingWindow;
import PROTOCOLS.StopAndWait;
import PROTOCOLS.Utopia;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author yeico
 */
public class JFrameMain extends javax.swing.JFrame {
    private ImageIcon img = new ImageIcon();
    private Icon icon = new Icon() {
        @Override
        public void paintIcon(Component cmpnt, Graphics grphcs, int i, int i1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getIconWidth() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getIconHeight() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    private int number = 0;
    private JButton nextButton = new JButton();
    private JButton backButton = new JButton();
    private JButton startButton;
    private JPanel panel = new JPanel();
    private MyThread thread;
    private int index = 0; //Determinate the Jframe that use our Project
    
    
    private JFrame[] frameProtocols = {new JFrameUtopia(), new JFrameStopAndWait(), new JFramePAR(), 
        new JFrameSlidingWindow(), new JFrameGoBack(), new JFrameSelectiveRepeat()};
    //private Utopia utopia;
    private Protocol protocol = new Protocol();
    private ProtocolThread protocolSender;
    private ProtocolThread protocolReciever;

    public JFrameMain() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        initComponents();
        this.setTitle("Protocol Simulator");
        this.setSize(1300,700);
        this.setLocationRelativeTo(null);
        
        
          
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        panel.setBackground(Color.white);
        backButton.setBounds(0, 0, 100, 100);
        nextButton.setBounds(0, 0, 100, 100);
        
        for (int i = 0; i < frameProtocols.length; i++) {
            frameProtocols[i].add(createFrameProtocol(frameProtocols[i].getClass().getDeclaredField("name").get(frameProtocols[i]).toString()));
        }
        
        
        //protocolPanel.add(frameProtocols[index].getContentPane());
        
        //PANEL VENTANA PRINCIPAL
        panel.add(backButton);
        panel.add(frameProtocols[index].getContentPane());
        panel.add(nextButton);
      
        //----------------------------------------
        img = new ImageIcon(getClass().getResource("/Images/next-arrow.png"));
        icon = new ImageIcon(img.getImage().getScaledInstance(nextButton.getWidth(),nextButton.getHeight(),Image.SCALE_AREA_AVERAGING));
        nextButton.setIcon(icon);
        
        img = new ImageIcon(getClass().getResource("/Images/back-arrow.png"));
        icon = new ImageIcon(img.getImage().getScaledInstance(backButton.getWidth(),backButton.getHeight(),Image.SCALE_AREA_AVERAGING));
        backButton.setIcon(icon);
        //--------------------------------
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!startButton.isEnabled()){
                        startButton.setEnabled(true);
                        stopSimulation();
                        finishSimulation();
                    }
                    panel.remove(frameProtocols[index].getContentPane());
                    panel.remove(backButton);
                    panel.remove(nextButton);
                    
                    index = (index - 1 + frameProtocols.length) % frameProtocols.length;
                    panel.add(backButton);
                    panel.add(frameProtocols[index].getContentPane());
                    System.out.println("--------"+frameProtocols[index].getClass().getDeclaredField("name").get(frameProtocols[index]).toString()+"--------");
                    panel.add(nextButton);
                    panel.updateUI();
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        System.out.println("--------"+frameProtocols[index].getClass().getDeclaredField("name").get(frameProtocols[index]).toString()+"--------");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!startButton.isEnabled()){
                        startButton.setEnabled(true);
                        stopSimulation();
                        finishSimulation();
                    }
                    panel.remove(frameProtocols[index].getContentPane());
                    panel.remove(backButton);
                    panel.remove(nextButton);
                    index = (index + 1) % frameProtocols.length;
                    panel.add(backButton);
                    panel.add(frameProtocols[index].getContentPane());
                    System.out.println("--------"+frameProtocols[index].getClass().getDeclaredField("name").get(frameProtocols[index]).toString()+"--------");
                    panel.add(nextButton);
                    panel.updateUI();
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //-------------------------------------------------
        this.add(panel);
        setResizable(false);
    }
    public JPanel createFrameProtocol(String nameProtocol){
        JPanel protocolPanel = new JPanel(){
            //Draw the line
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(10));
                g2.drawLine(210,120, 770,120);
            };
        };
        JLabel titleLabel = new JLabel(nameProtocol);
        Font letra = new Font("Arial", Font.BOLD, 16);
        titleLabel.setFont(letra);
        titleLabel.setBounds(400, 50, 200, 30);
        
        JPanel framePanel = new JPanel(null);
        framePanel.setBackground(Color.GRAY);
        framePanel.setBounds(205, 125, 50, 25);
        
        JPanel framePanel2 = new JPanel(null);
        framePanel2.setBackground(Color.GRAY);
        framePanel2.setBounds(725, 125, 50, 25);
        
        JPanel framePanel3 = new JPanel(null);
        framePanel3.setBackground(Color.BLUE);
        framePanel3.setBounds(205, 125, 50, 25);
        framePanel3.setVisible(false);
        
        JPanel buttonsPanel = new JPanel(null);
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBounds(720, 300, 250, 320);
        
        

        JPanel labelsPanel = new JPanel(null);
        labelsPanel.setBackground(Color.WHITE);
        labelsPanel.setBounds(30, 300, 600, 320);
  
//        for (int i = 0; i < getLabelsInfo(nameProtocol).size(); i++) {
//            labelsPanel.add(getLabelsInfo(nameProtocol).get(i));
//        }
        ArrayList<Component> labels = getLabelsInfo(nameProtocol);
        //System.out.println(labels.size());
        //labelsPanel.add(labels.get(0));
        for (int i = 0; i < labels.size(); i++) {
            //System.out.println(labels.size() + i);
            labelsPanel.add(labels.get(i));
        }
        
        JLabel machineA = new JLabel();
        machineA.setBounds(100,30,100,100);
        img = new ImageIcon(getClass().getResource("/Images/machineB.png"));
        icon = new ImageIcon(img.getImage().getScaledInstance(machineA.getWidth(),machineA.getHeight(),Image.SCALE_AREA_AVERAGING));
        machineA.setIcon(icon);
        
        JLabel machineB = new JLabel();
        machineB.setBounds(780,30,100,100);
        img = new ImageIcon(getClass().getResource("/Images/machineB.png"));
        icon = new ImageIcon(img.getImage().getScaledInstance(machineB.getWidth(),machineB.getHeight(),Image.SCALE_AREA_AVERAGING));
        machineB.setIcon(icon);
  
        
        startButton = new JButton("Start simulation");
        startButton.setBounds(30, 10, 120, 70);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startButton.setEnabled(false); // Deshabilitar el botón
                startSimulation(protocolPanel, framePanel3, nameProtocol);
            }
        });
        
        JButton stopButton = new JButton("Stop simulation");
        stopButton.setBounds(30,110,120,70);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopSimulation();
            }
        });
        JButton resumeButton = new JButton("To resume simulation");
        resumeButton.setBounds(30,210,120,70);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                toResumeSimulation();
            }
        });
        
        buttonsPanel.add(startButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(resumeButton);
        
        
        protocolPanel.setLayout(null);
        protocolPanel.setBounds(10, 10, frameProtocols[0].getWidth()-35, frameProtocols[0].getHeight()-60);
        protocolPanel.setBackground(Color.white);
        protocolPanel.add(titleLabel);
        protocolPanel.add(framePanel3);
        protocolPanel.add(framePanel);
        protocolPanel.add(framePanel2);
        
        protocolPanel.add(machineA);
        protocolPanel.add(machineB);
        protocolPanel.add(buttonsPanel);
        protocolPanel.add(labelsPanel);
        return protocolPanel;
    }
    
    public void startSimulation(JPanel refProtocolPanel, JPanel refFramePanel3, String protocolName){
        refFramePanel3.setBounds(205, 125, 50, 25);
        refFramePanel3.setVisible(true);
        switch(protocolName){
            case "Protocolo Utopia":
                protocol = new Utopia();
                break;
            case "Protocolo StopAndWait":
                protocol = new StopAndWait();
                break;
            case "Protocolo PAR":
                protocol = new PAR();
                break;
            case "Protocolo SlidingWindow":
                protocol = new SlidingWindow();
                break;
            case "Protocolo GOBACK":
                protocol = new GoBack();
                break;
            case "Protocolo SelectiveRepeat":
                protocol = new SelectiveRepeat();
                break;
        }
        protocol.setErrorRate(number);
        protocol.graphicThread = new MyThread(50, refProtocolPanel, refFramePanel3, protocolName);
        protocolSender = new ProtocolThread(100, protocol,0);
        protocolSender.startProtocol();
        protocolReciever = new ProtocolThread(100, protocol,1);
        protocolReciever.startProtocol();
    }
    
    public ArrayList<Component> getLabelsInfo(String protocolName){
        ArrayList<Component> list = new ArrayList<>();
        switch(protocolName){
        case "Protocolo Utopia":
            list = utopiaLabels();
            break;
        case "Protocolo StopAndWait":
            list = stopAndWaitLabels();
            break;
        case "Protocolo PAR":
            list = parLabels();
            break;
        case "Protocolo SlidingWindow":
            return slidingWindowLabels();
        case "Protocolo GOBACK":
            return goBackLabels();
        case "Protocolo SelectiveRepeat":
            return selectiveRepeatLabels();
        }
        return list;
    }
    public ArrayList<Component> utopiaLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        JLabel label1 = new JLabel(); //datos no recibidos
        JLabel label2 = new JLabel(); //datos listos para enviar
        JLabel label3 = new JLabel(); //los datos se han entregado a la capa superior
        JPanel label4 = new JPanel(); //datos no recibidos
        JPanel label5 = new JPanel(); //datos listos para enviar
        JPanel label6 = new JPanel(); //los datos se han entregado a la capa superior
        
        
        label1.setText("No existe frame");
        label2.setText("Frame enviado");
        label3.setText("Frame recibido");
        label4.setBackground(Color.GRAY);
        label5.setBackground(Color.blue);
        label6.setBackground(Color.GREEN);
        
        label1.setBounds(55, 0, 100, 40);
        label2.setBounds(55, 40, 100, 40);
        label3.setBounds(55, 80, 100, 40);
        label4.setBounds(10, 0, 40, 30);
        label5.setBounds(10, 40, 40, 30);
        label6.setBounds(10, 80, 40, 30);
        
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        return labels;
    }
    public ArrayList<Component> stopAndWaitLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        labels = (ArrayList<Component>) utopiaLabels().clone();
        JLabel label1 = new JLabel();
        JPanel label2 = new JPanel();
        label1.setText("Permiso del receptor");
        label2.setBackground(new Color(64,207,255));
        label1.setBounds(55, 120, 140, 40);
        label2.setBounds(10, 120, 40, 30);
        labels.add(label1);
        labels.add(label2);
        return labels;
    }
    
    public ArrayList<Component> parLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        labels = (ArrayList<Component>) stopAndWaitLabels().clone();
        JLabel labelTimer = new JLabel();
        JLabel labelLatencia = new JLabel();
        protocol.timerSec(labelTimer);
        //labelTimer.setText("timer: " + protocol.timerSec() + "s");
        int time = protocol.waitTime/1000;
        labelLatencia.setText("Latencia maxima: " + time + "s");
        labelTimer.setBounds(155, 0, 100, 40);
        labelLatencia.setBounds(155, 40, 200, 40);
        
        
        
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        JPanel label4 = new JPanel();
        JPanel label5 = new JPanel();
        JTextField label6 = new JTextField();
        JButton label7 = new JButton();
        label1.setText("TIMEOUT");
        label2.setText("cksum_err");
        label3.setText("Tasa de errores: ");
        label7.setText("Aceptar");
        label4.setBackground(Color.RED);
        label5.setBackground(Color.YELLOW);
        label6.setText("0");
        
        label1.setBounds(55, 160, 100, 40);
        label2.setBounds(55, 200, 100, 40);
        label3.setBounds(55, 240, 100, 40);
        label4.setBounds(10, 160, 40, 30);
        label5.setBounds(10, 200, 40, 30);
        label6.setBounds(200, 240, 100, 30);
        label7.setBounds(300, 240, 100, 30);
        label7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(label6.getText().length()==0 || Integer.valueOf(label6.getText())> 100 || Integer.valueOf(label6.getText())<0){
                    number = 0;
                }
                else{
                    number = Integer.valueOf(label6.getText());
                }
                
            }
        });
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        labels.add(label7);
        labels.add(labelLatencia);
        labels.add(labelTimer);
        return labels;
    }
    
    public ArrayList<Component> slidingWindowLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        labels = (ArrayList<Component>) parLabels().clone();
        return labels;
    }
    public ArrayList<Component> goBackLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        return labels;
    }
    public ArrayList<Component> selectiveRepeatLabels(){
        ArrayList<Component> labels = new ArrayList<>();
        return labels;
    }
    
    public void stopSimulation(){
        System.out.println("stop");
        protocol.graphicThread.suspend();
        protocolSender.suspend();
        protocolReciever.suspend();
    }
    public void toResumeSimulation(){
        System.out.println("to resume");
        protocol.graphicThread.resume();
        protocolSender.resume();
        protocolReciever.resume();
    }
    public void finishSimulation(){
        protocol.running = false;
        protocol.graphicThread.finish();
        protocolSender.finishProtocol();
        protocolReciever.finishProtocol();
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(200, 400));
        setSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new JFrameMain().setVisible(true);
                } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


