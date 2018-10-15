/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class ExecutePut implements Action{
    private byte[] hash;
    
    public ExecutePut(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);           
            return;
        }
        Utils.executePut(hash, node, protocol);
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
    
}
