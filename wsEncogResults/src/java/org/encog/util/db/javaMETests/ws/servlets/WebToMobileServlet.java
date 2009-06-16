//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */
package org.encog.util.db.javaMETests.ws.servlets;

import org.encog.util.db.javaMETests.ws.servlets.JavonGateways;
import org.encog.util.db.javaMETests.ws.servlets.InvocationAbstraction;
import org.encog.util.db.javaMETests.ws.servlets.Utility;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * An automatically-generated servlet gateway. This servlet provides
 * J2ME access to the following server-side methods and classes:
 *
 * <ul>
 *  <li> sq.webtomobileservletsupport.StockQuoteSoapProxy.getQuote
 * </ul>
 */
public class WebToMobileServlet extends HttpServlet {

    /**
     *  This constant indicates the command code for an invocation in the standard
     *  protocol.
     */
    private final static short INVOCATION_CODE = 1;

    /**
     *  This member indicates a successful result
     */
    private final static short RESULT_SUCCESSFUL = 1;

    /**
     *  This member indicates a server side exception
     */
    private final static short RESULT_EXCEPTION = 2;

    /**
     * The version string for the protocol. This must match the client's
     * version
     */
    private final static String PROTOCOL_VERSION = "???";

    /**
     *  This member contains the method abstractions that can be invoked by the
     *  servlet.
     */
    private final static Map METHODS;

    static {
        METHODS = new HashMap();
        METHODS.put( new Integer(2), new JavonGateways.org_encog_util_db_javaMETests_ws_servlets_SubmitResultsgetModel2Gateway());
        METHODS.put( new Integer(1), new JavonGateways.org_encog_util_db_javaMETests_ws_servlets_SubmitResultssaveResult1Gateway());
    }

    /**
     * Called to handle a GET request. Returns a message that the servlet
     * is deployed
     *
     * @param req
     * @param resp
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=iso-8859-1");
        String title = "WebToMobileServlet servlet status";
        String message = "WebToMobileServlet servlet active";
        OutputStream os = resp.getOutputStream();
        String response = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n" +
                "\"http://www.w3.org/TR/REC-html40/loose.dtd\">\n" +
                "<html>\n<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">" +
                "<title>" + title + "</title>\n" +
                "</head>\n<body>\n" +
                "<center><h1>" + message + "</h1></center>\n" +
                "</body>\n</html>\n";
        os.write(response.getBytes("ISO-8859-1"));
        os.close();
    }

    /**
     *  Called by the server (via the service method) to allow a servlet to handle
     *  a POST request. This method dispatches the calls to the underlying class.
     *
     * @param  req an HttpServletRequest object that contains the request the client has made of the servlet
     * @param  resp an HttpServletResponse object that contains the response the servlet sends to the client
     * @exception  ServletException  - if the request for the POST could not be handled
     * @throws IOException Description of Exception
     */
    protected void doPost( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        resp.setContentType("application/octet-stream");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);
        DataOutput out = outputStream;
        int requestID=0;
        try {
            short resultCode = RESULT_SUCCESSFUL;
            DataInputStream in = new DataInputStream(req.getInputStream());

            HttpSession session = req.getSession(true);

            String versionString = req.getHeader("version");
            if (versionString != null) {
                if (!versionString.equals(PROTOCOL_VERSION)) {
                    throw new IOException("Incompatible protocol version: "
                            + "Client's version '" + versionString + "' "
                            + "does not match server's version '"
                            + PROTOCOL_VERSION + "'");
                }
            }

            short commandCode = in.readShort();

            if ( commandCode == INVOCATION_CODE ) {
                requestID = in.readInt();
                Object returnValue = invokeMethod(session,requestID,in);
                out.writeShort(resultCode);
                Utility.writeResults(out, returnValue, ((InvocationAbstraction)METHODS.get(new Integer(requestID))).getReturnIds());
            }

            in.close();
        } catch ( Exception err ) {
            if (outputStream == null) {
                outputStream = new DataOutputStream(resp.getOutputStream());
                out = outputStream;
            }

            out.writeShort(RESULT_EXCEPTION);
            err.printStackTrace();
            String errMessage = err.getMessage();
            if (errMessage == null) {
                errMessage = "";
            }
            if (err instanceof IOException) {
                out.writeUTF(err.getMessage());
            } else {
                if (errMessage.length() > 0) {
                    errMessage = ": " + errMessage;
                }
                out.writeUTF(err.getClass().getName() + err.getMessage());
            }
        }
        outputStream.close();
        byte[] outputData = baos.toByteArray();
        resp.setContentLength(outputData.length);
        OutputStream httpOutputStream = resp.getOutputStream();
        try {
            httpOutputStream.write(outputData);
        } finally {
            httpOutputStream.close();
        }
    }

    /**
     *  This method performs the actual invocation of server functionality.
     *
     * @param session The http session
     * @param requestID The id of a specific method invocation
     * @param input The stream from which we should read the parameters for the methods
     * @return Description of the Returned Value
     * @throws IOException  Thrown when a protocol/response error occurs
     */
    private Object invokeMethod(HttpSession session, int requestID, DataInput input) throws Exception {
        return ((InvocationAbstraction)METHODS.get( new Integer(requestID))).invoke(session, input);
    }
}
//GEN-END:Client
