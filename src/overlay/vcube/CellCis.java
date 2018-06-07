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
public class CellCis {
    private ArrayList<Integer> cell;       
    
    public CellCis() {
        this.cell = new ArrayList<Integer>();
    }
    
    public ArrayList<Integer> getCell() {
        return this.cell;
    }
    
    public void print() {
        int size = this.cell.size();
        for(int i = 0; i < size; i++) {
            int index = this.cell.get(i);
            System.out.print(""+index);
            if(i < size - 1) System.out.print(", ");
        }
    }        

    public int getIndexFirstNodeUp() {                        
        for(int i = 0; i < cell.size(); i++) {            
            if(Network.get(cell.get(i)).isUp()) {                
                return cell.get(i);
            }
        }
        return -1;
    }    
}
