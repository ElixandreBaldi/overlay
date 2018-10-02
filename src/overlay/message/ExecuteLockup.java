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
public class ExecuteLockup implements Action{
    private String msg;
    public ExecuteLockup(String msg) {
        this.msg = msg;        
    }    

    private int responsibleKey(String msg) {
        return 5;
    }
    
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        int p = responsibleKey(this.msg);
        int tid = protocol.getP().getTid();
        Utils.send(node.getIndex(), p, node.getProtocol(tid), new LookUp(node.getIndex(), "msg"));
    }
}
