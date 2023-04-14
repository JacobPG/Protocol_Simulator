/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    
    private JButton nextButton = new JButton();
    private JButton backButton = new JButton();
    private JPanel panel = new JPanel();
    private MyThread thread;
    private int index = 0; //Determinate the Jframe that use our Project
    
    
    private JFrame[] frameProtocols = {new JFrameUtopia(), new JFrameStopAndWait(), new JFramePAR(), 
        new JFrameSlidingWindow(), new JFrameGoBack(), new JFrameSelectiveRepeat()};

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
                panel.remove(frameProtocols[index].getContentPane());
                panel.remove(backButton);
                panel.remove(nextButton);
                
                index = (index - 1 + frameProtocols.length) % frameProtocols.length;
                panel.add(backButton);
                panel.add(frameProtocols[index].getContentPane());
                panel.add(nextButton);
                panel.updateUI();
            }

        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(frameProtocols[index].getContentPane());
                panel.remove(backButton);
                panel.remove(nextButton);
                index = (index + 1) % frameProtocols.length;
                panel.add(backButton);
                panel.add(frameProtocols[index].getContentPane());
                panel.add(nextButton);
                panel.updateUI();
            }
        });
        //-------------------------------------------------
        this.add(panel);
        setResizable(false);
    }
    public JPanel createFrameProtocol(String nombre){
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
        JLabel titleLabel = new JLabel(nombre);
        Font letra = new Font("Arial", Font.BOLD, 16);
        titleLabel.setFont(letra);
        titleLabel.setBounds(400, 50, 200, 30);
        
        JPanel framePanel = new JPanel(null);
        framePanel.setBackground(Color.yellow);
        framePanel.setBounds(205, 125, 50, 25);
        
        JPanel framePanel2 = new JPanel(null);
        framePanel2.setBackground(Color.yellow);
        framePanel2.setBounds(725, 125, 50, 25);
        
        JPanel framePanel3 = new JPanel(null);
        framePanel3.setBackground(Color.red);
        framePanel3.setBounds(205, 125, 50, 25);
        
        JPanel buttonsPanel = new JPanel(null);
        buttonsPanel.setBackground(Color.yellow);
        buttonsPanel.setBounds(720, 300, 250, 320);
        
        
        JPanel labelsPanel = new JPanel(null);
        labelsPanel.setBackground(Color.yellow);
        labelsPanel.setBounds(30, 300, 600, 320);
        
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
  
        
        JButton startButton = new JButton("Start simulation");
        startButton.setBounds(30, 10, 120, 70);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startButton.setEnabled(false); // Deshabilitar el botón
                startSimulation(protocolPanel, framePanel3, startButton);
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
    
    public void startSimulation(JPanel refProtocolPanel, JPanel refFramePanel3,JButton startButton){
        refFramePanel3.setBounds(205, 125, 50, 25);
        thread = new MyThread(100, refProtocolPanel, refFramePanel3, startButton);
        thread.startMyThread();
        
        //sender ->consola
        /*Sending packet [Sender]
Info in frame: 0110101000010110*/
        //Cambia la interfaz
        //MyThread hilo = new MyThread(100); //se ejecuta la animación
        //hilo.start();
        //reciver  ->consola
        /*Recieved frame: type = [data] | info = [0110101000010110]*/
        //se cambia interfaz
        // se vuelve cambiar interfaz nuetro
    }
    public void stopSimulation(){
        System.out.println("stop");
        thread.finish();
        
        
    }
    public void toResumeSimulation(){
        System.out.println("to resume");
        thread.startMyThread();
        
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


