/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import overlay.actions.Action;
import overlay.actions.LookUp;
import overlay.actions.Put;
import overlay.vcube.VCubeCreate;
import overlay.vcube.VCubeProtocol;
import peersim.core.CommonState;
import peersim.core.Network;
import peersim.core.Node;
import peersim.core.Protocol;
import peersim.edsim.EDSimulator;
import peersim.transport.Transport;

/**
 *
 * @author elixandrebaldi
 */
public class Utils {    
    
    static public boolean flagDown = true;
    
    static public int countNodeDown = 0;    
    
    static public int pid = 0;
    
    static public int repetation = 0;                

    public static long hit = 0;
    
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
        BigInteger v = value.divide(two.pow(256-VCubeCreate.getnCluster()));
                
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
    
    public static String getRandomString() {
        byte[] array = new byte[20]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));        
    }

    public static void executeLookup(byte[] hash, Node node, VCubeProtocol protocol) {
        short p = Utils.responsibleKey(hash, protocol.getTimestamp().clone());
        int tid = protocol.getP().getTid();
        int time = CommonState.getIntTime();
        EDSimulator.add(1, new LookUp(node.getIndex(), hash, time), Network.get(p), Utils.pid);        
        //System.out.println("Nodo "+protocol.getCurrentId()+" enviando lookup para "+p);
    }
    
    public static void executePut(byte[] hash, Node node, VCubeProtocol protocol) {
        short p = Utils.responsibleKey(hash, protocol.getTimestamp().clone());        
        int time = CommonState.getIntTime();
        EDSimulator.add(1, new Put(node.getIndex(), hash, time), Network.get(p), Utils.pid);        
        System.out.println("Nodo "+protocol.getCurrentId()+" enviando put para "+p);        
    }

    public static void printNetwork() {
        for(int i = 0; i < Network.size(); i++) {
            VCubeProtocol node = (VCubeProtocol) Network.get(i).getProtocol(Utils.pid);
            System.out.print(" "+node.getStatus());
        }
        System.out.println("");
    }
    
    public static int getIndexResponsable(short responsable, List<Responsables> responsables) {        
        for(int i = 0; i < responsables.size(); i++) {
            if(responsables.get(i).getResponsable() == responsable) return i;        
        }        
        return -1;
    }
    
    public static short findFuller(short[] timestamp){
        short fuller = -1;
        int countFuller = -1;
        List<Responsables> responsables = new ArrayList<>();
        for(int i = 0; i < timestamp.length; i++) {
            if(timestamp[i] % 2 != 0 ){
                short responsable = Utils.findResponsible((short) i, timestamp);
                int indexResponsable = getIndexResponsable(responsable, responsables);
                if( indexResponsable >= 0) {
                    int count = responsables.get(indexResponsable).incrementCount();
                    if(count > countFuller) {
                        fuller = responsable;
                        countFuller = count;
                    }
                } else {
                    responsables.add(new Responsables(responsable));
                    if(countFuller < 1) {
                        fuller = responsable;
                        countFuller = 1;
                    }
                }
            }
        }        
        return fuller;
    }

    public static boolean isPair(short s) {
        return s % 2 == 0;
    }
}
