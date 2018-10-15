/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class PutAnswer implements Action{
    private short sender;
    private boolean putTrue;
    private byte[] hash;
    private int startTime;
    
    public PutAnswer(short sender, boolean putTrue, byte[] hash, int startTime) {
        this.sender = sender;
        this.putTrue = putTrue;
        this.hash = hash;
        this.startTime = startTime;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(putTrue) {
            //System.out.println("Nodo: "+node.getIndex()+"   Confiramação de Put Recebida de: "+this.sender);
            protocol.removeVerifyTimestamp(startTime);
        } else {
            Utils.executePut(hash, node, protocol);
        }
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}