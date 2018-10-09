/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.Random;
import peersim.config.Configuration;
import peersim.core.Network;
import peersim.core.Node;
import peersim.dynamics.NodeInitializer;

/**
 *
 * @author elixandrebaldi
 */
public class VCubeInitializer implements NodeInitializer {

	private static final String PAR_PROT = "protocol";

	private int pid = 0;

	private VCubeProtocol vcp;

	public VCubeInitializer(String prefix) {
		pid = Configuration.getPid(prefix + "." + PAR_PROT);                
	}

	public void initialize(Node n) {
		vcp = (VCubeProtocol) n.getProtocol(pid);
		join(n);
	}

	public void join(Node myNode) {
		Random generator = new Random();
		                
		// search a node to join
		Node n;
		do {
                    n = Network.get(generator.nextInt(Network.size()));
		} while (n != null || n.isUp() != false);
                
                System.out.println("Teste: "+n.getIndex());
		
	}
}
