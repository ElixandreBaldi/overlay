/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.logging.Level;
import java.util.logging.Logger;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class LockupAnswer implements Action{
    private short sender;
    private boolean lookupTrue;
    private byte[] hash;
    private int startTime;
    
    public LockupAnswer(short sender, boolean lookupTrue, byte[] hash, int startTime) {
        this.sender = sender;
        this.lookupTrue = lookupTrue;
        this.hash = hash;
        this.startTime = startTime;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(lookupTrue) {
            System.out.println("Nodo: "+node.getIndex()+"   Mensagem Recebida de: "+this.sender);
            protocol.removeVerifyTimestamp(startTime);
        } else {
            Utils.executeLookup(hash, node, protocol);
        }
    }
}