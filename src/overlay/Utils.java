/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import overlay.message.Ping;
import overlay.message.Action;
import overlay.message.LockupAnswer;
import overlay.message.LookUp;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.core.Protocol;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */
public class Utils {
    static public void send(int sender, int target, Protocol t, Action message) {        
        Transport transp = (Transport) t;        
        transp.send(Network.get(sender), Network.get(target), message, VCubeCreate.getPid());
    }
    
    static public void updateTimestampLocal(short[] timestampLocal, short[] timestampSender, int indexLocal, int indexSender) {        
        for(int i = 0; i < timestampLocal.length; i++) {
            if(timestampLocal[i] < timestampSender[i] && i != indexLocal) timestampLocal[i] = timestampSender[i];                            
        }
        if(timestampLocal[indexSender] % 2 != 0) timestampLocal[indexSender]++;            
    }
    
    static public short findResponsible(short v, short[] timestamp) {
        short z = 0;
        short i = v;
        while(true){
            if(i < Network.size() && timestamp[i]%2 == 0) break;
            z++;
            i = (short) (v^z);
        }
        return i;
    }
    
    static public short responsibleKey(byte[] hash, short[] timestamp) {
        BigInteger value = new BigInteger(hash);
        BigInteger two = new BigInteger("2");
        BigInteger v = value.divide(two.pow(160-VCubeCreate.getnCluster()));
                
        short j = v.shortValue();
        if(j < 0) j *= -1;
        //TODO ta certo esse * -1?
        
        return findResponsible(j, timestamp);                            
        //return j;
    }
    
    static public Node getRandomNode() {
        Node target;
        short size = (short) Network.size();
        do{                                        
            target = Network.get(CommonState.r.nextInt(size));
        } while(target == null || target.isUp() == false);
        
        return target;
    }
    
    public static byte[] generateHash(String resource, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(resource.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int highPiece = ((bytes[i] >> 4) & 0xf) << 4;
            int lowPiece = bytes[i] & 0xf;
            
            if (highPiece == 0) s.append('0');
            
            s.append(Integer.toHexString(highPiece | lowPiece));
        }
        return s.toString();
    }
    
    public static void executeLookup(byte[] hash, Node node, VCubeProtocol protocol) {
        short p = Utils.responsibleKey(hash, protocol.getTimestamp().clone());
        int tid = protocol.getP().getTid();
        Utils.send(
                node.getIndex(),
                p, 
                node.getProtocol(tid), 
                new LookUp(node.getIndex(), hash));
        //System.out.println("Nodo "+protocol.getCurrentId()+" enviando lookup para "+p);
    }
}
