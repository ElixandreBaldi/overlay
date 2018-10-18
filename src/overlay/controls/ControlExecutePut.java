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
        if(!Utils.flagPut) return false;
        Utils.flagPut = false;
        
        int countPutToEach = (int) ((Utils.nPuts / Network.size()) + 1);
        
        for(int j = 0; j < countPutToEach; j++) {
            for(int i = 0; i < Network.size(); i++) {
                byte[] hash = Utils.generateHash("put"+UUID.randomUUID().toString(), "SHA-256");                
                EDSimulator.add(j, new ExecutePut(hash), Network.get(i), pid);                
            }
        }
              
        return false;
    }
}
