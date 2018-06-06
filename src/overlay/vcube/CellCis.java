/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

import java.util.ArrayList;
import peersim.core.Node;

/**
 *
 * @author elixandrebaldi
 */
public class CellCis {
    private ArrayList<Node> cell;       
    
    public CellCis() {
        this.cell = new ArrayList<Node>();
    }
    
    public ArrayList<Node> getCell() {
        return this.cell;
    }
    
    public void print() {
        int size = this.cell.size();
        for(int i = 0; i < size; i++) {
            int index = this.cell.get(i).getIndex();
            System.out.print(""+index);
            if(i < size - 1) System.out.print(", ");
        }
    }        

    int getIndexFirstNodeUp() {                        
        for(int i = 0; i < cell.size(); i++) {            
            if(cell.get(i).isUp()) {                
                return cell.get(i).getIndex();
            }
        }
        return -1;
    }    
}
