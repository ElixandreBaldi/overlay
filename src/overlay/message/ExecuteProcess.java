/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.List;
import java.util.Queue;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class ExecuteProcess implements Action{

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {                      
        List <Action> processQueue = protocol.getProcessQueue();
        while(!processQueue.isEmpty()) {            
            Action event = processQueue.remove(0);
            event.run(node, protocol, false);            
            
        }        
    }
    
}
