/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.vcube;

/**
 *
 * @author elixandrebaldi
 */
public class Parameters {
    int tid;
    
    int pid;
    
    public int getPid() {
        return this.pid;
    }
    
    public int getTid() {
        return this.tid;
    }
    
    public Parameters clone(){
        Parameters p = new Parameters();
        p.tid = getTid();
        p.pid = getPid();
        
        return p;
    }
}
