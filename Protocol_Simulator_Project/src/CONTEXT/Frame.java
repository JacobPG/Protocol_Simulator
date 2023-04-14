/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTEXT;

/**
 *
 * @author yeico
 */
public class Frame {
    public FrameKindEnum frameKind; /* what kind of frame is it? */
    public int seq; /* sequence number */ 
    public int ack; /* acknowledgement number seq_nr*/
    public Packet info; /* the network layer packet seq_nr*/

    
}
