/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psomcl.tesis;

/**
 *
 * @author Estefanis
 */
public class Metricas {

    private final double entropia;
    private final double contraste;
    private final String path;

    public Metricas(double entropia, double contraste, String path) {
        this.entropia = entropia;
        this.contraste = contraste;
        this.path = path;
    }

    public double getEntropia() {
        return entropia;
    }

    public double getContraste() {
        return contraste;
    }

    public String getPath() {
        return path;
    }

}
