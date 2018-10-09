/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class VerifyTimestampPut implements Action{
    private int startTime;    
    private byte[] hash;    

    public VerifyTimestampPut(int startTime, byte[] hash) {
        this.startTime = startTime;        
        this.hash = hash;
    }

    public VerifyTimestampPut() {    
    }    
    
    public int getStartTime() {
        return startTime;
    }
    
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);
            return;
        }        
        if(CommonState.getIntTime() - startTime >= 100) {            
            Utils.executePut(hash, node, protocol);
        } else {
            Utils.addVerifyTimestampPut(hash, node, startTime);
        }
    }
    
}
