/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import java.util.UUID;
import overlay.Utils;
import overlay.actions.ExecuteLookup;
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
        
        if(!Utils.flagLookup) return false;
        Utils.flagLookup = false;
        
        int countLookUpToEach = (int) ((Utils.nLookups / Network.size()) + 1);
        
        for(int j = 0; j < countLookUpToEach; j++) {
            for(int i = 0; i < Network.size(); i++) {
                byte[] hash = Utils.generateHash("lookup"+UUID.randomUUID().toString(), "SHA-256");        
                EDSimulator.add(j, new ExecuteLookup(hash), Network.get(i), Utils.pid);        
            }
        }
        
        return false;
    }
}
