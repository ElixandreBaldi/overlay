/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.math.BigInteger;
import java.util.ArrayList;
import overlay.Utils;
import overlay.vcube.Parameters;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDSimulator;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */

public class LookUp implements Action{
    private int sender;    
    private byte[] key;
    private int startTime;
    
    public LookUp(int sender, byte[] key, int startTime) {
        this.sender = sender;
        this.key = key;   
        this.startTime = startTime;
    }

    public LookUp() {        
    }
    
    public int getSender() {
        return sender;
    }
    
    public byte[] getKey() {
        return key;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol) {        
        Parameters p = protocol.getP();        
        boolean lookupTrue = true;
        if(!(Utils.responsibleKey(key, protocol.getTimestamp()) == protocol.getCurrentId())) lookupTrue = false;                    
        EDSimulator.add(1, new LockupAnswer(protocol.getCurrentId(), lookupTrue, key, startTime), Network.get(this.sender), Utils.pid);
    }
    
    @Override
    public int getStartTime() {
        return this.startTime;
    }
}
