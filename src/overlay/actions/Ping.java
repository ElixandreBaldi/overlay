/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

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
    private short sender;   
    private int startTime;   
    
    public Ping(short sender, int startTime) {
        this.sender = sender;
        this.startTime = startTime;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {        
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        //System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu Ping de nodo "+sender);        
        Utils.send(
            protocol.getCurrentId(),
            this.sender, 
            (Transport) node.getProtocol(protocol.getP().getTid()), 
            new Pong(protocol.getCurrentId(), protocol.getTimestamp().clone(), startTime)
        );        
    }            
}
