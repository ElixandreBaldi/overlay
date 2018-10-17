/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.math.BigInteger;
import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class PongError implements Action{
    private int sender;
    private short[] timestampSender;
    private int startTime;    

    public PongError(int sender, short[] timestamp, int startTime) {
        this.sender = sender;
        this.timestampSender = timestamp;
        this.startTime = startTime;        
    }

    @Override
    public void run(Node node, VCubeProtocol protocol) {
        //System.out.println("Nodo: "+protocol.getCurrentId()+" detectou falha no nodo "+sender+"       no start: "+startTime+"      no tempo: "+CommonState.getIntTime());
        if(Utils.isPair(protocol.getTimestamp()[sender])){
            protocol.getTimestamp()[sender]++;
        }
        
        //protocol.printTimestamp();
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}