/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.math.BigInteger;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class LookUpMessage {
    private Node sender;
    
    private BigInteger targetId;
    
    private int hopCounter = -1;
    
    public LookUpMessage(Node sender, BigInteger targetId) {
        this.sender = sender;
        this.targetId = targetId;
    }
    
    public void increaseHop() {
        this.hopCounter++;
    }
    
    public Node getSender() {
        return sender;
    }
    
    public BigInteger getTarget() {
        return targetId;
    }
    
    public int getHopCounter() {
        return hopCounter;
    }
}
