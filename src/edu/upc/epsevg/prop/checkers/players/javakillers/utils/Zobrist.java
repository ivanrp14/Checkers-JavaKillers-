/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers.utils;
import edu.upc.epsevg.prop.checkers.CellType;
import static edu.upc.epsevg.prop.checkers.CellType.P1;
import edu.upc.epsevg.prop.checkers.GameStatus;
import edu.upc.epsevg.prop.checkers.PlayerType;
import java.security.*;
/**
 *
 * @author ivanr
 */
import java.security.SecureRandom;

public class Zobrist {
    public long table[][][] = new long[8][8][4];
    public long turn[] = new long[2];
    private  SecureRandom random = new SecureRandom();
    
    public Zobrist(){
        initTable();
        
    }
    public static int indexOf(CellType c){
        if(c == CellType.P1){
            return 0;
        }
        if(c == CellType.P1Q){
            return 1;
        }
        if(c == CellType.P2){
            return 2;
        }
        if(c == CellType.P2Q){
            return 3;
        }
        if(c == CellType.EMPTY) {
            return 4;
        }
        else return -1;
    }
    private void initTable(){
         for (int i = 0; i<8; i++)
            for (int j = 0; j<8; j++)
                for (int  k = 0; k<4; k++){
                    table[i][j][k] = random.nextLong();
                }
         turn[0] = random.nextLong();
         turn[1] = random.nextLong();
    }
    public long calculateFullHash(GameStatus gs){
      
        long h = 0;
        for (int i = 0; i<8; i++)
        {
            for (int j = 0; j<8; j++)
            {
                CellType c = gs.getPos(i, j);
                if (c != null && c != CellType.EMPTY)
                {
                    int piece = indexOf(c);
                    h ^= table[i][j][piece] ;
                }
            }
        }
        
        h ^= turn[gs.getCurrentPlayer().ordinal()];
        return h;
    }
    

    
    
    
    
}