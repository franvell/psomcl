/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testopencv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import org.opencv.imgproc.Imgproc;
import psomcl.tesis.Tesis;

/**
 *
 * @author Estefanis
 */
public class TestOpenCV {

    /**
     * @param args the command line arguments
     */
    
    private static final Logger log = Logger.getLogger(TestOpenCV.class.getName());

   

//    public static void main(String[] args) {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.out.println("Opencv-" + Core.VERSION);
//
//        System.out.println("imread");
//        Mat image = imread("C:\\Users\\user\\Documents\\NetBeansProjects\\TestOpenCV\\src\\image\\mdb001.pgm");
//        System.out.println("size:" + Arrays.toString(size(image)));
//        System.out.println("type:" + image.type());
//        double e = entropy(image);
//        double c = contraste(image);
//        Mat elemento = getElemento(1, 5);
//        
//        
//        
//        System.out.println("hola");
//
////        System.out.println("convencion");
////        Mat m = Mat.eye(3, 5, CvType.CV_8UC1);
////        System.out.println("m:" + m.dump());
////        Mat con = convencion(m);
////        print(con);
////
////        Mat z = Mat.zeros(3, 5, CvType.CV_8UC1);
////        System.out.println("z:" + z.dump());
////        System.out.println("test dilateBinario");
////        int v[] = {0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0};
////        Mat f = new Mat(4, 4, CvType.CV_8S);
////        f.put(0, 0, v);
////
////        //Mat f = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
////        System.out.println("f:" + f.dump());
////        //Mat ele = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
////        Mat ele = new Mat(3, 3, CvType.CV_8S);
////        int v1[] = {0, 1, 0, 1, 1, 0, 1, 1, 0};
////        ele.put(0, 0, v1);
////        System.out.println("ele:" + ele.dump());
////        Mat r = dilateBinario(f, ele);
////        print(r);
////        System.out.println("test nElementoGray");
////        Mat nd = nElementoGray(ele, 3);
////        print(nd);
//Resultado r = mclV3(image, elemento, 1, 2, 0.5);
//System.out.println("tttttttttttr " + r.toString());
//    }

    public static Mat imread(String path) {
//        System.out.println("path>>>"+path);
        Mat image = Imgcodecs.imread(path, CV_LOAD_IMAGE_GRAYSCALE);
        
        return image;
    }

    public static double contraste(Mat imagen) {
        Mat hist = new Mat();
        float range[] = {0, 256};
        List<Mat> lll = new ArrayList<>();
        lll.add(imagen);
        Imgproc.calcHist(lll, new MatOfInt(0), new Mat(), hist, new MatOfInt(256), new MatOfFloat(range), true);

        double inten_med = Core.mean(imagen).val[0];
        double total = imagen.total();
        double sum = 0;
        for (int i = 0; i < hist.rows(); i++) {
            double p = (hist.get(i, 0)[0]) / total;
            Double l = Math.pow(i - inten_med, 2) * p;
            if (l.isNaN()) {
                l = 0.0;
//                System.out.print("NaN");
            }
            sum = sum + l;
        }
        hist.release();
        double c = Math.sqrt(sum) / 256.0;
//        System.out.println("contraste:" + c);
        return c;
    }

    public static double entropy(Mat m) {

        Mat hist = new Mat();
        float range[] = {0, 256};
        List<Mat> lll = new ArrayList<>();
        lll.add(m);
        Imgproc.calcHist(lll, new MatOfInt(0), new Mat(), hist, new MatOfInt(256), new MatOfFloat(range), true);

        double total = m.total();
        double sum = 0.0;
        for (int i = 0; i < hist.rows(); i++) {
            double p = (hist.get(i, 0)[0]) / total;
            Double l = (p * (Math.log(p) / Math.log(2)));
            if (l.isNaN()) {
                l = 0.0;
            }
            sum = sum + l;
        }
        hist.release();
        double e = -1 * (sum / 8);
//        System.out.println("entropia:" + e);
        return e;

    }

    public static void print(Mat m) {
        System.out.println("=======================================");
        for (int i = 0; i < m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                System.out.print(" " + (int) m.get(i, j)[0]);
            }
            System.out.println("");
        }
        System.out.println("=======================================");
    }

    public static int[] size(Mat m) {
        int[] xy = new int[2];
        xy[0] = m.rows();
        xy[1] = m.cols();
        return xy;

    }

    public static Mat getElemento(int method, int dim) {
//        System.out.println("getElemento method:" + method + " dim:" + dim);
        Mat element = new Mat();
        switch (method) {
            case 1: // disk
                element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(2 * dim - 1, 2 * dim - 1));
                break;
            case 2: // diamond
                element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(2 * dim + 1, 2 * dim + 1));
                break;
            case 3:    // octagon  
                while ((dim % 3) != 0) {
                    dim++;
                }
                element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(2 * dim + 1, 2 * dim + 1));
                break;
            case 4: // rectangle
                element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(dim, dim));
                break;
            default: //square
                element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(dim, dim));
                break;
        }
//        print(element);
        return element;
    }

    public static Mat convencion(Mat m) {
        Mat image = new Mat(m.rows(), m.cols(), CvType.CV_8S);
        double[] con = new double[m.rows() * m.cols()];
        int c = 0;
        for (int i = 0; i < m.rows(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                con[c++] = (m.get(i, j)[0] == 0 ? Double.NEGATIVE_INFINITY : 0);
            }
        }
        image.put(0, 0, con);
        return image;
    }

    public static Mat dilateBinario(Mat f, Mat ele) {
        int[][] ui = new int[2][f.rows() * f.cols()];
        int[][] ue = new int[2][ele.rows() * ele.cols()];
        int ci = 0;
        // extraer las coordenadas de los 1 de la imagen
        for (int i = 0; i < f.rows(); i++) {
            for (int j = 0; j < f.cols(); j++) {
                if ((int) f.get(i, j)[0] == 1) {
                    ui[0][ci] = i;
                    ui[1][ci] = j;
                    ci++;

                }
            }
        }
        //System.out.println("ci " + ci);
        //System.out.println("ui[0] " + Arrays.toString(ui[0]));
        //System.out.println("ui[1] " + Arrays.toString(ui[1]));

        // extraer las coordenadas de los 1 de la ele
        int ce = 0;
        for (int i = 0; i < ele.rows(); i++) {
            for (int j = 0; j < ele.cols(); j++) {
                if ((int) ele.get(i, j)[0] == 1) {
                    ue[0][ce] = i;
                    ue[1][ce] = j;
                    ce++;
                }
            }
        }
        //System.out.println("ce " + ce);
        //System.out.println("ue[0] " + Arrays.toString(ue[0]));
        //System.out.println("ue[1] " + Arrays.toString(ue[1]));
        //calcula la suma
        int[][] r = new int[2][ci * ce];
        int c = 0;
        for (int i = 0; i < ci; i++) {
            for (int j = 0; j < ce; j++) {
                r[0][c] = ui[0][i] + ue[0][j];
                r[1][c] = ui[1][i] + ue[1][j];
                c++;
            }
        }
        //System.out.println("c " + c);
        //System.out.println("r[0] " + Arrays.toString(r[0]));
        //System.out.println("r[1] " + Arrays.toString(r[1]));

        //llena 1 
        int x = max(r[0]);
        int y = max(r[1]);
        Mat image = new Mat(x + 1, y + 1, CvType.CV_32S);
       
        int[][] g = new int[x + 1][y + 1];
        for (int i = 0; i < ci * ce; i++) {
            g[r[0][i]][r[1][i]] = 1;
        }

        int k = 0;
        int[] g1 = new int[(x + 1) * (y + 1)];
        for (int i = 0; i < x + 1; i++) {
            for (int j = 0; j < y + 1; j++) {
                g1[k] = g[i][j];
                k++;
            }
        }

        image.put(0, 0, g1);
       // System.out.println("putt");
        return image;
    }

    private static int max(int[] v) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < v.length; i++) {
            if (v[i] > max) {
                max = v[i];
            }
        }
        return max;
    }

    public static Mat nElementoGray(Mat ele, int n) {
//        System.out.println("nElementoGray n:" + n);
        if (n == 0) {
            return Mat.zeros(ele.rows(), ele.cols(), CvType.CV_8S);
        }
        Mat nele = ele;
        if (n >= 1) {
            for (int i = 0; i < n - 1; i++) {
                nele = dilateBinario(nele, ele);
            }
//            print(nele);
//            System.out.println("nele " + nele.type());
            nele = extraccion(nele, n - 1);
            //print(nele);
            nele = convencion(nele);
        }
//        print(nele);
        return nele;
    }

    private static Mat extraccion(Mat nele, int n) {
//        System.out.println("extraccion n:" + n);
        Mat r = Mat.zeros(nele.rows() - n, nele.cols() - n, CvType.CV_32S);
        int[] x = new int[(nele.rows() - n) * (nele.cols() - n)];
        int k = 0;
        for (int i = n; i < nele.rows(); i++) {
            for (int j = n; j < nele.cols(); j++) {
                x[k] = (int) nele.get(i, j)[0];
                k++;
            }
        }

//        System.out.println("x " + Arrays.toString(x));
        r.put(0, 0, x);
        return r;
    }

    public static Mat imerodeNGray(Mat img, Mat ele, int n) {
//        System.out.println("path>>>" + path);
        Mat nele = nElementoGray(ele, n);
        nele.convertTo(nele, img.type());
//        System.out.println("type nele " + nele.type());
//        System.out.println("type img " + img.type());
        Mat clon = new Mat();
        Imgproc.morphologyEx(img, clon, Imgproc.MORPH_ERODE, nele);
        return clon;

    }

    public static Mat imdilateNGray(Mat img, Mat ele, int n) {
//        System.out.println("imdilateNGray n:" + n);
        Mat nele = nElementoGray(ele, n);
        nele.convertTo(nele, img.type());
//        System.out.println("type nele " + nele.type());
//        System.out.println("type img " + img.type());
        Mat clon = new Mat();
        Imgproc.morphologyEx(img, clon, Imgproc.MORPH_DILATE, nele);
        return clon;

    }

    public static FbFd FBFD(Mat img, Mat ele, int n, int m) {
        Mat fb = Mat.zeros(img.rows(), img.cols(), img.type());
        Mat fd = Mat.zeros(img.rows(), img.cols(), img.type());
        for (int i = n; i < m; i++) {
            //Calcula FBright
            Mat EN = imerodeNGray(img, ele, i);
            
            Mat g = imdilateNGray(EN, ele, i);
//            System.out.println("type EN " + EN.type());
//            System.out.println("type g " + g.type());
            Mat resta = new Mat();
//            System.out.println("type resta "+ resta.type());
       
            Core.subtract(img, g, resta);
            Mat suma = new Mat();
            Core.add(fb, resta, suma);
            fb.copyTo(suma);
            
            // Calcula FDark
            Mat ID = imdilateNGray(img, ele, i);
            Mat g1 = imerodeNGray(ID, ele, i);
            Mat resta1 = new Mat();
            Core.subtract(g1, img, resta1);
            Core.add(fd, resta, fd);
        }
        //print(fb);
        //print(fd);
        return new FbFd(fb, fd);
    }

    public static Resultado mclV3(Mat img, Mat ele, int n, int m, double alfa) {
        FbFd fbfd = FBFD(img, ele, n, m);
        Mat multiB = new Mat();
        Core.multiply(fbfd.getfBrick(), new Scalar(alfa), multiB);
        Mat multiD = new Mat();
        Core.multiply(fbfd.getfDark(), new Scalar(alfa), multiD);
        Mat suma = new Mat();
        Core.add(img, multiB, suma);
        Mat f = new Mat();
        Core.subtract(suma, multiD, f);
        double e = entropy(f);
        double c = contraste(f);
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        String pathSalida = "C:\\Users\\FRAN\\Documents\\tesis\\psomcl\\Resultados\\salida"+df.format(d)+".png";
        Imgcodecs.imwrite(pathSalida, f);
        Resultado r  = new Resultado(e, c, f, pathSalida);
        log.info(">>>" + r.toString());
        
        return r;
    }
    
    
   
}
