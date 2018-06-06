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
public class TableCis {
    private CellCis[][] cis;
    private int nLine;
    private int nColumn;
    
    public TableCis(int nLine, int nColumn) {                
        this.nLine = nLine;
        this.nColumn = nColumn;
        this.cis = new CellCis[this.nLine][this.nColumn];
        this.attTableCis();
    }
    
    private int[] cis(int i, int s) {        
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
    
    private void attTableCis() {        
        for(int i = 0; i < this.nColumn; i++) {
            for(int s = 0; s < this.nLine; s++) {
                CellCis cell = new CellCis();                
                int[] index = this.cis(i, s + 1);
                for(int j = 0; j < index.length; j++) {
                    cell.getCell().add(Network.get(index[j]));
                }
                this.cis[s][i] = cell;
            }
        }
    }
    
    public void print() {
        for(int i = 0; i < this.nLine; i++) {
            for(int j = 0; j < this.nColumn; j++) {
                this.cis[i][j].print();
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }        
    
    public void defineNeighbor(int column, int line, ArrayList<Node> neighbor) {        
        
        CellCis cell = cis[line][column];
        ArrayList<Node> nodesJ = cell.getCell();
        int size = nodesJ.size();
        
        for(int i = 0; i < size; i++) {
            int indexJ = nodesJ.get(i).getIndex();
            int indexFirstNodeUpJ = cis[line][indexJ].getIndexFirstNodeUp();
            if(indexFirstNodeUpJ == column){                     
                neighbor.add(Network.get(indexJ));
            }
        }
    }
}
