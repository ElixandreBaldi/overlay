/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class Pong implements Action{
    private int sender;
    private short[] timestampSender;
    private int startTime;

    public Pong(int sender, short[] timestamp, int startTime) {
        this.sender = sender;
        this.timestampSender = timestamp;
        this.startTime = startTime;
    }

    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);
            return;
        }
        System.out.println("Nodo: "+protocol.getCurrentId()+" recebeu Pong de nodo "+sender+"       no start: "+startTime+"      no tempo: "+CommonState.getIntTime());
        protocol.removeVerifyTimestamp(startTime);
        Utils.updateTimestampLocal(protocol.getTimestamp(), this.timestampSender, protocol.getCurrentId(), this.sender);
    }
}