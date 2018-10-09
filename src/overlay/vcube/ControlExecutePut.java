/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.UUID;
import overlay.Utils;
import overlay.message.ExecuteLookup;
import overlay.message.ExecuteProcess;
import overlay.message.ExecutePut;
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
        //System.out.println(Utils.stringHexa(hash));
        
        ExecutePut message = new ExecutePut(hash);
        EDSimulator.add(50, message, Utils.getRandomNode(), pid);  
        /*if(VCubeCreate.flag2) {
            EDSimulator.add(5, message, Network.get(5), pid);
            VCubeCreate.flag2 = false;
        } */       
        return false;
    }
}
