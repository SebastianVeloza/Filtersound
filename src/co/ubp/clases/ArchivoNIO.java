/*
 * Clase para creación de archivos secuenciales
 * Autor: Johanna Marcela Suárez Pedraza
 * Facultad de Ingeniería de Sistemas e Informática
 * UPB Bucaramanga
 * Febrero 26 de 2019
 */
package co.ubp.clases;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ArchivoNIO {

    private final Path archivo;
    private final String FINLINEA;
    public BigDecimal[] arregloX, arregloY;

    public ArchivoNIO(String nombreArchivo) throws IOException {
        archivo = Paths.get(nombreArchivo);
        FINLINEA = System.getProperty("line.separator");

        if (!Files.exists(archivo)) {
            System.out.println("*********************************************");
            System.out.println("****El archivo no existe... Creado el archivo");
            System.out.println("*********************************************");
            Files.createFile(archivo);
        }
    }

    public void agregarFila(String fila) throws IOException {
        List<String> listaLineas = new ArrayList<>();
        listaLineas.add(fila);
        Files.write(archivo, listaLineas, StandardOpenOption.APPEND);
    }

    public void arreglosXyY() throws IOException {
        int i, limite;
        String contenido, lineasContenido[];

        String[] arreglolec;

        if (Files.size(archivo) > 0) {
            contenido = new String(Files.readAllBytes(archivo));
            lineasContenido = contenido.split(FINLINEA);
            limite = lineasContenido.length;
            
            arreglolec = lineasContenido[0].split("\n");
            arregloX = new BigDecimal[arreglolec.length];
            arregloY = new BigDecimal[arreglolec.length];

            String[] linea;

            for (i = 0; i < arreglolec.length; i++) {
                
                linea = arreglolec[i].split(",");
                

                arregloX[i] = new BigDecimal(linea[0]);

                arregloY[i] = new BigDecimal(linea[1]);

            }
        } else {
            System.out.println("El archivo está vacio...");
        }
    }

}// Fin de clase
