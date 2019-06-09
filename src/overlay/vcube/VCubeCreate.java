/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.math.BigInteger;
import java.util.ArrayList;
import overlay.Utils;
import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeCreate implements Control {
    
    static public int nodosOk;
    
    static public int scenario;
    
    static private int pid = 0;
    
    private final String PAR_PROT = "protocol";
    
    private final String PAR_IDLENGTH = "idLength";        
    
    static private int nCluster;
    
    private int networkSize;
    
    private int idLength;
    
    static public String pathOut;
    
    public VCubeCreate(String prefix) {                
        pid = Configuration.getPid(prefix +"."+ PAR_PROT);
        Utils.pid = pid;
        this.idLength = Configuration.getInt(prefix + "." + PAR_IDLENGTH);
        
        this.nodosOk = Configuration.getInt(prefix + ".nodosOk");
        this.scenario = Configuration.getInt(prefix + ".scenario");
        this.pathOut = Configuration.getString(prefix + ".pathOut");
        Utils.nPuts = Configuration.getInt(prefix + ".nPuts");
        Utils.nLookups = Configuration.getInt(prefix + ".nLookups");
        Utils.stepVCube = Configuration.getInt(prefix + ".stepVCube");
    }
    
    static public int getnCluster() {
        return nCluster;        
    }
    
    static public int getPid() {
        return pid;        
    }
    
    public boolean execute() {
        this.networkSize = Network.size();
        this.nCluster = (int) Math.ceil(Math.log(networkSize) / Math.log(2));
        for(short i = 0; i < this.networkSize; i++) {
            Node node = (Node) Network.get(i);
            VCubeProtocol vcp = (VCubeProtocol) node.getProtocol(this.pid);
            vcp.setCurrentId(i);
            vcp.setTimestamp(Network.size());
        }
        return false;
    }
}