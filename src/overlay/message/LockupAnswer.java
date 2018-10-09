/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

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
    
    public LockupAnswer(short sender, boolean lookupTrue, byte[] hash) {
        this.sender = sender;
        this.lookupTrue = lookupTrue;
        this.hash = hash;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(lookupTrue) {
            //System.out.println("Nodo: "+node.getIndex()+"\nMensagem Recebida de: "+this.sender);
        } else {
            Utils.executeLookup(hash, node, protocol);
        }
        
    }
    
}
