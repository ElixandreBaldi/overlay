/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class ExecuteLookup implements Action{
    private byte[] hash;        
    
    public ExecuteLookup(byte[] hash) {
        this.hash = hash;
    }       

    public ExecuteLookup() {        
    }
    
    @Override
    public void run(Node node, VCubeProtocol protocol) {     
        if(protocol.getStatus()) {
            Utils.executeLookup(hash, node, protocol);
        } else {
            int size = Network.size();
            VCubeProtocol target = null;
            do {                
                target = (VCubeProtocol) Network.get(CommonState.r.nextInt(size)).getProtocol(Utils.pid);
            } while (target == null || !target.getStatus());
            
            EDSimulator.add(0, new ExecuteLookup(hash), Network.get(target.getCurrentId()), Utils.pid);        
        }
        
        
    }

    @Override
    public int getStartTime() {
        return -1;
    }
}
