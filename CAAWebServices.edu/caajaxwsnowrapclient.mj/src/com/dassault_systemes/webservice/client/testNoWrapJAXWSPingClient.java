package com.dassault_systemes.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import com.dassault_systemes.webservice.IPingService;
import com.dassault_systemes.webservice.Ping;
import com.dassault_systemes.webservice.PingErrorType;
import com.dassault_systemes.webservice.PingResponse;
import com.dassault_systemes.webservice.PingService;
 

/**
 * @author RCB
 * sample of a jax-ws client which calls the ping web service.
 * (standard JAXB binding).
 */
public class testNoWrapJAXWSPingClient {

	/**
	 * @param args
	 */
	
	String _rootURI=null;
	String _inputSentence;
	
	private testNoWrapJAXWSPingClient(){_inputSentence="Test Sentence for the ping web service ";}
	
	public static void main(String[] args) {
		
		testNoWrapJAXWSPingClient wsclient = new testNoWrapJAXWSPingClient();
		
		// parse the arguments
		boolean hasError = wsclient.parseArgs(args);
		
		// if arguments are wrong, displays the help and exits.
		if(hasError)
			wsclient.printUsage();
		
		wsclient.callService();
	}

	/**
	 * calls the ping web service.
	 */
	private void callService() {
		
		PingService srv = null;
		
		if(_rootURI==null)
			srv=new PingService();
		else{
			URL wsdl_URL=null;
			try {
				wsdl_URL = new URL(_rootURI+"/webservice?command=get&id=ping&type=wsdl");
			} catch (MalformedURLException e) {
				System.out.println("ERROR : \""+_rootURI+"\" is not an available URL");
				printUsage();
				
			}
			
			try{ 
				String namespace="urn:com:dassault_systemes:webservice";
				String serviceName="PingService";
				srv=new PingService(wsdl_URL,new QName(namespace,serviceName));
			}
			catch(Exception e){
				System.out.println("Error when instanciating the ping web service. ErrorMessage is :");
				e.printStackTrace();
			}
		}
		IPingService myPingService = srv.getPingServicePort();
		

		try {
			System.out.println("Calling the \"ping\" web service with input sentence =\""+_inputSentence+"\"");
			com.dassault_systemes.webservice.Ping request = new Ping();
			request.setISentence(_inputSentence);
			PingResponse response = myPingService.ping(request);
			Integer wordsCount = response.getOWordCount();
			
			System.out.println("webservice response ==>wordCounts ="+wordsCount);
			
			
		} catch (PingErrorType e) {
			System.out.println("ERROR WHEN CALLING The \"ping\" webservice");
			e.printStackTrace();
			System.exit(1);
		}
		
	}

	/**
	 *  USAGE
	 */
	private  void printUsage() {
		System.out.println("Usage : testStdJAXWSPingClient [-s wordsToCount] [-u serverURI]");
		System.out.println("        -s wordsToCount : the input sentence which the web service will count the words (optional)");
		System.out.println("        -u    serverURI : the server URI where the web service is located (optional)");
		System.out.println("                          format : http://server:port/rootURI");
		System.out.println("                          By default takes the url the artifacts where generated from");
		System.exit(1);
	}

	/**
	 * Parses the input arguments of the program
	 * @param args
	 * @return false if something was wrong.
	 */
	private  boolean parseArgs(String[] args) {
		for(int i=0;i<args.length;i++){
			if(args[i].charAt(0)=='-'){
				if(args[i].charAt(1)=='u')
					_rootURI=args[++i];
				else if(args[i].charAt(1)=='s')
					_inputSentence=args[++i];
			}
			else 
				return true;
		} 

		return false;
	}

		
}
