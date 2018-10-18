/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import overlay.Utils;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
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
    public void run(Node node, VCubeProtocol protocol) {
        if(putTrue) {
            //if(CommonState.getIntTime() - startTime > 3) 
            //System.out.println("Nodo: "+protocol.getCurrentId()+"   Confiramação de Put Recebida de: "+this.sender+"    startTime: "+startTime+"   time:"+CommonState.getIntTime());
            long dif = CommonState.getIntTime() - startTime;
            Utils.sumTimePut += dif;
            Utils.countPuts++;

            if( (VCubeCreate.scenario == 1 || (VCubeCreate.scenario == 3 && Utils.timeDiagnostic > 0)) && Utils.countPuts >= Utils.nPuts) {                
                Utils.finish(CommonState.getIntTime());
            }
        } else {
            Utils.executePut(hash, node, protocol);
        }
    }
    
    @Override
    public int getStartTime() {
        return -1;
    }
}