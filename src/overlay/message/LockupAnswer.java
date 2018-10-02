/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import overlay.vcube.VCubeProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class LockupAnswer implements Action{
    private String msg;
    public LockupAnswer(String msg) {
        this.msg = msg;        
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        System.out.println("Nodo: "+node.getIndex()+"\nMensagem Recebida: "+this.msg);
    }
    
}
