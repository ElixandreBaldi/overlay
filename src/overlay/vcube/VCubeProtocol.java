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
import overlay.actions.ExecuteLookup;
import overlay.actions.ExecutePut;
import overlay.actions.FindEmptyVertex;
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
        
    public VCubeProtocol(String prefix) {
        this.prefix = prefix;        
        this.p = new Parameters();
        this.p.tid = Configuration.getPid(this.prefix + "." + PAR_TRANSPORT);        
    }   
    
    public void processEvent(Node node, int pid, Object o) {
        Ping foo = new Ping();        
        if(this.status || o.getClass().equals(foo.getClass())) {
            Action event = (Action) o;
            event.run(node, (VCubeProtocol) this);        
        } else {
            LookUp fooL = new LookUp();
            Put fooP = new Put();
            
            ExecuteLookup exL = new ExecuteLookup();
            ExecutePut exP = new ExecutePut();
            
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
            } else if(o.getClass().equals(exL.getClass())) {                
                Utils.countLookup++;
                Utils.countLookupFault++;
            } else if(o.getClass().equals(exP.getClass())) {
                Utils.countPuts++;
                Utils.countPutFault++;
            }
        }
    }
    
    public void executeReLookup(LookUp fooL) {
        short p = Utils.responsibleKey(fooL.getKey(), this.getTimestamp());                
        EDSimulator.add(5, new LookUp(fooL.getSender(), fooL.getKey(), fooL.getStartTime()), Network.get(p), Utils.pid);
    }
    
    public void executeRePut(Put fooP) {
        short p = Utils.responsibleKey(fooP.getKey(), this.getTimestamp());  
        EDSimulator.add(5, new Put(fooP.getSender(), fooP.getKey(), fooP.getStartTime()), Network.get(p), Utils.pid);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status, int ativator) {
        if(status && this.status) {            
            //recebeu no tempo 1, e mandou no tempo 2
            EDSimulator.add(2, new FindEmptyVertex(), Network.get(this.currentId), Utils.pid);
            //System.out.println("To ativo by: "+this.currentId+"   "+CommonState.getIntTime());
            Utils.countUpStatusTrue++;
        } else {
            this.status = status;
            if(status) {
                int time = CommonState.getIntTime();
                time++;
                //System.out.println("Nodo  "+ativator+"   ativou o nodo "+this.currentId+"      "+time);               
                if(Utils.networkFull() && VCubeCreate.scenario == 0) {
                    Utils.finish(time);
                }                
                
                if(VCubeCreate.scenario == 5 || VCubeCreate.scenario == 6) {
                    Utils.countUpQueue--;
                } 
                Utils.countStartNode++;
                //System.out.println("alguem entrou");
            }else {
                this.setTimestamp(timestamp.length);
                Utils.countExitNode++;
                //System.out.println("alguem saiu");
            }
        }                
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

    public short getCurrentId() {
        return currentId;
    }

    public void setCurrentId(short currentId) {
        this.currentId = currentId;
        
        this.status = true;
        
        if(VCubeCreate.nodosOk == -1) {      
            if(!(this.currentId == 0)) this.status = false;
        } else if(VCubeCreate.nodosOk == 0) {
            if(!(this.currentId < Network.size() / 2)) this.status = false;
        }
        
        //System.out.println(this.status);
        
    }
    
    public void setTimestamp(int size) {
        this.timestamp = new short[size];        
        if(VCubeCreate.scenario == 0) Arrays.fill(this.timestamp, (short) 1);
        else Arrays.fill(this.timestamp, (short) 2);
        this.timestamp[currentId] = 0;
    }        

    public void printTimestamp() {
        //for(int i = 0; i < timestamp.length; i++) System.out.print(" "+timestamp[i]+", ");
        
        //System.out.println("");
    }
}
