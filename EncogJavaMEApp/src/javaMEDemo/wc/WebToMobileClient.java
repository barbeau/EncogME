//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */
package javaMEDemo.wc;

import java.util.*;
import java.io.*;
import javax.microedition.io.*;

import org.encog.util.db.javaMETestsServer.TestResult;
/**
 *
 */
public class WebToMobileClient {

    /** The URL of the servlet gateway */
    private String serverURL;

    /** The session cookie of this client */
    private String sessionCookie;

    /**
     * Empty array used for no-argument calls, and to represent the value "void"
     */

    private final static Object[] _ = new Object[0];

    /**
     * Constructs a new WebToMobileClient
     * and initializes the URL to the servlet gateway from a hard-coded value.
     */
    public WebToMobileClient() {
        this("http://localhost:8080/wsEncogResults/servlet/org.encog.util.db.javaMETests.ws.servlets.WebToMobileServlet");
    }

    /**
     * Constructs a new WebToMobileClient
     * and initializes the URL to the servlet gateway from the given value
     *
     * @param serverURL URL of the deployed servlet
     */
    public WebToMobileClient(String serverURL) {
        this.serverURL = serverURL;
    }
    public Double[] getModel(int id) throws IOException {
        Object params[] = new Object[] {
            new Integer(id)
        };
        int paramIDs[] = new int[] {
            4
        };
        return (Double[])invokeServer(2, params, paramIDs);
    }
    public boolean saveResult(org.encog.util.db.javaMETestsServer.TestResult result) throws IOException {
        Object params[] = new Object[] {
            (org.encog.util.db.javaMETestsServer.TestResult)result
        };
        int paramIDs[] = new int[] {
            10
        };
        return ((Boolean)invokeServer(1, params, paramIDs)).booleanValue();
    }

    /**
     *  This method performes a dynamic invocation on the server. It is generic in
     *  order to reduce the code size.
     *
     *@param  requestID        The id of the server service (method) we wish to
     *      invoke.
     *@param  parameters       The parameters that should be passed to the server
     *      (type safety is not checked by this method!)
     *@param  returnType       Is used to indicate the return type we should read
     *      from the server
     *@return                  The return value from the invoked service
     *@exception  IOException  When a communication error or a remove exception
     * occurs
     */
    private Object invokeServer(int requestID, Object[] parameters, int[] paramIDs ) throws IOException {
        HttpConnection connection = (HttpConnection) Connector.open( serverURL );
        connection.setRequestMethod(HttpConnection.POST);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Accept", "application/octet-stream");

        if (sessionCookie == null) {
            // if this is the first time this client contatcs the server,
            // verify that the version matches
            connection.setRequestProperty("version", "???");
        } else {
            connection.setRequestProperty("cookie", sessionCookie);
        }

        DataOutputStream output = connection.openDataOutputStream();

        // Write invocation code
        output.writeShort( 1 );

        /* Write the byte signifying that only one call
         * is being made.
         */
        // TODO This is not reflected on server now
        //output.writeShort(1 /* one call to be made to the server */);

        output.writeInt(requestID);
        for (int i = 0; i < parameters.length; i++ ) {
            writeObject(output, parameters[i], paramIDs[i]);
        }

        output.close();

        int response;
        try {
            response = connection.getResponseCode();
        } catch (IOException e) {
            throw new IOException("No response from " + serverURL);
        }
        if (response != 200) {
            throw new IOException(response + " " + connection.getResponseMessage());
        }
        DataInputStream input = connection.openDataInputStream();
        String sc = connection.getHeaderField("set-cookie");
        if (sc != null) {
            sessionCookie = sc;
        }
        short errorCode = input.readShort();
        if (errorCode != 1) {
            // there was a remote exception
            throw new IOException(input.readUTF());
        }

        Object returnValue = readObject(input);

        input.close();
        connection.close();
        return returnValue;
    }

    /**
     * Serializes object to the stream with given type id
     *
     * @param out
     * @param o object to be serialized to the stream
     * @param id idetification code of the serialized object
     */
    private void writeObject( DataOutputStream out, Object o, int id ) throws IOException {
        if( o == null ) {
            // write null type to the stream
            out.writeShort( -1 );
            return;
        }
        switch( id ) {
            case 2:
                // long
                out.writeShort(2);
                out.writeLong(((Long)o).longValue());
                break;
            case 4:
                // int
                out.writeShort(4);
                out.writeInt(((Integer)o).intValue());
                break;
            case 7:
                // double
                out.writeShort(7);
                out.writeDouble( ((Double)o).doubleValue() );
                break;
            case 10:
                // TestResult
                out.writeShort(10);
                org.encog.util.db.javaMETestsServer.TestResult b_org_encog_util_db_javaMETestsServer_TestResult = (org.encog.util.db.javaMETestsServer.TestResult)o;
                out.writeInt(b_org_encog_util_db_javaMETestsServer_TestResult.getPlatform());
                out.writeInt(b_org_encog_util_db_javaMETestsServer_TestResult.getNumEpochs());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getMinStopError() );
                out.writeInt(b_org_encog_util_db_javaMETestsServer_TestResult.getMathPOWSolution());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getLearningRate() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getMomemtum() );
                out.writeLong(b_org_encog_util_db_javaMETestsServer_TestResult.getTrainingTime());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getFinalTrainError() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest00Actual() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest00Error() );
                out.writeLong(b_org_encog_util_db_javaMETestsServer_TestResult.getTest00Time());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest10Actual() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest10Error() );
                out.writeLong(b_org_encog_util_db_javaMETestsServer_TestResult.getTest10Time());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest01Actual() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest01Error() );
                out.writeLong(b_org_encog_util_db_javaMETestsServer_TestResult.getTest01Time());
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest11Actual() );
                out.writeDouble( b_org_encog_util_db_javaMETestsServer_TestResult.getTest11Error() );
                out.writeLong(b_org_encog_util_db_javaMETestsServer_TestResult.getTest11Time());

                break;
            default:
                // default if a data type is not supported
                throw new IllegalArgumentException("Unsupported parameter type: " + o.getClass());
        }
    }

    private static Object readObject(DataInput in) throws IOException {
        int type = in.readShort();
        Object result;
        switch (type) {
            case 1:
                // boolean
                return new Boolean(in.readBoolean());
            case 3:
                // Double[]
                int a_size_3 = in.readInt();
                Double[] a_result_3 = new Double[ a_size_3 ];
                for( int a_i_3 = 0; a_i_3 < a_size_3; a_i_3++ ) {
                    a_result_3[a_i_3] = (Double)readObject( in );
                }
                result = a_result_3;

                return result;
            case 5:
                // Double
                result = new Double(in.readDouble());
                return result;
            case -1: /* NULL */
                return null;
        }
        throw new IllegalArgumentException("Unsupported return type (" + type + ")");
    }
}
//GEN-END:Client
