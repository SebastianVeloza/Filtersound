/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ubp.clases;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author USER
 */
public class CalculoRegresion {

    public static BigDecimal calculoSt(BigDecimal[] datosY) {
        BigDecimal Yprom = new java.math.BigDecimal("0.0");
        BigDecimal st = new java.math.BigDecimal("0.0");
        BigDecimal resta;
        

        for (int i = 0; i < datosY.length; i++) {
            Yprom = Yprom.add(datosY[i]);
        }

        Yprom = Yprom.divide(new java.math.BigDecimal(datosY.length), 100, RoundingMode.HALF_UP);   
        
        for (int i = 0; i < datosY.length; i++) {
            resta = datosY[i].subtract(Yprom);
            st = st.add(resta.pow(2));
        }
//        System.out.println("St " + st);
        return st;
    }
    
    public static BigDecimal calculoSr(BigDecimal[] datosY, BigDecimal[] datosYEst) {
        BigDecimal sr = new java.math.BigDecimal("0.0");
        BigDecimal resta;
      
        
        for (int i = 0; i < datosY.length; i++) {
            resta = datosY[i].subtract(datosYEst[i]);
            sr = sr.add(resta.pow(2));
        }
        
        return sr;
    }
    
    public static BigDecimal[] calculoDatosYEst(BigDecimal[] datosX, BigDecimal[] coef){
        BigDecimal[] arrYEst = new BigDecimal[datosX.length];
        BigDecimal yEst;
        BigDecimal potenciaX;
        int contPos = 0;
        for (BigDecimal x : datosX) {
            
            yEst = new java.math.BigDecimal("0.0");
            for (int i = 0; i < coef.length; i++) {
                potenciaX = x.pow(i);
                potenciaX = potenciaX.multiply(coef[i]); 
                yEst = yEst.add(potenciaX);
            }
            arrYEst[contPos] = yEst;
            contPos++;
        }
        
        return arrYEst;
    }
    
    public static BigDecimal calculoRcuad(BigDecimal[] datosY, BigDecimal[] datosX, BigDecimal[] coef){
        BigDecimal sr;
        BigDecimal st;
        BigDecimal rCuad;
        BigDecimal resta;
        BigDecimal[] datosYEst;
        datosYEst = calculoDatosYEst(datosX, coef);
        
        sr = calculoSr(datosY, datosYEst);
        st = calculoSt(datosY);
        
        resta = st.subtract(sr);
        
        rCuad = resta.divide(st, 15, RoundingMode.HALF_UP); 
        
        return rCuad;       
    }
}
