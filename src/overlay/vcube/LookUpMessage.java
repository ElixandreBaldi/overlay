/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.math.BigInteger;
import java.util.ArrayList;
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
    
    private ArrayList<Integer> visited;
    
    public LookUpMessage(Node sender, BigInteger targetId) {
        this.sender = sender;
        this.targetId = targetId;
        this.visited = new ArrayList<Integer>();
        visited.add(sender.getIndex());
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
    
    public boolean verifyVisited(int index) {        
        for(int i = 0; i < visited.size(); i++) {
            if(visited.get(i) == index) {                
                return true;
            }
        }
        
        visited.add(index);
        return false;
    }
}
