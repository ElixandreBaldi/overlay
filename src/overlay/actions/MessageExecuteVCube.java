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
public class MessageExecuteVCube implements Action{

    public MessageExecuteVCube() {   
        //System.out.println("oiiii");
    }
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {         
        Parameters p = protocol.getP();        
        int pid = VCubeCreate.getPid();
        Transport t = (Transport) node.getProtocol(p.getTid());
        short[] timestamp = protocol.getTimestamp();
        
        ArrayList<Integer> targets = new ArrayList<>();        
        int nCluster = VCubeCreate.getnCluster();
        
        for(int i = 1; i <= nCluster; i++) {
            Cis.getTargets(node.getIndex(), i, targets, timestamp.clone());        
        }
        
        for(int i = 0; i < targets.size(); i++) {
            EDSimulator.add(0, new ExecutePing(targets.get(i).shortValue()), Network.get(protocol.getCurrentId()), Utils.pid);            
        }        
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}
