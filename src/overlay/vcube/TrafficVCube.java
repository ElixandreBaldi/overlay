/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import overlay.message.LookUpMessage;
import java.math.BigInteger;
import overlay.message.MessageExecuteVCube;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandrebaldi
 */
public class TrafficVCube implements Control {

    private static final String PAR_PROT = "protocol";
    
    private final int pid;     
    
    public TrafficVCube(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);                                 
    }        
    
    public boolean execute() {        
        for(int i = 0; i < Network.size(); i++){
            MessageExecuteVCube message = new MessageExecuteVCube();
            EDSimulator.add(10, message, Network.get(i), pid);
            System.out.println("1");
        }
        
        return false;
    }
}
