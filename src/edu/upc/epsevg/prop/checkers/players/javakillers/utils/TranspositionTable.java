/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers.utils;

/**
 *
 * @author ivanr
 */
public class TranspositionTable {
    public Node table[];
    private static final int NodeSize = 8;
    public TranspositionTable(float gb){
        int arraySize = (int) (gb * 1024 * 1024 * 1024);
        arraySize = arraySize / NodeSize;
        table = new Node[arraySize];
    }
    public Node get(long hash){
        int index =  (int) (hash % table.length);
        return table[getIndex(hash)];
    }
    public void set(Node n,  long hash){
        
        table[getIndex(hash)] = n;
    }
    private int getIndex(long hash){
        return Math.abs( (int)(hash % table.length));
    }
   
    public int getFullIndexes(){
        int l = 0;
        for (int i = 0; i < table.length; i++) {
            if(table[i] != null)l++;
        }
        
        return l;
    }
}
