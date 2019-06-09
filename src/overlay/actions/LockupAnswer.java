/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import overlay.Utils;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class LockupAnswer implements Action{
    private short sender;
    private boolean lookupTrue;
    private byte[] hash;
    private int startTime;
    
    public LockupAnswer(short sender, boolean lookupTrue, byte[] hash, int startTime) {
        this.sender = sender;
        this.lookupTrue = lookupTrue;
        this.hash = hash;
        this.startTime = startTime;
    }

    public LockupAnswer() {        
    }

    @Override
    public void run(Node node, VCubeProtocol protocol) {
        if(lookupTrue) {
            //if(CommonState.getIntTime() - startTime > 3) 
            //System.out.println("Nodo: "+node.getIndex()+"   Confiramação de LookUp Recebida de: "+this.sender+"    startTime: "+startTime+"   time:"+CommonState.getIntTime());            
            long dif = CommonState.getIntTime() - startTime;
            Utils.sumTimeLookup += dif;
            Utils.countLookup++;
            Utils.timeNewEvent = CommonState.getIntTime();
            
            if( (VCubeCreate.scenario == 2 || VCubeCreate.scenario == 3) && Utils.countLookup >= Utils.nLookups) {
                Utils.finish(CommonState.getIntTime());
            }
            
            int limit = (int) (Utils.nLookups * 0.9);
            if((VCubeCreate.scenario == 6 || VCubeCreate.scenario == 8) && Utils.countLookup >= limit) {
                Utils.finish(CommonState.getIntTime());
            }
            
        } else {
            Utils.countReLookup++;
            Utils.executeReLookup(hash, node, protocol);
        }   
        
        
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}