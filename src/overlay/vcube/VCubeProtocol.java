/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.math.BigInteger;
import java.util.ArrayList;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeProtocol implements EDProtocol {
    
    private final String PAR_TRANSPORT = "transport";
    
    private BigInteger vcubeId;
    
    private int currentId;
    
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
            //System.out.println("Nodo "+node.getIndex()+" recebeu confirmação de entrega de "+sender.getIndex());
        } else if(event.getClass() == LookUpMessage.class) {
            LookUpMessage message = (LookUpMessage) event;
            message.increaseHop();
            BigInteger target = message.getTarget();
            
            Transport t = (Transport) node.getProtocol(p.tid);
            Node sender = message.getSender();
            
            if(target != ((VCubeProtocol) node.getProtocol(pid)).getVCubeId()) { //não chegou no alvo                                                          
                for(int i = 0; i < neighbor.size(); i++) {
                    if(!message.verifyVisited(neighbor.get(i).getIndex())) {
                        //System.out.println("Salto em "+node.getIndex());
                        t.send(message.getSender(), neighbor.get(i), message, pid);
                        break;
                    }                                                                                     
                }
            } else { //chegou no alvo, fazer envio de confirmação de entrega                
                //System.out.println("Nodo "+this.currentId+" recebeu mensagem de nodo "+sender.getIndex());
                t.send(node, sender, new FinalMessage(node, message.getHopCounter()), pid);
            }
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

    public BigInteger getVCubeId() {
        return vcubeId;
    }

    public void setVCubeId(BigInteger vcubeId) {
        this.vcubeId = vcubeId;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }
    
    
    
    public void printNeighbor() {        
        for(int i = 0; i < neighbor.size(); i++) {
            System.out.print(""+neighbor.get(i).getIndex());
            if(i < neighbor.size() - 1) System.out.print(", ");
        }
        System.out.println("");
    }
}
