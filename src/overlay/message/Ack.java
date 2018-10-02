/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.Queue;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;
import peersim.transport.Transport;

/**
 *
 * @author elixandre
 */
public class Ack implements Action{
    private int sender;
    private int[] timestampSender;
    
    public Ack(int sender, int[] timestamp) {
        this.sender = sender;
        this.timestampSender = timestamp;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {        
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }        
        int indexLocal = protocol.getCurrentId();
        int tid = protocol.getP().getTid();
        int[] timestampLocal = protocol.getTimestamp();
        //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu ack de nodo "+sender);             
        Utils.updateTimestampLocal(protocol.getTimestamp(), this.timestampSender, protocol.getCurrentId(), this.sender);
        Utils.send(indexLocal, this.sender, (Transport) node.getProtocol(tid), new Nack(indexLocal, timestampLocal));
    }            
}
