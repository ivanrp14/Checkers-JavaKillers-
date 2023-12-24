/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.epsevg.prop.checkers.players.javakillers.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivanr
 */
public class Movement {
    private List<Point> path;
    private List<Point> killsPoints;

    public Movement(List<Point> path, List<Point> killsPoints) {
        this.path = path;
        this.killsPoints = killsPoints;
    }

    public Movement(List<Point> path) {
        this.path = path;
        this.killsPoints = new ArrayList<>();
    }

    public List<Point> getPath() {
        return path;
    }

    public List<Point> getKillsPoints() {
        
        return killsPoints;
    }
    
    
    
}
