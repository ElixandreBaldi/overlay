/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import java.util.UUID;
import overlay.Utils;
import overlay.actions.ExecuteLookup;
import overlay.actions.ExecuteProcess;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandrebaldi
 */
public class ControlExecuteLookup implements Control {
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlExecuteLookup(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {                        
        byte[] hash = Utils.generateHash("lookup"+UUID.randomUUID().toString(), "SHA-256");
        //System.out.println(Utils.stringHexa(hash));
        
        ExecuteLookup message = new ExecuteLookup(hash);
        EDSimulator.add(50, message, Utils.getRandomNode(), pid);  
        /*if(VCubeCreate.flag1) {
            EDSimulator.add(5, message, Network.get(3), pid);     
            VCubeCreate.flag1 = false;
        } */       
        return false;
    }
}
