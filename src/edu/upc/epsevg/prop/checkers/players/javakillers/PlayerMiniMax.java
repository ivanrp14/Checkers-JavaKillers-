/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers;

import edu.upc.epsevg.prop.checkers.GameStatus;
import edu.upc.epsevg.prop.checkers.IPlayer;
import edu.upc.epsevg.prop.checkers.PlayerMove;


/**
 *
 * @author ivanr
 */
public class PlayerMiniMax implements IPlayer{
    
    private long nodesExplorats;
    final private int infinito = Integer.MAX_VALUE;
    
    public int moviment(Tauler t, int color)
    {
      //int col = (int)(t.getMida() * Math.random());
      this.color = color; //Guardem el color que ens passen com a paràmetre
      System.out.println(color);
      tiradas = 0;
      tiradas ++;
      int mejorMovimiento = MinMax(t, depth);
      System.out.println("S'ha decit tirar a la columna "+mejorMovimiento+" havent explorat jugades"+tiradas);
      return mejorMovimiento;
    }

    public Activitat2(int depth){
        this.depth = depth;
    }

    public String nom()
    {
      return nom;
    }
  
    public int MinMax(Tauler t, int depth){
        int col = 0;
        Integer valor = -infinito-1;
        int alfa = -infinito;
        int beta = infinito;
        for (int i = 0; i < t.getMida(); ++i){
            if(t.movpossible(i)){
                Tauler auxiliar = new Tauler(t); //Creem una copia del tauler actual
                auxiliar.afegeix(i,color); //Fem el moviment que hem comprovat com a possible
                int min = valorMinim(auxiliar, i, alfa, beta, depth-1);
                if (valor < min){
                    col = i;
                    valor = min;
                }
                if (beta < valor){
                    return col;
                }
                alfa = Math.max(valor,alfa);
            }
        }
        return col;
    }
   
    /**
    * Mètode que ens ajuda a calcular la funció de minmax donant-nos el màxim
    *
    t = Tauler actual sobre el que fem el moviment
    col = columna sobre la qual s'ha fet l'ultima jugada
    alfa = valor de alfa de la poda
    beta = valor de beta de la poda.
    depth = profunditat del arbre que mira les jugades possibels
    */
    public int valorMàxim(Tauler t, int col, int alfa, int beta, int depth){
        if(t.solucio(col, -color)) //Si el moviment a la columna i és solució del contrari
            return -infinito;
        if(depth > 0){ //Si encara no hem arribat a les fulles
            Integer valor = -infinito-1;
            for (int i = 0; i < t.getMida(); ++i){
                if(t.movpossible(i)){
                    Tauler auxiliar = new Tauler(t);
                    auxiliar.afegeix(i,color); ////Creem un tauler auxiliar per ajudar-nos amb l'algorisme
                    valor = Math.max(valor, valorMinim(auxiliar,i, alfa, beta, depth-1));
                    if (beta < valor){
                        return valor;
                    }
                    alfa = Math.max(valor,alfa);
                }
            }
            return valor;
        }else{ //Si estem a una fulla que no és solució del rival mirem la heuristica
            return calculHeuristica(t, col);
        }
        
    }
    /**
    * Mètode que ens ajuda a calcular la funció de minmax donant-nos el mínim
    *
    t = Tauler actual sobre el que fem el moviment
    col = columna sobre la qual s'ha fet l'ultima jugada
    alfa = valor de alfa de la poda
    beta = valor de beta de la poda.
    depth = profunditat del arbre que mira les jugades possibels
    */
    public int valorMinim(Tauler t, int col, int alfa, int beta, int depth){
        if(t.solucio(col, color))   //Si tenim que el moviment a la col i és solució per nosaltres tornem +infinit 
            return infinito;
        if(depth > 0){//Si encara no hem arribat a les fulles
            Integer valor = infinito-1;
            for (int i = 0; i < t.getMida(); ++i){
                if(t.movpossible(i)){
                    Tauler auxiliar = new Tauler(t); //Creem un tauler auxiliar per ajudar-nos amb l'algorisme
                    auxiliar.afegeix(i,-color);
                    valor = Math.min(valor, valorMàxim(auxiliar,i, alfa, beta, depth-1));
                    if (valor < alfa){
                        return valor; 
                    }
                    beta = Math.min(valor,beta);
                }
            }
            return valor;
        }
        else{ //Si estem a una fulla que no és solució del rival mirem la heuristica
            return calculHeuristica(t, col); /*Nomès entra si depth = 0, i per tant, que hem arribat a la profunditat indicada
            Hem de tenir en compte que partim d'una profunditat != 0, per exemple, 2 i per cada iteració de minmax reduim 
            en 1 el valor de la variable depth. Per tant, com ha de pasar nomès calculem la funció heurística a les fulles
            del nostre arbre de minmax*/
        }
    }

    @Override
    public PlayerMove move(GameStatus gs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void timeout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
