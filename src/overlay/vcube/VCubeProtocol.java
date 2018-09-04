/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import overlay.message.FinalMessage;
import overlay.message.LookUpMessage;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import overlay.message.Message;
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
    
    private int[] timestamp;
        
    public VCubeProtocol(String prefix) {
        this.prefix = prefix;
        
        this.p = new Parameters();
        this.p.tid = Configuration.getPid(this.prefix + "." + PAR_TRANSPORT);
        
        this.neighbor = new ArrayList<Node>();
        
    }
    
    public void processEvent(Node node, int pid, Object o) {
        Message event = (Message) o;
        this.p.pid = pid;        
        event.apply(node, this.p, this.neighbor);                      
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
    
    public void setTimestamp(int size) {
        this.timestamp = new int[size];        
        Arrays.fill(this.timestamp, 1);
    }    
    
    public void printNeighbor() {        
        for(int i = 0; i < neighbor.size(); i++) {
            System.out.print(""+neighbor.get(i).getIndex());
            if(i < neighbor.size() - 1) System.out.print(", ");
        }
        System.out.println("");
    }   
}
