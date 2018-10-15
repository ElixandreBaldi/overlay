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
class ExecutePing implements Action{
    private short target;
    
    public ExecutePing(short target) {
        this.target = target;
    }        

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);           
            return;
        }
        int time = CommonState.getIntTime();
        //System.out.println("Nodo "+protocol.getCurrentId()+" mandando ping para "+target+"    "+time);
        
        Utils.send(
                protocol.getCurrentId(),
                target, 
                node.getProtocol(protocol.getP().getTid()), 
                new Ping(protocol.getCurrentId(), time));
        
        Utils.addVerifyTimestampPing(protocol, node, time, target);
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
