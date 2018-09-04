/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.ArrayList;
import overlay.vcube.Parameters;
import peersim.core.*;
/**
 *
 * @author elixandrebaldi
 */
public class FinalMessage implements Message{
    private int hopCounter;    
    
    private Node sender;

    public FinalMessage(Node sender, int hopCounter) {
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

    public void apply(Node node, Parameters p, ArrayList<Node> neighbor) {                
        //System.out.println("Nodo "+node.getIndex()+" recebeu confirmação de entrega de "+this.sender.getIndex());
    }
}
