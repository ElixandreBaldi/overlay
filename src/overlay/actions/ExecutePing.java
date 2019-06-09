/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.ArrayList;
import overlay.Utils;
import overlay.vcube.Cis;
import overlay.vcube.Parameters;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
import peersim.transport.Transport;


/**
 *
 * @author elixandre
 */
public class ExecutePing implements Action{

    public ExecutePing() {   
        //System.out.println("oiiii");
    }
    @Override
    public void run(Node node, VCubeProtocol protocol) {
        
        if(!protocol.getStatus()) return;
        
        Parameters p = protocol.getP();                
        short[] timestamp = protocol.getTimestamp();
        
        ArrayList<Integer> targets = new ArrayList<>();        
        int nCluster = VCubeCreate.getnCluster();
        for(int i = 1; i <= nCluster; i++) {
            Cis.getTargets(node.getIndex(), i, targets, timestamp.clone());        
        }
        
        int time = CommonState.getIntTime();
        for(int i = 0; i < targets.size(); i++) {
            //System.out.println("Target de "+protocol.getCurrentId()+"    =    "+targets.get(i)+"        enviando no tempo: "+(time+ i +1));
            
            EDSimulator.add(i+1, new Ping(protocol.getCurrentId(), time+i), Network.get(targets.get(i)), Utils.pid);            
        }        
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
