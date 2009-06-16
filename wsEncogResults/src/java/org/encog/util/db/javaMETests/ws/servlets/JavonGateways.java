//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */
package org.encog.util.db.javaMETests.ws.servlets;

import java.io.*;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Invocation Gateways
 */
public class JavonGateways {
    /**
     *  This class implements the application server connectivity specific to the needs.
     *  org.encog.util.db.javaMETests.ws.SubmitResults
     */
    public static class org_encog_util_db_javaMETests_ws_servlets_SubmitResultsgetModel2Gateway implements InvocationAbstraction {

        /**
         *  This method performs the actual invocation of server functionality. It is
         *  used by the servlet to delegate functionality to external classes.
         *
         * @param input The stream from which we should read the parameters for the methods
         * @return The return value for the method NULL IS NOT SUPPORTED!!!!
         * @throws Exception  Thrown when a protocol error occurs
         */
        public Object invoke(HttpSession session, DataInput input) throws Exception {
            int id = ((Integer)Utility.readObject( input )).intValue();

            org.encog.util.db.javaMETests.ws.SubmitResults instance = (org.encog.util.db.javaMETests.ws.SubmitResults)session .getAttribute("org.encog.util.db.javaMETests.ws.SubmitResults");
            if (instance == null) {
                instance = (org.encog.util.db.javaMETests.ws.SubmitResults) Class.forName("org.encog.util.db.javaMETests.ws.SubmitResults").newInstance();
                session.setAttribute("org.encog.util.db.javaMETests.ws.SubmitResults", instance);
            }
            return instance.getModel(id);
        }

        public int[] getIds() {
            return new int[] {
                4
            };
        };

        public int[] getReturnIds() {
            return new int[] {
                3
            };
        };
    }
    /**
     *  This class implements the application server connectivity specific to the needs.
     *  org.encog.util.db.javaMETests.ws.SubmitResults
     */
    public static class org_encog_util_db_javaMETests_ws_servlets_SubmitResultssaveResult1Gateway implements InvocationAbstraction {

        /**
         *  This method performs the actual invocation of server functionality. It is
         *  used by the servlet to delegate functionality to external classes.
         *
         * @param input The stream from which we should read the parameters for the methods
         * @return The return value for the method NULL IS NOT SUPPORTED!!!!
         * @throws Exception  Thrown when a protocol error occurs
         */
        public Object invoke(HttpSession session, DataInput input) throws Exception {
            org.encog.util.db.javaMETestsServer.TestResult result = (org.encog.util.db.javaMETestsServer.TestResult)Utility.readObject( input );

            org.encog.util.db.javaMETests.ws.SubmitResults instance = (org.encog.util.db.javaMETests.ws.SubmitResults)session .getAttribute("org.encog.util.db.javaMETests.ws.SubmitResults");
            if (instance == null) {
                instance = (org.encog.util.db.javaMETests.ws.SubmitResults) Class.forName("org.encog.util.db.javaMETests.ws.SubmitResults").newInstance();
                session.setAttribute("org.encog.util.db.javaMETests.ws.SubmitResults", instance);
            }
            return new Boolean(instance.saveResult(result));
        }

        public int[] getIds() {
            return new int[] {
                10
            };
        };

        public int[] getReturnIds() {
            return new int[] {
                1
            };
        };
    }

    private static Object readObject(DataInput in) throws IOException {
        return Utility.readObject(in);
    }
}
//GEN-END:Client
