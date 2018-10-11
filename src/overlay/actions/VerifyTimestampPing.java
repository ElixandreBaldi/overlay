/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class VerifyTimestampPing implements Action{
    private int time;
    
    private short target;
    
    public VerifyTimestampPing(int time, short target) {
        this.time = time;
        this.target = target;
    }

    public VerifyTimestampPing() {        
    }
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        if(CommonState.getIntTime() - time >= Utils.timestampLimit) {
            if(protocol.getTimestamp()[target] % 2 == 0) protocol.getTimestamp()[target]++;
            
            System.out.println("Nodo "+protocol.getCurrentId()+"   detectou falha no nodo: "+target+"        no start: "+time+"        no tempo: "+CommonState.getIntTime());
        } else {
            Utils.addVerifyTimestampPing(node, time, target);
        }
    }    

    public int getStartTime() {
        return time;
    }
}
