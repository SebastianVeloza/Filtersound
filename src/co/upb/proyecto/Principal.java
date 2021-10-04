package co.upb.proyecto;

import co.ubp.clases.ArchivoNIO;
import co.ubp.clases.CalcMatriz;
import co.ubp.clases.CalculoRegresion;
import co.ubp.clases.Matriz;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Principal {

    public static List<BigDecimal[]> listaCoeficientes = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ArchivoNIO objArchivo = new ArchivoNIO("data3.txt");
        BigDecimal[] arregloX, arregloY;
        objArchivo.arreglosXyY();

        arregloX = objArchivo.arregloX;
        arregloY = objArchivo.arregloY;

        divisionSegmentos(arregloX, arregloY);
    }

    public static void divisionSegmentos(BigDecimal[] arrX, BigDecimal[] arrY) {
        BigDecimal rCuadLim = new java.math.BigDecimal("0.83");
        int tamaño = arrX.length, particion;
        BigDecimal[] xNuevo1, xNuevo2, yNuevo1, yNuevo2;

        if (regresion(arrX, arrY, false).compareTo(rCuadLim) < 0) {
            if (tamaño % 2 == 0) {
                particion = tamaño / 2;

            } else {
                particion = (tamaño + 1) / 2;
            }
            xNuevo1 = new BigDecimal[particion];
            yNuevo1 = new BigDecimal[particion];
            for (int i = 0; i < particion; i++) {
                xNuevo1[i] = arrX[i];
                yNuevo1[i] = arrY[i];
            }

            xNuevo2 = new BigDecimal[tamaño - particion];
            yNuevo2 = new BigDecimal[tamaño - particion];
            for (int i = particion; i < tamaño; i++) {
                xNuevo2[i - particion] = arrX[i];
                yNuevo2[i - particion] = arrY[i];
            }

            if (xNuevo1.length >= 6) {

                divisionSegmentos(xNuevo1, yNuevo1);
            }
            if (xNuevo2.length >= 6) {
                divisionSegmentos(xNuevo2, yNuevo2);
            }

        }

    }

    public static BigDecimal regresion(BigDecimal[] arregloX, BigDecimal[] arregloY, Boolean interpolado) {
        BigDecimal rCuadLim = new java.math.BigDecimal("0.83");
        BigDecimal rCuad = new java.math.BigDecimal("0.0");
        BigDecimal[][] cof = null;
        BigDecimal[] res = null;
        BigDecimal[][] cofYres;
        BigDecimal[] coeficientesRegresion = new BigDecimal[2];

        int gradoLimite = 10;
        int grado = 1;
        int n = arregloX.length;

        while (rCuad.compareTo(rCuadLim) < 0 && grado <= gradoLimite) {

            cof = new BigDecimal[grado + 1][grado + 1];
            res = new BigDecimal[grado + 1];
            Matriz.cf(arregloX, arregloY, grado, n, cof, res); //Se crea la matriz de regresion cof 

            cofYres = CalcMatriz.construirMatriz(cof, res); //Se unen los coeficientes con los resultados      

            coeficientesRegresion = CalcMatriz.gaussJordan(cofYres); //Se calculan los coeficientes en base a la matriz

            rCuad = CalculoRegresion.calculoRcuad(arregloY, arregloX, coeficientesRegresion);
            grado++;
        }

        if (rCuad.compareTo(rCuadLim) >= 0) {
            listaCoeficientes.add(coeficientesRegresion);
            System.out.println("Cantidad datos: " + n);
            System.out.println("R^2: " + rCuad);
        } else if (arregloX.length < 12 || interpolado) {
            List<BigDecimal[]> resul;
            resul = interpolacionRepetida(arregloX, arregloY);
            arregloX = resul.get(0);
            arregloY = resul.get(1);

            if (arregloX.length > 1000) {
                listaCoeficientes.add(coeficientesRegresion);
                System.out.println("Cantidad datos: " + n);
                System.out.println("R^2: " + rCuad);
                System.out.println("Interpolado");
                return new java.math.BigDecimal("0.84");
            }

            return regresion(arregloX, arregloY, true);
        }
        return rCuad;
    }

    public static BigDecimal[] interpolacion(BigDecimal[] x, BigDecimal[] y) {

        int gradoInterpolacion = 1;

        BigDecimal[] coeficientes = new BigDecimal[gradoInterpolacion + 1];

        BigDecimal resultado, y2, y1, x2, x1, resta1, resta2, mult;

        coeficientes[0] = y[0];

        y2 = y[1];
        y1 = y[0];
        x2 = x[1];
        x1 = x[0];

        resta1 = y2.subtract(y1);
        resta2 = x2.subtract(x1);

        resultado = resta1.divide(resta2, 100, RoundingMode.HALF_UP);

        coeficientes[1] = resultado;

        BigDecimal xMedio = (x[1].add(x[0])).divide(new java.math.BigDecimal("2.0"));

        mult = coeficientes[1].multiply(xMedio.subtract(x[0]));
        BigDecimal yXmedio = coeficientes[0].add(mult);

        BigDecimal[] arrXyYNuevos = {xMedio, yXmedio};

        return arrXyYNuevos;
    }

    public static List<BigDecimal[]> interpolacionRepetida(BigDecimal[] arrX, BigDecimal[] arrY) {
        List<BigDecimal> xLista = new ArrayList<>();
        List<BigDecimal> yLista = new ArrayList<>();
        List<BigDecimal[]> res = new ArrayList<>();

        BigDecimal[] grupoX = new BigDecimal[2];
        BigDecimal[] grupoY = new BigDecimal[2];
        BigDecimal[] arrXyYNuevos;

        for (int i = 0; i < arrX.length - 1; i++) {
            grupoX[0] = arrX[i];
            grupoX[1] = arrX[i + 1];
            grupoY[0] = arrY[i];
            grupoY[1] = arrY[i + 1];
            xLista.add(arrX[i]);
            yLista.add(arrY[i]);
            if (arrX[i].compareTo(arrX[i + 1]) != 0) {
                arrXyYNuevos = interpolacion(grupoX, grupoY);
                xLista.add(arrXyYNuevos[0]);
                yLista.add(arrXyYNuevos[1]);
            }

        }
        xLista.add(arrX[arrX.length - 1]);
        yLista.add(arrY[arrY.length - 1]);

        arrX = xLista.toArray(arrX);
        arrY = yLista.toArray(arrY);
        res.add(arrX);
        res.add(arrY);
        return res;
    }

}
