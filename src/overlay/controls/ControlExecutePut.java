/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import java.util.UUID;
import overlay.Utils;
import overlay.actions.ExecuteLookup;
import overlay.actions.ExecutePut;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandrebaldi
 */
public class ControlExecutePut implements Control {
    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlExecutePut(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {                        
        byte[] hash = Utils.generateHash("put"+UUID.randomUUID().toString(), "SHA-256");                
        EDSimulator.add(1, new ExecutePut(hash), Utils.getRandomNode(), pid);
              
        return false;
    }
}
