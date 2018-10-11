/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import java.util.List;
import overlay.Responsables;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindEmptyVertex implements Action{
    
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        short fuller = Utils.findFuller(protocol.getTimestamp().clone());
        
        EDSimulator.add(0, new FindMostAppropriate(), Network.get(fuller), Utils.pid);
    }
    
}
