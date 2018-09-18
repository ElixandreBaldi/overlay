/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.ArrayList;
import overlay.vcube.Cis;
import overlay.vcube.Parameters;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
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
    public void run(Node node, VCubeProtocol protocol) {
        Parameters p = protocol.getP();
        int indexNode = node.getIndex();
        int pid = p.getPid();
        Transport t = (Transport) node.getProtocol(p.getTid());
        int[] timestamp = protocol.getTimestamp();
        
        ArrayList<Integer> targets = new ArrayList<>();        
        int nCluster = VCubeCreate.getnCluster();
        
        for(int i = 1; i <= nCluster; i++) {
            Cis.getTargets(indexNode, i, targets, timestamp.clone());        
        }
        
        for(int i = 0; i < targets.size(); i++) {
            t.send(Network.get(indexNode), Network.get(targets.get(i)), new Ack(indexNode, timestamp.clone()), pid);
        }
    }
}
