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
import overlay.Utils;
import peersim.config.Configuration;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;
import overlay.actions.Action;
import overlay.actions.LockupAnswer;
import overlay.actions.Ping;
import overlay.actions.Put;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.edsim.EDSimulator;

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
        Ping foo = new Ping();        
        if(this.status || o.getClass().equals(foo.getClass())) {
            Action event = (Action) o;
            event.run(node, (VCubeProtocol) this);        
        } else {
            LookUp fooL = new LookUp();
            Put fooP = new Put();            
            
            if(o.getClass().equals(fooL.getClass())) {                
                fooL = (LookUp) o;
                VCubeProtocol protocol = (VCubeProtocol) Network.get(fooL.getSender()).getProtocol(Utils.pid);
                protocol.executeReLookup(fooL);
                                
                Utils.hitsLookup++;                
            }
            else if(o.getClass().equals(fooP.getClass())) {
                fooP = (Put) o;
                VCubeProtocol protocol = (VCubeProtocol) Network.get(fooP.getSender()).getProtocol(Utils.pid);
                protocol.executeRePut(fooP);
                
                Utils.hitsPut++;
            }
        }
    }
    
    public void executeReLookup(LookUp fooL) {
        short p = Utils.responsibleKey(fooL.getKey(), this.getTimestamp());
                
        EDSimulator.add(10, new LookUp(fooL.getSender(), fooL.getKey(), fooL.getStartTime()), Network.get(p), Utils.pid);
        //System.out.println("nodo: "+this.currentId+" status: "+this.status+"      timestamp: "+this.getTimestamp()[this.currentId]);
        //System.out.println("Nodo "+fooL.getSender()+" reeeeeenviando lookup para "+p+"                     startTime:"+fooL.getStartTime()+"   no tempo "+CommonState.getIntTime());
    }
    
    public void executeRePut(Put fooP) {
        short p = Utils.responsibleKey(fooP.getKey(), this.getTimestamp());
                
        EDSimulator.add(10, new Put(fooP.getSender(), fooP.getKey(), fooP.getStartTime()), Network.get(p), Utils.pid);
        //System.out.println("nodo: "+this.currentId+" status: "+this.status+"      timestamp: "+this.getTimestamp()[this.currentId]);
        //System.out.println("Nodo "+fooL.getSender()+" reeeeeenviando put para "+p+"                     startTime:"+fooP.getStartTime()+"   no tempo "+CommonState.getIntTime());
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        
        if(!status) this.setTimestamp(timestamp.length);                    
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
        Arrays.fill(this.timestamp, (short) 2);
        this.timestamp[currentId] = 0;
    }        

    public void printTimestamp() {
        for(int i = 0; i < timestamp.length; i++) System.out.print(" "+timestamp[i]+", ");
        
        System.out.println("");
    }
}
