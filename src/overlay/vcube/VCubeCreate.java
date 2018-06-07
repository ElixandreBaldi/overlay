/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.ArrayList;
import peersim.config.Configuration;
import peersim.core.Control;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeCreate implements Control {
    
    private int pid = 0;
    
    private final String PAR_PROT = "protocol";
    
    private TableCis cis;        

    private int nCluster;
    
    private int networkSize;
    
    public VCubeCreate(String prefix) {
        this.pid = Configuration.getPid(prefix +"."+ PAR_PROT);
    }
    
    public boolean execute() {        
        this.networkSize = Network.size();
        this.nCluster = (int) Math.ceil(Math.log(networkSize) / Math.log(2));        
        this.cis = new TableCis(nCluster, networkSize); 
        for(int i = 0; i < this.networkSize; i++) {
            Node node = (Node) Network.get(i);
            VCubeProtocol vcp = (VCubeProtocol) node.getProtocol(this.pid);  
            vcp.setNeighbor(this.defineNeighbor(i));
            System.out.println("Index: "+i);
            vcp.printNeighbor();
        }                        
        return false;
    }
    
    private ArrayList<Node> defineNeighbor(int index) {        
        ArrayList<Node> neighbor = new ArrayList<>();
        for(int s = 0; s < nCluster; s++) {
            cis.defineNeighbor(index, s, neighbor);                            
        }
        
        return neighbor;
    }    
}
