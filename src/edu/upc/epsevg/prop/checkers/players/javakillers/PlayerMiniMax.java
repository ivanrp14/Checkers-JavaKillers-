/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers;

import edu.upc.epsevg.prop.checkers.CellType;
import static edu.upc.epsevg.prop.checkers.CellType.P1Q;
import edu.upc.epsevg.prop.checkers.GameStatus;
import edu.upc.epsevg.prop.checkers.IAuto;
import edu.upc.epsevg.prop.checkers.IPlayer;
import edu.upc.epsevg.prop.checkers.MoveNode;
import edu.upc.epsevg.prop.checkers.PlayerMove;
import edu.upc.epsevg.prop.checkers.PlayerType;
import edu.upc.epsevg.prop.checkers.SearchType;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ivanr
 */

public class PlayerMiniMax  implements IPlayer, IAuto{
 
    

    private String name;
    private long exploredNodes;
    private int depth, maxDepthReached;
    private PlayerType maximizerPlayer, minimizerPlayer;

    public PlayerMiniMax(int d) {
        this.name = "MiniMax";
        this.exploredNodes = 0;
        this.depth = d;
        this.maxDepthReached = 0;
        
    }
    
    
    

    @Override
    public PlayerMove move(GameStatus gs) {
        //Asignamos que PlayerType somos y que PlayerType es el oponente
        maximizerPlayer = gs.getCurrentPlayer();
        minimizerPlayer = PlayerType.opposite(maximizerPlayer);
        
        long timeant = System.currentTimeMillis(); //Usaremos para ver cuanto tarda en cada minimax
        exploredNodes = 0;
        List<Point> bestMovement = new ArrayList<>();
        double actualNodeValue = Double.NEGATIVE_INFINITY;
        double alfa = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        List<MoveNode> movementsList = gs.getMoves();
        for (int i = 0; i < movementsList.size(); i++) {
            for(List<Point> path : getAllPaths(movementsList.get(i))){
                GameStatus gs_copy = new GameStatus(gs);
                gs_copy.movePiece(path);
                double childNodeValue = MiniMax(gs_copy,depth -1 , alfa, beta, true); //Comprobar isMaximizer
                if (actualNodeValue < childNodeValue) {
                    bestMovement = path;
                    actualNodeValue = childNodeValue;
                }
            }
            
        }


        System.out.println(" > Per aquest moviment s'han explorat " + exploredNodes + " jugades finals en :"+(System.currentTimeMillis()-timeant)+"ms");
        
        return new PlayerMove(bestMovement, exploredNodes, maxDepthReached, SearchType.MINIMAX);
    }
    
    
    
   public double MiniMax(GameStatus gs, int d, double alfa, double beta, boolean isMaximizer) {
       if(depth - d > maxDepthReached) maxDepthReached++;
       double actualNodeValue = 0;
        if (d == 0) {
            exploredNodes++;
            actualNodeValue =  Heuristic(gs);
        }
        else if (d > 0){
            exploredNodes++;
            if (gs.isGameOver()) {
                if (gs.checkGameOver()) {
                    if (gs.GetWinner() == maximizerPlayer) actualNodeValue = Double.POSITIVE_INFINITY;
                    else actualNodeValue =  Double.NEGATIVE_INFINITY;
                } 
            }
            else{
               if (isMaximizer) {
                    actualNodeValue = Double.NEGATIVE_INFINITY;
                    List<MoveNode> movementsList = gs.getMoves();
                    for (int i = 0; i < movementsList.size(); i++) {
                        for (List<Point> path : getAllPaths(movementsList.get(i))) {
                            GameStatus gs_copy = new GameStatus(gs);
                            gs_copy.movePiece(path);

                            actualNodeValue = Math.max(actualNodeValue,
                                    MiniMax(gs_copy, d - 1,  alfa, beta, false));

                            alfa = Math.max(actualNodeValue, alfa);
                            if (beta <= alfa) {
                                break;
                            }
                        }
                    }
                } 
                else {
                    actualNodeValue = Double.POSITIVE_INFINITY;
                    List<MoveNode> movementsList = gs.getMoves();
                    for (int i = 0; i < movementsList.size(); i++) {
                        for (List<Point> path : getAllPaths(movementsList.get(i))) {
                            GameStatus gs_copy = new GameStatus(gs);
                            gs_copy.movePiece(path);

                            actualNodeValue = Math.min(actualNodeValue,
                                    MiniMax(gs_copy, d - 1,  alfa, beta, true));

                            beta = Math.min(actualNodeValue, beta);
                            if (beta <= alfa) {
                                break;
                            }
                        }
                    }
                } 
            
            } 
            
        }
        return actualNodeValue;
    }

    private int Heuristic(GameStatus gs){
        return (gs.getScore(maximizerPlayer) - gs.getScore(minimizerPlayer));
    }
    public static List<List<Point>> getAllPaths(MoveNode root) {
        List<List<Point>> allPaths = new ArrayList<>();
        getAllPathsRecursive(root, new ArrayList<>(), allPaths);
        return allPaths;
    }

    private static void getAllPathsRecursive(MoveNode node, List<Point> currentPath, List<List<Point>> allPaths) {
        currentPath.add(node.getPoint());

        if (node.getChildren().isEmpty()) {
            // Reached a leaf node, add the current path to the result
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            // Continue the traversal for each child
            for (MoveNode child : node.getChildren()) {
                getAllPathsRecursive(child, currentPath, allPaths);
            }
        }

        // Remove the current node from the path to backtrack
        currentPath.remove(currentPath.size() - 1);
    }

    @Override
    public void timeout() {
    }

    @Override
    public String getName() {
        return name;
    }
    public void printGS(GameStatus gs){
        /*
        em = Pos buida
        __ = Pos no valida
        p1 = Player1
        p2 = Player2
        q1 = Dama/Reina 1
        q2 = Dama/Reina2
        */
        String s = new String();
        
        for(int i = 0; i < gs.getSize(); i++){
            for(int j = 0; j < gs.getSize(); j++){
                if( (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)){
                    s += "__";
                }
                else{
                    CellType cell = gs.getPos(j, i);
                    switch   (cell) {
                        case EMPTY:
                            s += "em";
                            break;
                        case P1:
                            s+= "p1";
                            break;
                       case P2:
                            s+= "p2";
                            break;
                       case P1Q:
                          s+= "q1";
                            break;  
                       case P2Q:
                          s+= "q2";
                            break;
                    }
                }
                
                s += " ";
            }
            s += "\n";
        }
        System.out.println(s);
    }
    
}