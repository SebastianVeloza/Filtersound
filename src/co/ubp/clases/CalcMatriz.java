package co.ubp.clases;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcMatriz {

    public static BigDecimal[][] construirMatriz(BigDecimal[][] cof, BigDecimal[] res) {

        BigDecimal[][] matriz = new BigDecimal[cof.length][cof.length + 1];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (j != matriz[i].length - 1) {
                    matriz[i][j] = cof[i][j];
                } else {
                    matriz[i][j] = res[i];
                }
            }
        }

        return matriz;
    }

    public static BigDecimal[] gaussJordan(BigDecimal[][] matriz) {

        BigDecimal[] filModificadora;
        BigDecimal valModificador;
        BigDecimal valAmodificar;

        for (int i = 0; i < matriz.length; i++) {

            filModificadora = matriz[i].clone();

            valModificador = filModificadora[i];

            for (int j = 0; j < matriz[i].length; j++) { //Se divide toda la fila para generar el 1

                matriz[i][j] = matriz[i][j].divide(valModificador, 100, RoundingMode.HALF_UP);

            }

            for (int j = 0; j < matriz.length; j++) { // recorre las filas con los demas valores
                filModificadora = matriz[i].clone(); //obtengo la fila con la que le hago operaciones a las otras
                valAmodificar = matriz[j][i];

                if (j == i) {
                    continue;
                }

                for (int k = 0; k < filModificadora.length; k++) { //Se multiplica la fila modificadora por el contrario del val a modificar
                    filModificadora[k] = filModificadora[k].multiply(new java.math.BigDecimal("-1.0")).multiply(valAmodificar);
                }

                for (int k = 0; k < matriz[j].length; k++) { //Se resta la fila a modificar por la fila modificadora
                    matriz[j][k] = matriz[j][k].add(filModificadora[k]);
                }

            }
        }

        BigDecimal[] raices = new BigDecimal[matriz[1].length - 1];
        int ultimaPosicion = matriz[1].length - 1;
        BigDecimal[] fila;

        for (int i = 0; i < matriz.length; i++) {
            fila = matriz[i];
            raices[i] = fila[ultimaPosicion];
        }

        return raices;
    }

}
