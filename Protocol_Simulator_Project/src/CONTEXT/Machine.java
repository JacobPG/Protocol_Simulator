/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTEXT;

import PROTOCOLS.Protocol;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author yeico
 */
public class Machine {
    public String name;
    public int packetsSent;
    public int packetsRecive;

    public Machine(String name, int packetsSent, int packetsRecive) {
        this.name = name;
        this.packetsSent = packetsSent;
        this.packetsRecive = packetsRecive;
    }
}
