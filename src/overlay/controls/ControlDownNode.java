/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import overlay.Utils;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;

/**
 *
 * @author elixandre
 */
public class ControlDownNode implements Control{
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlDownNode(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {
        if(VCubeCreate.scenario == 3 || VCubeCreate.scenario == 4) {
            if(!Utils.flagDown) return false;
            int index = Network.size() / 2;
            VCubeProtocol target = (VCubeProtocol) Network.get(index).getProtocol(pid);
            target.setStatus(false, target.getCurrentId());
            Utils.flagDown = false;
        } else {
            if(Utils.canDown()) {
                //Utils.flagDown = false;
                int size = Network.size();
                VCubeProtocol target = null;
                do {                
                    target = (VCubeProtocol) Network.get(CommonState.r.nextInt(size)).getProtocol(pid);
                } while (target == null || !target.getStatus());            
                target.setStatus(false, target.getCurrentId());
                //System.out.println("saiu: "+target.getCurrentId()+"    time: "+CommonState.getIntTime());            
            }
        }
        return false;
    }
}
