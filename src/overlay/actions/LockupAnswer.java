/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import overlay.Utils;
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

    @Override
    public void run(Node node, VCubeProtocol protocol) {
        if(lookupTrue) {
            if(CommonState.getIntTime() - startTime > 3) System.out.println("Nodo: "+node.getIndex()+"   Confiramação de LookUp Recebida de: "+this.sender+"    startTime: "+startTime+"   time:"+CommonState.getIntTime());            
        } else {
            Utils.executeLookup(hash, node, protocol);
        }   
        
        
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}