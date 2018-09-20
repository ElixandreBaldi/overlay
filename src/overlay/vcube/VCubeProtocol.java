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
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;
import overlay.message.Action;

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
    
    private int[] timestamp;
        
    public VCubeProtocol(String prefix) {
        this.prefix = prefix;        
        this.p = new Parameters();
        this.p.tid = Configuration.getPid(this.prefix + "." + PAR_TRANSPORT);                     
    }

    public VCubeProtocol(String prefix, BigInteger vcubeId, int currentId, Parameters p, int[] timestamp){
        this.prefix = prefix;
        this.vcubeId = vcubeId;
        this.currentId = currentId;
        this.p = p.clone();                
        this.timestamp = new int[timestamp.length];
        
        for(int i = 0; i < timestamp.length; i++) this.timestamp[i] = timestamp[i];

    }
    
    public void processEvent(Node node, int pid, Object o) {                
        Action event = (Action) o;
        this.p.pid = pid;
        event.run(node, (VCubeProtocol) this.cloneVCube());                   
    }
    
    public int[] getTimestamp() {
        return this.timestamp;
    }
    
    public Parameters getP() {
        return this.p;
    }
    
    public Object clone() {                
        return new VCubeProtocol(prefix);        
    }
    
    public VCubeProtocol cloneVCube() {                
        return new VCubeProtocol(prefix, vcubeId, currentId, p, timestamp);        
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
        this.timestamp[currentId] = 0;
    }
}
