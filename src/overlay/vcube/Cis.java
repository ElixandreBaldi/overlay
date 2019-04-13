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
    
    static private short[] get(int i, int s) {   
        int k = (int) (Math.pow(2, s)/2);
        short[] indexCluster = new short[k];
        
        for(int x = 0; x < k; x++) {
            int p = (i ^ (int)Math.pow(2, s-1));
            
            if(x == 0) {
                indexCluster[x] = (short) p;
            } else {
                indexCluster[x] = (short) (p^x);
            }
        }        
        return indexCluster;
    }
    
    public void print(int nLine, int nColumn) {
        for(int s = 0; s < nLine; s++) {
            for(int i = 0; i < nColumn; i++) {
                short[] index = get(i, s);
                
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
    
    static public int getIndexFirstNodeUp(int index, int s, short[] timestamp) {                        
        short[] cjs = get(index, s);        
        for(int i = 0; i < cjs.length; i++) {
            /*if(cjs[i] < 0) {
                System.out.println("moio");
            }*/
            if(timestamp[cjs[i]&0xff] % 2 == 0) {                
                return cjs[i];
            }
        }
        return -1;
    }
    
    static public void getTargets(int indexI, int s, ArrayList<Integer> targets, short[] timestamp) {
        short[] cis = get(indexI, s);    
        int size = cis.length;
        for(int i = 0; i < size; i++) {
            int indexJ = cis[i];
            int indexFirstNodeUpJ = getIndexFirstNodeUp(indexJ, s, timestamp);
            if(indexFirstNodeUpJ == indexI){
                targets.add(Network.get(indexJ).getIndex());
            }
        }
    }
}
