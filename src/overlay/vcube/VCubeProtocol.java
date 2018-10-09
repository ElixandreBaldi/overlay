/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import overlay.message.FinalMessage;
import overlay.message.LookUp;
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
import overlay.message.Action;
import overlay.message.LockupAnswer;
import overlay.message.VerifyTimestampLookup;
import overlay.message.VerifyTimestampPut;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeProtocol implements EDProtocol {
    
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
    }

    public VCubeProtocol(String prefix, short currentId, Parameters p, short[] timestamp){
        this.prefix = prefix;        
        this.currentId = currentId;
        this.p = p.clone();   
        this.timestamp = new short[timestamp.length];       
        for(int i = 0; i < timestamp.length; i++) this.timestamp[i] = timestamp[i];
    }
    
    public void processEvent(Node node, int pid, Object o) {
        Action event = (Action) o;
        event.run(node, (VCubeProtocol) this, true);        
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
        for(int i = 0; i < processQueue.size(); i++) {
            VerifyTimestampLookup foo = new VerifyTimestampLookup();
            VerifyTimestampPut fooo = new VerifyTimestampPut();
            if(processQueue.get(i).getClass().equals(foo.getClass())) {
                VerifyTimestampLookup process = (VerifyTimestampLookup) processQueue.get(i);                
                if(process.getStartTime() == startTime) {                    
                    processQueue.remove(i);
                    System.out.println("Removendo");
                    break;
                }
            } else if (processQueue.get(i).getClass().equals(fooo.getClass())) {
                VerifyTimestampPut process = (VerifyTimestampPut) processQueue.get(i);
                if(process.getStartTime() == startTime) {                    
                    processQueue.remove(i);
                    System.out.println("Removendo");
                    break;
                }
            }
        }
    }
}
