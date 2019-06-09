/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import java.util.List;
import overlay.Utils;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindMostAppropriate implements Action{
    private int startTime;
    private short root;
    
    public FindMostAppropriate(short root, int startTime) {
        this.startTime = startTime;
        this.root = root;
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
        
        
        if(freeVertex.isEmpty()) {
            short newFuller = Utils.findFuller(timestamp.clone());
            if(newFuller >= 0) {                
                EDSimulator.add(Utils.getRepeatNextEvent(), new FindMostAppropriate(this.root, this.startTime), Network.get(newFuller), Utils.pid);
                Utils.countDelegateFindMostAppropriate++;
                //System.out.println("Delegado");
            } else {
                Utils.countViewFull++;
                //System.out.println("Vista como cheia");
                EDSimulator.add(Utils.getRepeatNextEvent(), new FindEmptyVertex(), Network.get(this.root), Utils.pid);
                //o nodo enxerga a rede como cheia.
            }
            
            return -1;
        }        
        return indexMostAppropriate;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol) {        
        
        int indexMostAppropriate = findMostAppropriate(protocol.getTimestamp().clone(), protocol.getCurrentId(), protocol);
        //System.out.println("Nodo "+protocol.getCurrentId()+" definiu o index mais apropriado: "+indexMostAppropriate);
        if(indexMostAppropriate >= 0) {
            VCubeProtocol target = (VCubeProtocol) Network.get(indexMostAppropriate).getProtocol(Utils.pid);
            target.setStatus(true, protocol.getCurrentId());
        }
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
