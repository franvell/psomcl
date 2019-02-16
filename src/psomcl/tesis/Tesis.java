package psomcl.tesis;

import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.metaheuristics.smpso.SMPSOTesis;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.encodings.solutionType.IntRealSolutionType;
import jmetal.util.Configuration;
import jmetal.util.JMException;
import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import testopencv.Resultado;
import testopencv.TestOpenCV;
import static testopencv.TestOpenCV.mclV3;

/**
 *
 * @author Estefanis
 */
public class Tesis extends Problem {

    private static final Logger log = Logger.getLogger(Tesis.class.getName());

    int filas;
    int columnas;
    String nombreImagen;

    public Tesis() {

        upperLimit_ = new double[numeroDeVariables];
        lowerLimit_ = new double[numeroDeVariables];
        //dim
        lowerLimit_[0] = Configuration.dimMin;
        upperLimit_[0] = Configuration.dimMax;
        //n
        lowerLimit_[1] = Configuration.nMin;
        upperLimit_[1] = Configuration.mMax - 1;

        //type
        lowerLimit_[2] = Configuration.typeMin;
        upperLimit_[2] = Configuration.typeMax;

        //alfa
        lowerLimit_[3] = Configuration.alfaMin;
        upperLimit_[3] = Configuration.alfaMax;

        tipoSolucion = new IntRealSolutionType(this, cantidadVarInt, cantidadVarReal);
    }

    @Override
    public void evaluate(Solution solution) throws JMException {

        try {
            Variable[] variables = solution.getDecisionVariables();
            // genera la trama para calcular la trama
//            StringBuilder builder = new StringBuilder();
//            builder.append("[e , c, path] = testMCLV3('")
//                    .append(Configuration.nombreImagen)
//                    .append("',")
//                    .append((int) variables[1].getValue()).append(",") // n
//                    .append(Configuration.mMax).append(",") // m
//                    .append(variables[3].getValue()).append(",") //alfa
//                    .append((int) variables[2].getValue()).append(",") //type
//                    .append((int) variables[0].getValue()).append(")"); //dim
//            MatlabProxy proxy = Configuration.factory.getProxy();
//            proxy.setVariable("e", 0);
//            proxy.setVariable("c", 0);
//            // evalua
//            proxy.eval(builder.toString());
//            // resultado
//            double entropia = ((double[]) proxy.getVariable("e"))[0];
//            double contraste = ((double[]) proxy.getVariable("c"))[0];
//            String path = (String) proxy.getVariable("path");

            /// borrar
            
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            Mat image = TestOpenCV.imread("C:\\Users\\FRAN\\Documents\\tesis\\psomcl\\src\\image\\mdb001.pgm");
//        System.out.println("size:" + Arrays.toString(size(image)));
//        System.out.println("type:" + image.type());
//        
            Mat elemento = TestOpenCV.getElemento((int) variables[2].getValue(), (int) variables[0].getValue());
//            System.out.println("japiroooooooooooo");
            Resultado r = mclV3(image, elemento, (int) variables[1].getValue(), Configuration.mMax, variables[3].getValue());
            System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            solution.setNombreImagenResultado(r.getPathSalida());
            solution.setObjective(0, r.getContraste());
            solution.setObjective(1, r.getEntropia());

            //Disconnect the proxy from MATLAB
           // proxy.disconnect();
            //proxy.exit();
//        } catch (MatlabInvocationException ex) {
//            log.error("Error al evaluar. " + ex.getMessage());
//            // ex.printStackTrace();
//        } catch (MatlabConnectionException ex) {
//            log.error("Error al conectar el proxy. " + ex.getMessage());
//        }
        }catch (Exception e){
            System.out.println("error en evaluar");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        try {
            Problem problema = new Tesis();
            Algorithm algoritmo = new SMPSOTesis(problema);
            log.info("nro de corrida:" + Configuration.nroCorrida);
            log.info("tamanho del enjambre:" + Configuration.cantidadParticulas);
            algoritmo.setInputParameter("tamanhoEnjambre", Configuration.cantidadParticulas); //tamaño del enjambre
            log.info("tamanho del archivo de lideres:" + Configuration.cantidadLideres);
            algoritmo.setInputParameter("tamanhoLideres", Configuration.cantidadLideres);//tamaño del archivo de lideres
            log.info("maximo de iteraciones:" + Configuration.cantidadIteraciones);
            algoritmo.setInputParameter("maximoIteraciones", Configuration.cantidadIteraciones);//maximo de iteraciones            
            HashMap parameters = new HashMap();//Operator parameters
            parameters.put("probability", 1.0 / problema.getNumberOfVariables());//para mutacion
            parameters.put("distributionIndex", 20.0);//para mutacion
            Mutation mutacion = MutationFactory.getMutationOperator("PolynomialMutation", parameters); //operador de turbulencia (mutacion)
            algoritmo.addOperator("mutation", mutacion);
            long initTime = System.currentTimeMillis();
            SolutionSet poblacion; 
            poblacion = algoritmo.execute();
            long estimatedTime = System.currentTimeMillis() - initTime;
            log.info("Tiempo total: " + estimatedTime + " ms");
            poblacion.printResultadosToFile();
            poblacion.insertToBD();
            //Disconnect the proxy from MATLAB
            //Configuration.proxy.disconnect();

        } catch (JMException ex) {
            log.error("Error al obtener el operador de turbulencia " + ex.getMessage());

        } catch (ClassNotFoundException ex) {
            log.error("Error al obtener el ejecutar el PSO " + ex.getMessage());

        }

    }

}
