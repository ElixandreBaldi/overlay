/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import peersim.core.*;
/**
 *
 * @author elixandrebaldi
 */
public class FinalMessage {
    private int hopCounter;    
    
    private Node sender;

    FinalMessage(Node sender, int hopCounter) {
        this.hopCounter = hopCounter;
        this.sender = sender;
    }
    
    public int getHopCounter() {
        return hopCounter;
    }

    public Node getSender() {
        return sender;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }
}
