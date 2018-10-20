/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import overlay.Utils;
import overlay.actions.ExecutePing;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.edsim.EDSimulator;


public class ControlVCube implements Control {

    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlVCube(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {                
        for(int i = 0; i < Network.size(); i++) EDSimulator.add(0, new ExecutePing(), Network.get(i), pid);  
        Utils.countTestesVCube++;
        
        if((CommonState.getIntTime() - Utils.timeNewEvent ) > 100) {
            Utils.finish(CommonState.getIntTime() - 100);            
        }
        
        //System.out.println("hits put: "+Utils.hitsPut);
        //System.out.println("hits lookup: "+Utils.hitsLookup);
        //System.out.println("");
        return false;
    }
}
