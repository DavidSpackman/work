package hondapassport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import matrix.db.*;
import matrix.util.MatrixException;

@SuppressWarnings("unused")
public class Util {
    
    static String tracefile = "trace.txt";
    
    public static Context connect(String host, String user, String password, String role, String vault, boolean use3DPassport) {
    	    	
        Context ctx;
        try {            
              	
			if (use3DPassport)            	
                ctx = new Context(host + Passport.getTicket(host, user, password));
			
            else {
                ctx = new Context(host);
                ctx.setPassword(password);
            }
            ctx.setUser(user);
            ctx.setRole(role);
            ctx.setVault(vault);
            ctx.connect();
            return ctx;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg.contains("XML: Expected") || msg.contains("Exception: Expected") || msg.contains("XML: -1"))
                msg = "Enovia reported the following error: " + msg +
                      "\nThis can be due to a wrong library version." + 
                      "\nTake file eMatrixServletRMI.jar" + 
                      "\nfrom your Enovia server and copy it into the RemoteMQL\\lib folder." +
                      "\nYou find it in C:\\Tomcat...\\...\\WEB-INF\\lib on your Enovia server.";
            else if (msg.contains("XML: java.lang.NullPointerException"))
                msg = "Enovia reported the following error: " + msg +
                      "\nThis can be due to a network address change." +
                      "\nRestart Tomcat and try again.";
            else if (msg.contains("java.net.SocketException: Connection reset"))
                msg = "The system reported the following error: " + msg +
                      "\nMaybe the server is down or the port number in the Host URL is wrong." +
                      "\nCheck that the server is alive or try with a different port number.";
            else if (msg.contains("PKIX"))
                msg = "The system reported the following error: " + msg +
                      "\nMaybe the SSL certificates are not installed properly or need to be renewed." +
                      "\nContact your admin or try to import the certificates with \"ManageSSLcertificates.bat\".";
            else if (msg.contains("HttpException"))
                msg = "The system reported the following error: " + msg +
                      "\nIt seems that you are trying to connect using 3DPassport." +
                      "\nMake sure you use the \"-passport\" option.";
            else if (host.isEmpty()) {
                String bits = System.getProperty("sun.arch.data.model");
                if (bits.equals("32"))
                    msg = "You are using 3DEXPERIENCE Database Browser in RIP mode but on a 32 bit Java environment." +
                          "\nRe-launch the 3DEXPERIENCE Database Browser in a 64 bit Java environment.";
                else
                    msg = "You are using 3DEXPERIENCE Database Browser in RIP mode." +
                          "\n• Make sure the PATH variable contains the ENOVIA Live Collaboration server path, e.g. C:\\enoviaV6R20??\\server\\win_b64\\code\\bin." +
                          "\n• Make sure you have copied 'enoviaKernel.jar' from the ...\\WEB-INF\\lib folder to EnoBrowser\\lib.";                    
            }
            trace("Connect Error: " + msg);
            trace(ex);
            System.err.println(msg);
            System.exit(4);
        }
        return null;
    }
    
    static void trace(Exception e) {
        try (PrintStream out = new PrintStream(new FileOutputStream(tracefile, true))) {
            out.println();
            e.printStackTrace(out);
            if (out != null) out.close();
        } catch (FileNotFoundException ex) {}
    }
    
    static void trace(String s) {
        try (PrintStream out = new PrintStream(new FileOutputStream(tracefile, true))) {
            if (s != null) {
                out.println();
                out.println(s);
            }
            if (out != null) out.close();
        } catch (FileNotFoundException ex) {}
    }
    
}