/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.message;

import java.util.ArrayList;
import overlay.vcube.Parameters;
import peersim.core.CommonState;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class MessageExecuteVCube implements Message{

    public MessageExecuteVCube() {   
        //System.out.println("oiiii");
    }
    @Override
    public void apply(Node node, Parameters p, ArrayList<Node> neighbor) {
        int teste = CommonState.getIntTime();
        
        System.out.println("invocado "+teste);
    }
    
    
}
