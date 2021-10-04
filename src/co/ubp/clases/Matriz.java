/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ubp.clases;

import java.math.BigDecimal;

/**
 *
 * @author ferna
 */
public class Matriz {
    public static BigDecimal pt(BigDecimal b, int ex) {
        BigDecimal pp = new java.math.BigDecimal("1.0");
        for (int i = 0; i < ex; i++) {
            pp = pp.multiply(b);         
        }
        return pp;
    }
     public static void cf(BigDecimal[]x ,BigDecimal[] y,int grado ,int N,BigDecimal [][] cof,BigDecimal[] res) {
        BigDecimal sum;BigDecimal[] sumatoria = new BigDecimal[2 * grado + 1];
        for (int i = 0; i <= 2 * grado; i++) {
            sum = new java.math.BigDecimal("0.0");
            for (int j = 0; j < N; j++) {
                sum = sum.add(pt(x[j], i));
            }
            sumatoria[i] = sum;
        }
        for (int i = 0; i <= grado; i++) {
            sum = new java.math.BigDecimal("0.0");
            for (int j = 0; j < N; j++) {
              
                sum = sum.add(pt(x[j], i).multiply(y[j]));
            }
            res[i] = sum;
        }
        for (int i = 0; i <= grado; i++) {
            for (int j = 0; j <= grado; j++) {
                cof[i][j] = sumatoria[i + j];}
        }
    }
      public static String imprimir(BigDecimal [][] cof,BigDecimal[] res) {
        String chain ="";
        for (BigDecimal[] fila : cof) {
            chain += "[ ";
            for (BigDecimal d : fila) {
                chain += d;chain += " ";}
            chain += "]";chain += "\n";}
        chain += "resultados: [";
        for (BigDecimal r : res) {
            chain += r;chain += " ";}
        chain += "]";
        return chain;
    }
}
