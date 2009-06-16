/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.encog.util.db.javaMETests.ws;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.encog.matrix.MatrixCODEC;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.persist.EncogPersistedCollection;
import org.encog.util.db.javaMETestsServer.TestResult;
import org.encog.util.db.javaMETestsServer.TestResultManager;

/**
 *
 * @author Sean
 */
@WebService()
public class SubmitResults {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "saveResult")
    public boolean saveResult(@WebParam(name = "result")
    TestResult result) {
        try{
        //Save result to database
            TestResultManager manager = new TestResultManager(true);
            manager.saveTestResults(result);
            return true;
        }catch(Exception e){
            System.out.println("Error submitting result: " + e);
            return false;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getModel")
    public java.lang.Double[] getModel(@WebParam(name = "id")
    int id) {
        try{
            String XMLmodel = null;

            //Get model (XML string) from database
            TestResultManager manager = new TestResultManager(true);
            XMLmodel = manager.getModel(id);  //TODO - fill out this method in org.encog.util.db.javaMETestsServer.TestResultManager

            System.out.println("Neural Net model from database:");
            System.out.println(XMLmodel);

            //  travis
            //TODO - Use the Encog persistance stuff to parse XML and create BasicNetwork "network"
            
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("E:\\netbeans\\nn-from-db.xml"));
                out.write(XMLmodel);
                out.close();
            } catch (IOException e) {
            }

            EncogPersistedCollection encog =  new EncogPersistedCollection();
            encog.load("E:\\netbeans\\nn-from-db.xml");
            BasicNetwork network = (BasicNetwork) encog.getList().get(0);

            //Here we will have BasicNetwork "network" here containing full neural net (same as the network trained on Desktop app)

            //Phone stuff is below

            //Convert weights and thresholds to format to be passed to cell phone
            Double[] weightsThresholds;
            weightsThresholds = MatrixCODEC.networkToArray(network);

            //Pass back array of Doubles containing Neural Net weights and thresholds to phone
            return weightsThresholds;
        }catch(Exception e){
            System.out.println("Error loading network: " + e);
            return null;
        }
    }

}
