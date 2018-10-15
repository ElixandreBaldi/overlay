/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import java.util.List;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindMostAppropriate implements Action{

    public FindMostAppropriate() {
    }
    
    public int counterBit(int n) {
        int count = 0;
        for (int i = 0; i < 32; ++i) {
            if (((n >>> i) & 1) == 1) {
                ++count;
            }
        }
        return count;
    }
    
    public int findMostAppropriate(short[] timestamp, int fuller, VCubeProtocol protocol){        
        List<Short> freeVertex = new ArrayList<>();
        int countBit = 0;
        short indexMostAppropriate = -1;
        
        for(short i = 0; i < timestamp.length; i++){
            if(timestamp[i] % 2 != 0 && Utils.findResponsible(i, timestamp) == fuller) {
                freeVertex.add(i);
                int countBit2 = counterBit(i ^ fuller);
                if(countBit2 > countBit) {
                    countBit = countBit2;
                    indexMostAppropriate = i;
                }
            }            
        }
        
        if(freeVertex.size() == 0) {
            short newFuller = Utils.findFuller(timestamp.clone());
            if(newFuller == -1) protocol.printTimestamp();
            else EDSimulator.add(0, new FindMostAppropriate(), Network.get(newFuller), Utils.pid);
            
            return -1;
        }
        
        return indexMostAppropriate;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        
        int indexMostAppropriate = findMostAppropriate(protocol.getTimestamp().clone(), protocol.getCurrentId(), protocol);
        
        if(indexMostAppropriate >= 0) {
            VCubeProtocol target = (VCubeProtocol) Network.get(indexMostAppropriate).getProtocol(Utils.pid);
            target.setStatus(true);
        }
    }
    
}
