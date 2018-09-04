/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.math.BigInteger;
import java.util.ArrayList;
import overlay.vcube.Parameters;
import overlay.vcube.VCubeProtocol;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */
public class LookUpMessage implements Message{
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

    @Override
    public void apply(Node node, Parameters p, ArrayList<Node> neighbor) {
        int pid = p.getPid();        
        this.increaseHop();
        BigInteger target = this.getTarget();
            
        Transport t = (Transport) node.getProtocol(p.getTid());
        Node sender = this.getSender();

        if(target != ((VCubeProtocol) node.getProtocol(pid)).getVCubeId()) { //não chegou no alvo                                                          
            for(int i = 0; i < neighbor.size(); i++) {
                if(!this.verifyVisited(neighbor.get(i).getIndex())) {
                    //System.out.println("Salto em "+node.getIndex());
                    t.send(this.getSender(), neighbor.get(i), this, pid);
                    break;
                }                                                                                     
            }
        } else { //chegou no alvo, fazer envio de confirmação de entrega                
            //System.out.println("Nodo "+node.getIndex()+" recebeu mensagem de nodo "+sender.getIndex());
            t.send(node, sender, new FinalMessage(node, this.getHopCounter()), pid);
        }
    }
}
