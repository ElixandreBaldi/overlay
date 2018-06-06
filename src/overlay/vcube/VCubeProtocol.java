/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.ArrayList;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeProtocol implements EDProtocol {
    
    private final String PAR_TRANSPORT = "transport";
    
    private String prefix;
    
    private Parameters p;
    
    private ArrayList<Node> neighbor;
        
    public VCubeProtocol(String prefix) {
        this.prefix = prefix;
        
        this.p = new Parameters();
        this.p.tid = Configuration.getPid(this.prefix + "." + PAR_TRANSPORT);
        
        this.neighbor = new ArrayList<Node>();
        
    }
    
    public void processEvent(Node node, int pid, Object event) {
        this.p.pid = pid;        
        
        if(event.getClass() == FinalMessage.class) {
            FinalMessage message = (FinalMessage) event;
            Node sender = message.getSender();
            System.out.println("Nodo "+node.getIndex()+" recebemos mensagem de nodo "+sender);
        }
    }
    
    public Object clone() {
        return new VCubeProtocol(this.prefix);
    }

    public ArrayList<Node> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(ArrayList<Node> neighbor) {
        this.neighbor = neighbor;
    }
    
    public void printNeighbor() {        
        for(int i = 0; i < neighbor.size(); i++) {
            System.out.print(""+neighbor.get(i).getIndex());
            if(i < neighbor.size() - 1) System.out.print(", ");
        }
        System.out.println("");
    }
}
