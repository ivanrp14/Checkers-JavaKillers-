/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers.utils;

import edu.upc.epsevg.prop.checkers.GameStatus;
import java.nio.file.attribute.AclEntryFlag;
import java.util.logging.Logger;

/**
 *
 * @author ivanr
 */
public class Node {
    public int bestMoveIndex, depth;
    public double eval;
   
    public Node(double eval, int bestMoveI, int depth) {
        this.bestMoveIndex = bestMoveI;
        this.depth = depth;
     
        this.eval = eval;
    }

    
   
    

   
    


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.bestMoveIndex != other.bestMoveIndex) {
            return false;
        }
        return this.eval == other.eval;
    }

   
    
    
}
