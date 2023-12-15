package edu.upc.epsevg.prop.checkers;

import edu.upc.epsevg.prop.checkers.players.HumanPlayer;
import edu.upc.epsevg.prop.checkers.players.RandomPlayer;
import edu.upc.epsevg.prop.checkers.IPlayer;
import edu.upc.epsevg.prop.checkers.players.OnePiecePlayer;
import edu.upc.epsevg.prop.checkers.players.javakillers.PlayerID;
import edu.upc.epsevg.prop.checkers.players.javakillers.PlayerMiniMax;
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Zobrist;



import javax.swing.SwingUtilities;

/**
 * Checkers: el joc de taula.
 * @author bernat
 */
public class Game {
        /**
     * @param args
     */
    public static void main(String[] args) { 
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                
                IPlayer player1 = new OnePiecePlayer(0.001f);
                IPlayer player2 = new PlayerMiniMax(12,2);
                //IPlayer player1 = new RandomPlayer("Kamikaze 1");
                //IPlayer player2 = new RandomPlayer("Kamikaze 2");
                                
                new Board(player1 , player2, 1, false);
             }
        });
    }
}
