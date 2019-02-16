/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testopencv;

import org.opencv.core.Mat;

/**
 *
 * @author Estefanis
 */
//@lombok.AllArgsConstructor
//@lombok.Data
//@lombok.NoArgsConstructor
public class Resultado {

    private double contraste;
    private double entropia;
    private Mat imageFinal;
    private String pathSalida;

    public Resultado() {
    }

    
    public Resultado(double contraste, double entropia, Mat imageFinal, String pathSalida) {
        this.contraste = contraste;
        this.entropia = entropia;
        this.imageFinal = imageFinal;
        this.pathSalida = pathSalida;
    }

    

    public double getContraste() {
        return contraste;
    }

    public void setContraste(double contraste) {
        this.contraste = contraste;
    }

    public double getEntropia() {
        return entropia;
    }

    public void setEntropia(double entropia) {
        this.entropia = entropia;
    }

    public Mat getImageFinal() {
        return imageFinal;
    }

    public void setImageFinal(Mat imageFinal) {
        this.imageFinal = imageFinal;
    }

    public String getPathSalida() {
        return pathSalida;
    }

    public void setPathSalida(String pathSalida) {
        this.pathSalida = pathSalida;
    }

    
    @Override
    public String toString() {
        return "Resultado{" + "contraste=" + contraste + ", entropia=" + entropia + '}';
    }
}
