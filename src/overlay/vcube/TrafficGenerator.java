/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import overlay.message.LookUpMessage;
import java.math.BigInteger;
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
public class TrafficGenerator implements Control {

    private static final String PAR_PROT = "protocol";
    
    private final int pid;
    
    public TrafficGenerator(String prefix) {
        pid = Configuration.getPid(prefix + "." + PAR_PROT);
    }
    
    public boolean execute() {        
        int size = Network.size();
        Node sender, target;        
        do{                            
            sender = Network.get(CommonState.r.nextInt(size));
            target = Network.get(CommonState.r.nextInt(size));
        } while((sender == null || sender.isUp() == false || target == null || target.isUp() == false) && sender.getIndex() == target.getIndex());        

        LookUpMessage message = new LookUpMessage(sender, ((VCubeProtocol) target.getProtocol(pid)).getVCubeId());
        EDSimulator.add(10, message, sender, pid);                        
        
        return false;
    }
}
