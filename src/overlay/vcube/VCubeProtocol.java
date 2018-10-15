/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import overlay.actions.FinalMessage;
import overlay.actions.LookUp;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;
import overlay.actions.Action;
import overlay.actions.LockupAnswer;
import overlay.actions.VerifyTimestampLookup;
import overlay.actions.VerifyTimestampPing;
import overlay.actions.VerifyTimestampPut;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeProtocol implements EDProtocol {
    
    private boolean status;
    
    private final String PAR_TRANSPORT = "transport";
    
    private short currentId;
    
    private String prefix;
    
    private Parameters p;
    
    private short[] timestamp;
    
    private List<Action> processQueue;
        
    public VCubeProtocol(String prefix) {
        this.prefix = prefix;        
        this.p = new Parameters();
        this.p.tid = Configuration.getPid(this.prefix + "." + PAR_TRANSPORT);                     
        this.processQueue = new LinkedList<>();
        this.status = true;
    }

    public VCubeProtocol(String prefix, short currentId, Parameters p, short[] timestamp){
        this.prefix = prefix;        
        this.currentId = currentId;
        this.p = p.clone();   
        this.timestamp = new short[timestamp.length];       
        for(int i = 0; i < timestamp.length; i++) this.timestamp[i] = timestamp[i];
        this.status = true;
    }
    
    public void processEvent(Node node, int pid, Object o) {
        if(status) {
            Action event = (Action) o;
            event.run(node, (VCubeProtocol) this, true);        
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public List<Action> getProcessQueue() {
        return this.processQueue;
    }
    
    public short[] getTimestamp() {
        return this.timestamp;
    }        
    
    public Parameters getP() {
        return this.p;
    }
    
    public Object clone() {                
        return new VCubeProtocol(prefix);        
    }
    
    public VCubeProtocol cloneVCube() {                
        return new VCubeProtocol(prefix, currentId, p, timestamp);        
    }  

    public short getCurrentId() {
        return currentId;
    }

    public void setCurrentId(short currentId) {
        this.currentId = currentId;
    }
    
    public void setTimestamp(int size) {
        this.timestamp = new short[size];        
        Arrays.fill(this.timestamp, (byte) 1);
        this.timestamp[currentId] = 0;
    }
    
    public void removeVerifyTimestamp(int startTime) {
        VerifyTimestampLookup foo = new VerifyTimestampLookup();
        VerifyTimestampPut fooo = new VerifyTimestampPut();
        VerifyTimestampPing foooo = new VerifyTimestampPing();
        for(int i = 0; i < processQueue.size(); i++) {            
            if(processQueue.get(i).getClass().equals(foo.getClass())) {
                VerifyTimestampLookup process = (VerifyTimestampLookup) processQueue.get(i);
                if(process.getStartTime() == startTime) {
                    processQueue.remove(i);
                    break;
                }
            } else if (processQueue.get(i).getClass().equals(fooo.getClass())) {
                VerifyTimestampPut process = (VerifyTimestampPut) processQueue.get(i);
                if(process.getStartTime() == startTime) {
                    processQueue.remove(i);
                    break;
                }
            } else if (processQueue.get(i).getClass().equals(foooo.getClass())) {
                VerifyTimestampPing process = (VerifyTimestampPing) processQueue.get(i);
                if(process.getStartTime() == startTime) {
                    processQueue.remove(i);                    
                    break;
                }
            }
        }
    }

    public void printTimestamp() {
        for(int i = 0; i < timestamp.length; i++) System.out.print(" "+timestamp[i]+", ");
        
        System.out.println("");
    }
}
