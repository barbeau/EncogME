//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */

package org.encog.util.db.javaMETests.ws.servlets;

import java.io.DataInput;
import javax.servlet.http.HttpSession;

/**
 *  This interface is used to abstract the servlet from the functionality be it
 *  an EJB or a plain Java class or a Web service.
 */
public interface InvocationAbstraction {
    /**
     *  This method performs the actual invocation of server functionality. It is
     *  used by the servlet to delegate functionality to external classes.
     *
     * @param session this http session
     * @param input The stream from which we should read the parameters for the methods
     * @return The return value for the method NULL IS NOT SUPPORTED!!!!
     * @throws java.lang.Exception Thrown when a protocol error occurs
     */
    public Object invoke(HttpSession session, DataInput input) throws Exception;

    /**
     * Return ID's for parameters in order they are called
     */
    public int[] getIds();

    public int[] getReturnIds();
}
//GEN-END:Client