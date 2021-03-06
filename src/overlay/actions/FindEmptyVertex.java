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
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;

/**
 *
 * @author elixandre
 */
public class FindEmptyVertex implements Action{
    
    @Override
    public void run(Node node, VCubeProtocol protocol) {
        
        short fuller = Utils.findFuller(protocol.getTimestamp().clone());
        if(fuller >= 0) {
            //System.out.println("Nodo "+protocol.getCurrentId()+"    encontrou o nodo mais sobrecarregado: "+fuller+"      "+CommonState.getIntTime());
            EDSimulator.add(1, new FindMostAppropriate(protocol.getCurrentId(), CommonState.getIntTime()), Network.get(fuller), Utils.pid);            
        }
        else {
            Utils.countViewFull++;
            //o nodo enxerga a rede como cheia.
        }
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
    
}
