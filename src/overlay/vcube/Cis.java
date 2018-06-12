/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.ArrayList;
import peersim.core.Network;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class Cis {
    
    static private int[] get(int i, int s) {        
        int k = (int) (Math.pow(2, s)/2);
        int[] indexCluster = new int[k];
        
        for(int x = 0; x < k; x++) {
            int p = (i ^ (int)Math.pow(2, s-1));
            
            if(x == 0) {
                indexCluster[x] = p;
            } else {
                indexCluster[x] = p^x;
            }
        }                
        
        return indexCluster;
    }
    
    public void print(int nLine, int nColumn) {
        for(int s = 0; s < nLine; s++) {
            for(int i = 0; i < nColumn; i++) {
                int[] index = get(i, s);
                
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }        
    
    public void printCell(int[] index) {
        int size = index.length;
        for(int i = 0; i < size; i++) {            
            System.out.print(""+index[i]);
            if(i < size - 1) System.out.print(", ");
        }
    }  
    
    static public int getIndexFirstNodeUp(int index, int s) {                        
        int[] cell = get(index, s);
        
        for(int i = 0; i < cell.length; i++) {            
            if(Network.get(cell[i]).isUp()) {                
                return cell[i];
            }
        }
        return -1;
    }    
    
    static public void defineNeighbor(int indexI, int s, ArrayList<Node> neighbor) {        
        
        int[] cell = get(indexI, s);        
        int size = cell.length;
        for(int i = 0; i < size; i++) {
            int indexJ = cell[i];
            int indexFirstNodeUpJ = getIndexFirstNodeUp(indexJ, s);            
            if(indexFirstNodeUpJ == indexI){            
                neighbor.add(Network.get(indexJ));
            }
        }
    }
}
