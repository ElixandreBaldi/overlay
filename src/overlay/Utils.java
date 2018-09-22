/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import overlay.message.Ack;
import overlay.message.Action;
import overlay.vcube.VCubeCreate;
import peersim.core.Network;
import peersim.core.Node;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */
public class Utils {
    static public void send(int sender, int target, Node node, int tid, Action message) {
        Transport t = (Transport) node.getProtocol(tid);
        t.send(Network.get(sender), Network.get(target), message, VCubeCreate.getPid());
    }
    
    static public void updateTimestampLocal(int[] timestampLocal, int[] timestampSender, int indexLocal, int indexSender) {        
        for(int i = 0; i < timestampLocal.length; i++) {
            if(timestampLocal[i] < timestampSender[i] && i != indexLocal) timestampLocal[i] = timestampSender[i];                            
        }
        if(timestampLocal[indexSender] % 2 != 0) timestampLocal[indexSender]++;            
    }
}