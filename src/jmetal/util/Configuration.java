package jmetal.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
//import matlabcontrol.MatlabProxyFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class contain types and constant definitions
 */
public class Configuration implements Serializable {

    /**
     *
     */
    public static Logger logger_ = Logger.getLogger(Configuration.class.getName());
    public static int cantidadParticulas;
    public static int cantidadLideres;
    public static int cantidadIteraciones;
    public static String bdConexion;
    public static String bdUser;
    public static String bdPass;
    public static String bdDriver;
    public static String nombreImagen;
    public static String nombreProblema;
    public static int cantidadVarReal;
    public static int cantidadVarInt;
    public static int numeroVariables;
    public static int numeroObjetivos;
    public static int dimMin;
    public static int dimMax;
    public static int nMin;
    public static int mMax;
    public static int typeMin;
    public static int typeMax;
    public static double alfaMin;
    public static double alfaMax;
   //public static MatlabProxyFactory factory;
   //public static MatlabProxy proxy;
    public static int nroCorrida;

    static {

        logger_.info("Cargando algoritmo.properties.. ");
        Properties prop = new Properties();
        InputStream input = null;
        PropertyConfigurator.configure("logger.properties");

        try {

            input = new FileInputStream("algoritmo.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            cantidadParticulas = Integer.valueOf(prop.getProperty("algoritmo.cantidadparticulas"));
            cantidadLideres = Integer.valueOf(prop.getProperty("algoritmo.cantidadlideres"));
            cantidadIteraciones = Integer.valueOf(prop.getProperty("algoritmo.cantidaditeraciones"));
            cantidadVarReal = Integer.valueOf(prop.getProperty("algoritmo.cantidadvarreal"));
            cantidadVarInt = Integer.valueOf(prop.getProperty("algoritmo.cantidadvarint"));
            numeroVariables = Integer.valueOf(prop.getProperty("algoritmo.numerovariables"));
            numeroObjetivos = Integer.valueOf(prop.getProperty("algoritmo.numeroobjetivos"));
            nombreImagen = prop.getProperty("algoritmo.nombreimagen");
            nombreProblema = prop.getProperty("algoritmo.nombre.problema");
            nroCorrida = Integer.valueOf(prop.getProperty("algoritmo.nroCorrida"));
            bdConexion = prop.getProperty("bd.conexion");
            bdDriver = prop.getProperty("bd.driver");
            bdUser = prop.getProperty("bd.user");
            bdPass = prop.getProperty("bd.pass");

            dimMin = Integer.valueOf(prop.getProperty("dimMin"));
            dimMax = Integer.valueOf(prop.getProperty("dimMax"));
            nMin = Integer.valueOf(prop.getProperty("nMin"));
            mMax = Integer.valueOf(prop.getProperty("mMax"));
            typeMin = Integer.valueOf(prop.getProperty("typeMin"));
            typeMax = Integer.valueOf(prop.getProperty("typeMax"));
            alfaMin = Double.parseDouble(prop.getProperty("alfaMin"));
            alfaMax = Double.parseDouble(prop.getProperty("alfaMax"));

            //factory = new MatlabProxyFactory();
            //proxy = factory.getProxy();

            logger_.info("Cargado algoritmo.properties");
//        } catch (MatlabConnectionException ex) {
//            logger_.error("Error en la generacion del proxy..");
//            ex.printStackTrace();
        } catch (IOException ex) {
            logger_.error("Error general..");
            ex.printStackTrace();

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

} // Configuration
