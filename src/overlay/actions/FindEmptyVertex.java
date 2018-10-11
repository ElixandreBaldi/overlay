/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay.actions;

import overlay.Utils;
import overlay.vcube.VCubeProtocol;
import peersim.core.Node;

/**
 *
 * @author elixandre
 */
public class FindEmptyVertex implements Action{

    public int encontrarMaisCheio(short[] timestamp){
        int maisCheio = -1;
        int cont = 0, cont2 = 0;
        int qtdVazio = 0;
        for(int i = 0; i < timestamp.length; i++){
            if(timestamp[i]%2 != 0)
                qtdVazio++;
        }
        
        int[] responsaveis = new int[qtdVazio];
        int j = 0;
        
        for(short i = 0; i < timestamp.length; i++){
            if(timestamp[i]%2 != 0){
                responsaveis[j] = Utils.findResponsible(i, timestamp);
                j++;
            }
        }        
        
        for(int i = 0; i < timestamp.length; i++){            
            if(timestamp[i]%2 == 0){                
                for(int k = 0; k<qtdVazio; k++){                    
                    if(responsaveis[k] == i){
                        cont++;
                    }
                }        
                if(cont > cont2){
                    cont2 = cont;
                    maisCheio = i;
                }
                cont = 0;
            }                        
        }
        return maisCheio;
    }
    
    public int encontrarMaisAdequado(short[] timestamp){
        int maisCheio = encontrarMaisCheio(timestamp);
        //System.out.println("Mais Cheio: "+maisCheio);
        int cont = 0;
        int imaisAdequado;
        int maisAdequado = 0;
        int[] nodosPossiveis = new int[0];
        int nNodos = 0, iMaisCheio = -1;
        for(short i = 0; i < timestamp.length; i++){
            if(Utils.findResponsible(i, timestamp) == maisCheio){
                int[] tmp = nodosPossiveis; // copia o array nodo atual para um temporário
                nNodos++; // incrementa o número de nodos
                nodosPossiveis = new int[nNodos]; // recria o array de nodos vazio, com o novo tamanho
                System.arraycopy(tmp, 0, nodosPossiveis, 0, tmp.length); // copia o array temporário para o novo
                nodosPossiveis[nNodos - 1] = i; // insere o novo nodo na listagem                
                if( i == maisCheio)
                    iMaisCheio = nNodos - 1;
            }            
        }
        
        if((iMaisCheio == nNodos - 1 || iMaisCheio == 0) && nNodos != 2){                                   
            return nodosPossiveis[nNodos/2];
        }
        if(nNodos - iMaisCheio > iMaisCheio)
            return nodosPossiveis[nNodos - 1];
        else
            return nodosPossiveis[0];                
    }
    
    @Override
    public void run(Node node, VCubeProtocol protocol, boolean execute) {
        if(execute) {
            protocol.getProcessQueue().add(this);            
            return;
        }
        short[] timestampClone = protocol.getTimestamp().clone();
        
        //System.out.println("");        
        //System.out.println("Nodo: "+protocol.getCurrentId());
        //for(int i = 0; i < timestampClone.length; i++) System.out.print(" "+timestampClone[i]);
        //System.out.println("");
        //System.out.println("Mais adequado: "+encontrarMaisAdequado(timestampClone));
        //System.out.println("");
    }
    
}
