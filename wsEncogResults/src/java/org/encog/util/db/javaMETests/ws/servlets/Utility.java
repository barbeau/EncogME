//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */
package org.encog.util.db.javaMETests.ws.servlets;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

/**
 *  This class is used as an external protocol utility. It is so we don't
 *  generate as much code.
 */

public class Utility {

    /** Marker for null. Null is a type and a value together. */
    private final static short NULL_TYPE = -1;

    /** Marker for void return types. */
    public final static Object VOID_VALUE = new Object();

    /**
     * Sends return values to the client output stream.
     *
     * @param output The output stream into which all the data should be written
     * @param  returnValue The value which we should write into the stream
     * @throws IOException If an error occured while writing the results
     */
    public static void writeResults( DataOutput output, Object returnValue, int[] paramIDs) throws IOException {
        for ( int i = 0; i < paramIDs.length; i++ ) {
            writeObject(output, returnValue, paramIDs[i]);
        }
    }

    /**
     *
     * @param output
     * @param o
     * @throws java.io.IOException
     */
    public static void writeObject(DataOutput output, Object o, int id) throws IOException {
        if( o == null ) {
            // write null type to the stream
            output.writeShort( NULL_TYPE );
            return;
        }
        switch( id ) {
            case 1:
                // boolean
                output.writeShort(1);
                output.writeBoolean(((Boolean)o).booleanValue());
                break;
            case 3:
                // Double[]
                output.writeShort(3);
                Double[] a_result_3 = (Double[]) o;
                output.writeInt( a_result_3.length );
                for( int i  = 0; i < a_result_3.length; i++ ) {
                    writeObject( output, a_result_3[i], 5 );
                }

                break;
            case 5:
                // Double
                output.writeShort(5);
                output.writeDouble( ((Double)o).doubleValue() );
                break;
            default:
                // default if a data type is not supported
                throw new IllegalArgumentException("Unsupported parameter type: " + o.getClass());
        }
    }

    /**
     *
     * @param in
     * @return
     * @throws java.io.IOException
     */
    protected static Object readObject(DataInput in) throws IOException {
        short type = in.readShort();
        Object result;
        switch (type) {
            case 2:
                // long
                return new Long(in.readLong());
            case 4:
                // int
                return new Integer(in.readInt());
            case 7:
                // double
                return new Double(in.readDouble());
            case 10:
                // TestResult
                org.encog.util.db.javaMETestsServer.TestResult b_org_encog_util_db_javaMETestsServer_TestResult = new org.encog.util.db.javaMETestsServer.TestResult();
                b_org_encog_util_db_javaMETestsServer_TestResult.setPlatform(in.readInt());
                b_org_encog_util_db_javaMETestsServer_TestResult.setNumEpochs(in.readInt());
                b_org_encog_util_db_javaMETestsServer_TestResult.setMinStopError(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setMathPOWSolution(in.readInt());
                b_org_encog_util_db_javaMETestsServer_TestResult.setLearningRate(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setMomemtum(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTrainingTime(in.readLong());
                b_org_encog_util_db_javaMETestsServer_TestResult.setFinalTrainError(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest00Actual(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest00Error(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest00Time(in.readLong());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest10Actual(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest10Error(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest10Time(in.readLong());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest01Actual(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest01Error(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest01Time(in.readLong());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest11Actual(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest11Error(in.readDouble());
                b_org_encog_util_db_javaMETestsServer_TestResult.setTest11Time(in.readLong());
                result = b_org_encog_util_db_javaMETestsServer_TestResult;

                return result;
            case NULL_TYPE: /* null */
                return null;
            default:
                throw new IllegalArgumentException(
                        "Unsupported return type (" + type + ")");
        }
    }
}
//GEN-END:Client