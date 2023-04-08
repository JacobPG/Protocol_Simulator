/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROTOCOLS;

import CONTEXT.Frame;
import CONTEXT.Packet;

/**
 *
 * @author yeico
 */
public class Protocol {
    Frame frame;
    int MAX_SEQ;
    EventTypeEnum eventType;
    int seq_nr;
    Packet packet;

    public Protocol(Frame frame, int MAX_SEQ, EventTypeEnum eventType, int seq_nr, Packet packet) {
        this.frame = frame;
        this.MAX_SEQ = MAX_SEQ;
        this.eventType = eventType;
        this.seq_nr = seq_nr;
        this.packet = packet;
    }
    /* Wait for an event to happen; return its type in event. */
    public void wait_for_event(EventTypeEnum event) {
        // implementación de la función
    }

    /* Fetch a packet from the network layer for transmission on the channel. */
    public void from_network_layer(Packet p) {
        p.information = packet.information;
    }

    /* Deliver information from an inbound frame to the network layer. */
    public void to_network_layer(Packet p) {
        // implementación de la función
    }

    /* Go get an inbound frame from the physical layer and copy it to r. */
    public void from_physical_layer(Frame r) {
        // implementación de la función
    }

    /* Pass the frame to the physical layer for transmission. */
    public void to_physical_layer(Frame s) {
        // implementación de la función
    }

    /* Start the clock running and enable the timeout event. */
    public void start_timer(int k) { //seq_nr
        // implementación de la función
    }

    /* Stop the clock and disable the timeout event. */
    public void stop_timer(int k) { //seq_nr
        // implementación de la función
    }

    /* Start an auxiliary timer and enable the ack timeout event. */
    public void start_ack_timer() {
        // implementación de la función
    }

    /* Stop the auxiliary timer and disable the ack timeout event. */
    public void stop_ack_timer() {
        // implementación de la función
    }

    /* Allow the network layer to cause a network layer ready event. */
    public void enable_network_layer() {
        // implementación de la función
    }

    /* Forbid the network layer from causing a network layer ready event. */
    public void disable_network_layer() {
        // implementación de la función
    }
    
}
