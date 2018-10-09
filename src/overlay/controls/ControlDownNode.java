/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import overlay.Utils;
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
        if(Utils.countNodeDown < Network.size()) {
            int size = Network.size();
            VCubeProtocol target = null;
            do {                
                target = (VCubeProtocol) Network.get(CommonState.r.nextInt(size)).getProtocol(pid);
            } while (target == null || !target.getStatus());            
            target.setStatus(false);
            System.out.println("saiu: "+target.getCurrentId());
            Utils.countNodeDown++;
        }
        return false;
    }
}
