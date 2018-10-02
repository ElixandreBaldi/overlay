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
public class Ping implements Action{
    private int sender;    
    
    public Ping(int sender) {
        this.sender = sender;        
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {        
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu ack de nodo "+sender);             
        //Utils.updateTimestampLocal(protocol.getTimestamp(), this.timestampSender, protocol.getCurrentId(), this.sender);
        Utils.send(
                protocol.getCurrentId(), 
                this.sender, 
                (Transport) node.getProtocol(protocol.getP().getTid()), 
                new Pong(protocol.getCurrentId(), protocol.getTimestamp().clone())
        );
    }            
}
