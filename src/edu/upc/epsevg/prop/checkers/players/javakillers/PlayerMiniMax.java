/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers;

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
    private int depth;
    private PlayerType maximizerPlayer, minimizerPlayer;

    public PlayerMiniMax(int d) {
        this.name = "MiniMax";
        this.exploredNodes = 0;
        this.depth = d;
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
                System.out.println(path);
                gs.movePiece(path);
                double childNodeValue = MiniMax(gs_copy, i, depth - 1, alfa, beta, true); //Comprobar isMaximizer
                if (actualNodeValue < childNodeValue) {
                    bestMovement = path;
                    actualNodeValue = childNodeValue;
                }
            }
            
        }


        System.out.println(" > Per aquest moviment s'han explorat " + exploredNodes + " jugades finals en :"+(System.currentTimeMillis()-timeant)+"ms");

        return new PlayerMove(bestMovement, exploredNodes, depth, SearchType.MINIMAX);
    }
    
    
    
   public double MiniMax(GameStatus gs, int profunditat, int color, double alfa, double beta, boolean isMaximizer) {
    if (gs.isGameOver()) {
        if (gs.checkGameOver()) {
            if (gs.GetWinner() == maximizerPlayer) return Double.POSITIVE_INFINITY;
            else return Double.NEGATIVE_INFINITY;
        } else {
            return 0;
        }
    } else if (profunditat == 0) {
        exploredNodes++;
        return Heuristic(gs);
    }

    double actualNodeValue;
    if (isMaximizer) {
        actualNodeValue = Double.NEGATIVE_INFINITY;
        List<MoveNode> movementsList = gs.getMoves();
        for (int i = 0; i < movementsList.size(); i++) {
            for (List<Point> path : getAllPaths(movementsList.get(i))) {
                GameStatus gs_copy = new GameStatus(gs);
                gs_copy.movePiece(path);

                actualNodeValue = Math.max(actualNodeValue,
                        MiniMax(gs_copy, profunditat - 1, color, alfa, beta, false));

                alfa = Math.max(actualNodeValue, alfa);
                if (beta <= alfa) {
                    break;
                }
            }
        }
    } else {
        actualNodeValue = Double.POSITIVE_INFINITY;
        List<MoveNode> movementsList = gs.getMoves();
        for (int i = 0; i < movementsList.size(); i++) {
            for (List<Point> path : getAllPaths(movementsList.get(i))) {
                GameStatus gs_copy = new GameStatus(gs);
                gs_copy.movePiece(path);

                actualNodeValue = Math.min(actualNodeValue,
                        MiniMax(gs_copy, profunditat - 1, color, alfa, beta, true));

                beta = Math.min(actualNodeValue, beta);
                if (beta <= alfa) {
                    break;
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
}