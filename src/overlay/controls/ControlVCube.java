/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.controls;

import overlay.actions.LookUp;

import java.math.BigInteger;
import overlay.Utils;
import overlay.actions.ExecuteProcess;
import overlay.actions.MessageExecuteVCube;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;


public class ControlVCube implements Control {

    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public ControlVCube(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {        
        for(int i = 0; i < Network.size(); i++){
            MessageExecuteVCube executeVCube = new MessageExecuteVCube();
            EDSimulator.add(1, executeVCube, Network.get(i), pid);                       
        }                
        
        return false;
    }
}
