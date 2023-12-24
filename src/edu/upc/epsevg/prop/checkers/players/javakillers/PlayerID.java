/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers;

import edu.upc.epsevg.prop.checkers.CellType;
import static edu.upc.epsevg.prop.checkers.CellType.EMPTY;
import static edu.upc.epsevg.prop.checkers.CellType.P1;
import static edu.upc.epsevg.prop.checkers.CellType.P1Q;
import static edu.upc.epsevg.prop.checkers.CellType.P2;
import static edu.upc.epsevg.prop.checkers.CellType.P2Q;
import edu.upc.epsevg.prop.checkers.GameStatus;
import edu.upc.epsevg.prop.checkers.IAuto;
import edu.upc.epsevg.prop.checkers.IPlayer;
import edu.upc.epsevg.prop.checkers.MoveNode;
import edu.upc.epsevg.prop.checkers.PlayerMove;
import edu.upc.epsevg.prop.checkers.PlayerType;
import edu.upc.epsevg.prop.checkers.SearchType;
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Movement;
import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Node;

import edu.upc.epsevg.prop.checkers.players.javakillers.utils.Zobrist;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa un jugador de damas utilizando el algoritmo MiniMax.
 * Comentarios agregados para explicar el código.
 */
public class PlayerID implements IPlayer, IAuto {

    // Atributos de la clase
    private String name;
    private long exploredNodes;
    private int depth, maxDepthReached;
    private PlayerType maximizerPlayer, minimizerPlayer;
    private Zobrist z;
    private Map<Long, Node> tt;
    private boolean timeOut;

    // Constructor de la clase
    public PlayerID() {
        this.name = "MiniMax";
        this.exploredNodes = 0;
        this.maxDepthReached = 0;
        this.timeOut = false;
        z = new Zobrist();
        tt = new HashMap<>();
    }

    // Método principal para realizar un movimiento
    public PlayerMove move(GameStatus gs) {
        timeOut = false;
        long hash = z.calculateFullHash(gs);
        maximizerPlayer = gs.getCurrentPlayer();
        minimizerPlayer = PlayerType.opposite(maximizerPlayer);
        exploredNodes = 0;
        depth = 1;
        double alfa = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        PlayerMove pm = null;
        while (!timeOut) {
            pm = Minimax(gs, depth, alfa, beta, hash);
            depth++;
        }
        return pm;
    }

     // Implementación del algoritmo MiniMax
    private PlayerMove Minimax(GameStatus gs, int d, double alfa, double beta, long hash) {
        List<Movement> allMoves = getAllPaths(gs.getMoves());
        List<Double> lv = new ArrayList<>();
        int bestMoveIndex = 0;
        double actualNodeValue = Double.NEGATIVE_INFINITY;
        double childNodeValue;

        if (gs.currentPlayerCanMove()) {
            Node n = tt.get(hash);
            if (n != null) {
                Movement mov0 = allMoves.get(0);
                Movement best = allMoves.get(n.bestMoveIndex);
                allMoves.set(0, best);
                allMoves.set(n.bestMoveIndex, mov0);
            }

            for (int i = 0; i < allMoves.size(); i++) {
                Movement move = allMoves.get(i);
                GameStatus gsCopy = new GameStatus(gs);
                if (!move.getPath().isEmpty()) {
                    long h = makeMove(gsCopy, move, hash);
                    childNodeValue = MiniMax_r(gsCopy, d - 1, alfa, beta, h);
                    lv.add(childNodeValue);
                    if (childNodeValue > actualNodeValue) {
                        bestMoveIndex = i;
                        actualNodeValue = childNodeValue;
                    }
                }
            }
        } else {
            gs.forceLoser();
        }

        return new PlayerMove(allMoves.get(bestMoveIndex).getPath(), exploredNodes, maxDepthReached, SearchType.MINIMAX);
    }

    private double MiniMax_r(GameStatus gs, int d, double alfa, double beta, long hash) {
        double actualNodeValue = 0;
        int bestMoveIndex = 0;
        double childNodeValue;
        exploredNodes++;

        if (depth - d > maxDepthReached) maxDepthReached++;

        Node n = tt.get(hash);

        if (d == 0) {
            if(n != null)return n.eval;
            return Heuristic(gs);
        } else if (d > 0) {
            if (gs.isGameOver()) {
                if (gs.checkGameOver()) {
                    if (gs.GetWinner() == maximizerPlayer) return Double.POSITIVE_INFINITY;
                    else if (gs.GetWinner() == minimizerPlayer) return Double.NEGATIVE_INFINITY;
                }
            } else {
                long h;
                List<Movement> allMoves = getAllPaths(gs.getMoves());
                if (n != null) {
                    Movement mov0 = allMoves.get(0);
                    Movement best = allMoves.get(n.bestMoveIndex);
                    allMoves.set(0, best);
                    allMoves.set(n.bestMoveIndex, mov0);
                }

                if (gs.getCurrentPlayer() == maximizerPlayer) {
                    actualNodeValue = Double.NEGATIVE_INFINITY;
                    for (int i = 0; i < allMoves.size(); i++) {
                        Movement move = allMoves.get(i);
                        GameStatus gsCopy = new GameStatus(gs);
                        h = makeMove(gsCopy, move, hash);
                        childNodeValue = MiniMax_r(gsCopy, d - 1, alfa, beta, h);

                        if (childNodeValue > actualNodeValue) {
                            bestMoveIndex = i;
                            actualNodeValue = childNodeValue;
                        }

                        alfa = Math.max(actualNodeValue, alfa);
                        if (beta <= alfa) {
                            break;
                        }
                    }
                } else {
                    actualNodeValue = Double.POSITIVE_INFINITY;
                    for (int i = 0; i < allMoves.size(); i++) {
                        Movement move = allMoves.get(i);
                        GameStatus gsCopy = new GameStatus(gs);
                        h = makeMove(gsCopy, move, hash);
                        childNodeValue = MiniMax_r(gsCopy, d - 1, alfa, beta, h);

                        if (childNodeValue < actualNodeValue) {
                            bestMoveIndex = i;
                            actualNodeValue = childNodeValue;
                        }

                        beta = Math.min(actualNodeValue, beta);
                        if (beta <= alfa) {
                            break;
                        }
                    }
                }

                if (n == null || n.depth < (depth - d)) tt.put(hash, new Node(actualNodeValue, bestMoveIndex, depth));
            }
        }
        return actualNodeValue;
    }
   
 private double Heuristic(GameStatus gs) {
        // Definir los valores de las piezas y situaciones
        final double PAWN_VALUE = 5;
        final double KING_VALUE = 7.75;
        final double BACK_ROW_BONUS = 4;
        final double CENTER_CONTROL_BONUS = 2.5;
        final double EDGE_CENTER_CONTROL_BONUS = 0.5;
        final double VULNERABLE_PIECE_PENALTY = -3;
        final double PROTECTED_PIECE_BONUS = 3;

        // Obtener el tamaño del tablero
        int size = gs.getSize();

        // Inicializar los contadores
        double score = 0;

        // Analizar cada celda del tablero
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                CellType cell = gs.getPos(x, y);
                if (cell == CellType.EMPTY) {
                    continue;
                }

                // Determinar si la ficha es del jugador actual
                boolean isMaximizerPiece = cell.getPlayer() == maximizerPlayer;

                // Calcula el valor base de la pieza
                double pieceValue = cell.isQueen() ? KING_VALUE : PAWN_VALUE;
                if (!isMaximizerPiece) {
                    pieceValue = -pieceValue;
                }

                // Comprobar y añadir bonificaciones o penalizaciones
                if (isInBackRow(y, size)) {
                     pieceValue += isMaximizerPiece ? BACK_ROW_BONUS : -BACK_ROW_BONUS;
                }
                if (isInCenterControlArea(x, y, size)) {
                    pieceValue += isMaximizerPiece ? CENTER_CONTROL_BONUS : -CENTER_CONTROL_BONUS;
                } else if (isInEdgeCenterControlArea(x, y, size)) {
                    pieceValue += isMaximizerPiece ? EDGE_CENTER_CONTROL_BONUS : -EDGE_CENTER_CONTROL_BONUS;
                }

                // Verificar si la pieza es vulnerable o protegida
                if (isPieceVulnerable(gs, x, y)) {
                    pieceValue += isMaximizerPiece ? VULNERABLE_PIECE_PENALTY : -VULNERABLE_PIECE_PENALTY;
                }
                if (isPieceProtected(gs, x, y)) {
                    pieceValue += isMaximizerPiece ? PROTECTED_PIECE_BONUS : -PROTECTED_PIECE_BONUS;
                }

                // Añadir el valor de la pieza al total
                score += pieceValue;
            }
        }
        return score;
    }
    
    private boolean isInBackRow(int y, int size) {
        // La primera y última fila son consideradas filas de atrás
        int firstRow = 0;
        int lastRow = size - 1;

        // Verifica si la pieza está en la primera o la última fila
        return y == firstRow || y == lastRow;
    }
    
    private boolean isInCenterControlArea(int x, int y, int size) {
        // Calcula las filas y columnas que definen el área central
        int middleRowStart = size / 2 - 1;
        int middleRowEnd = size / 2;
        int middleColStart = size / 2 - 2;
        int middleColEnd = size / 2 + 1;

        // Verifica si la pieza está en las filas y columnas centrales
        return (x >= middleColStart && x <= middleColEnd) && (y == middleRowStart || y == middleRowEnd);
    }
    
    private boolean isInEdgeCenterControlArea(int x, int y, int size) {
        // Define las filas centrales
        int middleRowStart = size / 2 - 1;
        int middleRowEnd = size / 2;

        // Verifica si la pieza está en una de las filas centrales
        boolean isInMiddleRow = y == middleRowStart || y == middleRowEnd;
        if (!isInMiddleRow) {
            return false;
        }

        // Define las columnas del borde del área central
        // Asumiendo un tablero de 8x8, estas serían las columnas 0, 1, 6, 7
        int edgeColStart1 = 0;
        int edgeColEnd1 = 1;
        int edgeColStart2 = size - 2;
        int edgeColEnd2 = size - 1;

        // Verifica si la pieza está en una de las columnas del borde del área central
        return (x >= edgeColStart1 && x <= edgeColEnd1) || (x >= edgeColStart2 && x <= edgeColEnd2);
    }
    
    private boolean isPieceVulnerable(GameStatus gs, int x, int y) {
        CellType cell = gs.getPos(x, y);
        if (cell == CellType.EMPTY) {
            return false;
        }

        boolean isQueen = cell == CellType.P1Q || cell == CellType.P2Q;
        PlayerType playerType = cell.getPlayer();

        // Direcciones específicas de verificación según el tipo de pieza
        int[][] checkDirections = getPieceDirectionsForVulne(isQueen, playerType);

        for (int[] dir : checkDirections) {
            int enemyX = x + dir[0];
            int enemyY = y + dir[1];
            int jumpX = x + 2 * dir[0];
            int jumpY = y + 2 * dir[1];

            // Verifica si hay una pieza enemiga y un espacio vacío para completar la captura
            if (isValidPosition(enemyX, enemyY, gs.getSize()) && 
                gs.getPos(enemyX, enemyY).getPlayer() == PlayerType.opposite(playerType) &&
                isValidPosition(jumpX, jumpY, gs.getSize()) && 
                gs.getPos(jumpX, jumpY) == CellType.EMPTY) {
                return true;
            }
        }
        return false;
    }

    private int[][] getPieceDirectionsForVulne(boolean isQueen, PlayerType playerType) {
        if (isQueen) {
            return new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        } else {
            if (playerType == PlayerType.PLAYER1) {
                return new int[][]{{1, -1}, {1, 1}};
            } else {
                return new int[][]{{-1, -1}, {-1, 1}};
            }
        }
    }

    
    private boolean isPieceProtected(GameStatus gs, int x, int y) {
        CellType cell = gs.getPos(x, y);
        if (cell == CellType.EMPTY) {
            return false;
        }

        PlayerType playerType = cell.getPlayer();
        boolean isQueen = cell == CellType.P1Q || cell == CellType.P2Q;

        // Comprobar si está protegida por piezas aliadas o por su posición en el tablero
        if (isProtectedByAlliesOrBoard(gs, x, y, playerType, isQueen)) {
            return true;
        }

        // Comprobar si está protegida contra reinas enemigas
        if (isProtectedFromEnemyQueens(gs, x, y, playerType)) {
            return true;
        }

        return false;
    }

    private boolean isProtectedByAlliesOrBoard(GameStatus gs, int x, int y, PlayerType playerType, boolean isQueen) {
        // Direcciones para comprobar la protección, variará según si la pieza es una reina o un peón
        int[][] directions = isQueen ? new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}} :
                                        playerType == PlayerType.PLAYER1 ? new int[][]{{-1, -1}, {-1, 1}} :
                                        new int[][]{{1, -1}, {1, 1}};

        for (int[] dir : directions) {
            int allyX = x + dir[0];
            int allyY = y + dir[1];

            if (isValidPosition(allyX, allyY, gs.getSize())) {
                CellType allyCell = gs.getPos(allyX, allyY);
                if (allyCell.getPlayer() == playerType) {
                    return true; // Protegida por una pieza aliada
                }
            } else {
                return true; // Protegida por estar en el borde del tablero
            }
        }

        return false;
    }

    private boolean isProtectedFromEnemyQueens(GameStatus gs, int x, int y, PlayerType playerType) {
        // Verificar todas las direcciones diagonales para posibles reinas enemigas
        int[][] queenDirections = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : queenDirections) {
            int currentX = x, currentY = y;
            while (isValidPosition(currentX += dir[0], currentY += dir[1], gs.getSize())) {
                CellType cell = gs.getPos(currentX, currentY);
                if (cell.getPlayer() == PlayerType.opposite(playerType) && cell.isQueen()) {
                    return false; // Vulnerable a una reina enemiga
                }
                if (cell != CellType.EMPTY) {
                    break; // Bloqueada por otra pieza
                }
            }
        }
        return true; // No hay reinas enemigas que amenacen esta pieza
    }



    private boolean isValidPosition(int x, int y, int size) {
        // Verifica si la posición está dentro del tablero
        return x >= 0 && x < size && y >= 0 && y < size;
    }


 public static List<Movement> getAllPaths(List<MoveNode> roots) {
    List<Movement> allPaths = new ArrayList<>();
    for (MoveNode root : roots) {
        getAllPathsRecursive(root, new ArrayList<>(), new ArrayList<>(), allPaths);
    }
    return allPaths;
}

// Función recursiva para obtener todos los caminos posibles
private static void getAllPathsRecursive(MoveNode node, List<Point> currentPath, List<Point> killsPoints,
        List<Movement> allPaths) {
    currentPath.add(node.getPoint());

    if (node.isJump()) {
        killsPoints.add(node.getJumpedPoint());
    }

    for (MoveNode child : node.getChildren()) {
        getAllPathsRecursive(child, new ArrayList<>(currentPath), new ArrayList<>(killsPoints), allPaths);
    }

    if (node.getChildren().isEmpty()) {
        Movement movement = new Movement(new ArrayList<>(currentPath), new ArrayList<>(killsPoints));
        if(movement.getKillsPoints().size() > 0)insertSorted(movement, allPaths);
        else allPaths.add(movement);
    }
}

// Función para insertar el movimiento de manera ordenada en la lista
private static void insertSorted(Movement newMovement, List<Movement> allPaths) {
    int index = 0;
    while (index < allPaths.size() && compareMovements(newMovement, allPaths.get(index)) > 0) {
        index++;
    }
    allPaths.add(index, newMovement);
}

// Función de comparación para determinar el orden de los movimientos
private static int compareMovements(Movement movement1, Movement movement2) {
    // Implementa la lógica de comparación basada en tus criterios, por ejemplo, el número de muertes
    int deaths1 = movement1.getKillsPoints().size();
    int deaths2 = movement2.getKillsPoints().size();

    return Integer.compare(deaths1, deaths2);
}   
    
   
   
    
   
    
    // Método de interfaz, no implementado aquí
    @Override
    public void timeout() {
        // Puede agregar lógica para manejar el tiempo de espera.
        timeOut = true;
    }

    // Método de interfaz para obtener el nombre del jugador
    @Override
    public String getName() {
        return name;
    }

    // Método para imprimir el estado del tablero (no utilizado en el código principal)
    public void printGS(GameStatus gs) {
        String s = new String();
        for (int i = 0; i < gs.getSize(); i++) {
            for (int j = 0; j < gs.getSize(); j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    s += "__";
                } else {
                    CellType cell = gs.getPos(j, i);
                    switch (cell) {
                        case EMPTY:
                            s += "em";
                            break;
                        case P1:
                            s += "p1";
                            break;
                        case P2:
                            s += "p2";
                            break;
                        case P1Q:
                            s += "q1";
                            break;
                        case P2Q:
                            s += "q2";
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

    // Método para realizar un movimiento y actualizar la tabla hash
    private long makeMove(GameStatus gs, Movement move, long hash) {
        PlayerType player = gs.getCurrentPlayer();
        long h = hash;
        if (move.getPath().size() > 0) {
            Point i = move.getPath().get(0);
            Point f = move.getPath().get(move.getPath().size() - 1);
            CellType c = gs.getPos(i);
            h ^= z.table[i.x][i.y][Zobrist.indexOf(c)];

            for (Point killPoint : move.getKillsPoints()) {
                CellType kc = gs.getPos(killPoint);
                h ^= z.table[killPoint.x][killPoint.y][Zobrist.indexOf(kc)];
            }
            gs.movePiece(move.getPath());
            
            c =gs.getPos(f);
            h ^= z.table[f.x][f.y][Zobrist.indexOf(c)];
            h ^= z.turn[PlayerType.opposite(player).ordinal()];
            h ^= z.turn[player.ordinal()];
        }
        
        return h; 
    }
}