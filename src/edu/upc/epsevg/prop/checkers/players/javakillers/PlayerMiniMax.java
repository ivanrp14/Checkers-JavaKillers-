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
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Node;
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.TranspositionTable;
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Zobrist;
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
    private final int CHECK_VALUE = 1;
    private final int QUEEN_VALUE = 1;
    private Zobrist z;
    private TranspositionTable tt;
    private int trobats;
    private  long timeTT, antTimeTT;
   
    
    public PlayerMiniMax(int d, float gb) {
        this.name = "MiniMax";
        this.exploredNodes = 0;
        this.depth = d;
        this.maxDepthReached = 0;
        z = new Zobrist();
        tt = new TranspositionTable(gb);
        System.out.println("Created TransTable withsize = "+ tt.table.length);
      
    }
    
    
    

    @Override
    public PlayerMove move(GameStatus gs) {
        trobats = 0;
        long hash = z.calculateFullHash(gs);
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
        List<Double> lv = new ArrayList<>();
        if(gs.currentPlayerCanMove()){
            for (int i = 0; i < movementsList.size(); i++) {
                MoveNode moveNode = movementsList.get(i);
                for(List<Point> path : getAllPaths(moveNode)){
                    GameStatus gs_copy = new GameStatus(gs);
                    double childNodeValue;
                    if(path.size() > 0){
                        long h = move( path, gs_copy, hash, maximizerPlayer, moveNode);
                        long ant = System.currentTimeMillis();
                        Node n = tt.get(h);
                        if(n != null){
                            childNodeValue = n.eval;
                             antTimeTT += System.currentTimeMillis() - ant;
                        }
                       
                        else{
                            childNodeValue = MiniMax(gs_copy,depth -1 , alfa, beta, false, h); //Comprobar isMaximizer
                           
                        }
                        lv.add(childNodeValue);
                        if (actualNodeValue < childNodeValue) {
                            bestMovement = path;
                            actualNodeValue = childNodeValue;
                        }
                        
                    }
                   
                    
                }
            }
            
        }
        else gs.forceLoser();
        System.err.println("Valors de moviments: " + lv+"     bestmv = "+ actualNodeValue+" TTtime = "+timeTT);
        System.out.println(" > Per aquest moviment s'han explorat " + exploredNodes + " jugades finals en :"+(System.currentTimeMillis()-timeant)+"ms");
        
        return new PlayerMove(bestMovement, exploredNodes, maxDepthReached, SearchType.MINIMAX);
        
        
       
    }
    
    
    
   public double MiniMax(GameStatus gs, int d, double alfa, double beta, boolean isMaximizer, long hash) {
        if(depth - d > maxDepthReached) maxDepthReached++;
        double actualNodeValue = 0;
        if (d == 0) {
            exploredNodes++;
            return Heuristic(gs);
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
                        MoveNode moveNode = movementsList.get(i);
                        for(List<Point> path : getAllPaths(moveNode)){
                            GameStatus gs_copy = new GameStatus(gs);
                            long h = move(path, gs_copy, hash, minimizerPlayer, moveNode);
                            long ant = System.currentTimeMillis();
                            Node n = tt.get(h);
                            if(n != null){
                                actualNodeValue = n.eval;
                                timeTT +=System.currentTimeMillis() - ant;
                            }
                            else{
                                actualNodeValue = Math.max(actualNodeValue,
                                    MiniMax(gs_copy, d - 1,  alfa, beta, false, h));
                                tt.set(new Node(actualNodeValue), h);
                            }
                            
                            
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
                        MoveNode moveNode = movementsList.get(i);
                        for(List<Point> path : getAllPaths(moveNode)){
                            GameStatus gs_copy = new GameStatus(gs);
                            long h = move(path, gs_copy,hash,maximizerPlayer, moveNode);
                            long ant = System.currentTimeMillis();
                            Node n = tt.get(h);
                            if(n != null){
                                actualNodeValue = n.eval;
                                timeTT +=System.currentTimeMillis() - ant;
                            }
                            else{
                                actualNodeValue = Math.min(actualNodeValue,
                                    MiniMax(gs_copy, d - 1,  alfa, beta, true, h));
                                tt.set(new Node(actualNodeValue), h);
                            }
                            

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


        int h = count(gs, maximizerPlayer, minimizerPlayer);


        
        return h;
        
        
    }
    private int count(GameStatus gs, PlayerType max, PlayerType min) {
        int score = 0;
        for( int i = 0; i < gs.getSize(); i++){
            for (int j = 0; j < gs.getSize(); j++) {
                CellType cell = gs.getPos(i, j);
                if(cell.getPlayer() == max){
                   score += CHECK_VALUE;
                   if(cell.isQueen()) score += QUEEN_VALUE;
                }
                else if(cell.getPlayer() == min){
                    score -= CHECK_VALUE;
                   if(cell.isQueen()) score -= QUEEN_VALUE;
                }
            }
        }
        return score;
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
        System.out.println("-------------------------\n");
    }
    private long move(List<Point> move, GameStatus gs, long hash, PlayerType player,  MoveNode moveNode){
        long ant = System.currentTimeMillis();
        Point initialPos = move.get(0);
        Point finalPos = move.get(move.size() - 1);
        CellType c = gs.getPos(initialPos.x, initialPos.y);
        
        hash ^= z.table[initialPos.x][initialPos.y][Zobrist.indexOf(CellType.EMPTY)];
        hash ^= z.table[finalPos.x][finalPos.y][Zobrist.indexOf(c)];
        if(moveNode.isJump()  &&  move.contains(moveNode.getJumpedPoint())){
            Point killPos = moveNode.getJumpedPoint();
            CellType killedC = gs.getPos(killPos);
            hash ^= z.table[killPos.x][killPos.y][Zobrist.indexOf(CellType.EMPTY)];
            
        }
        timeTT += System.currentTimeMillis()-ant;
        gs.movePiece(move);
        
        return hash;
    }
    
   
    
}