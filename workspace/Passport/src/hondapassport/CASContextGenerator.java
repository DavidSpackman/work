package hondapassport;

import matrix.db.Context;

public class CASContextGenerator {

	public static void main(String[] args) throws Exception{
	
		String host ="https://3dspace.sa.com/3dspace";
		String user ="sanne_admin";
		String password="Passport1";
		String role ="";
		String vault="";
		boolean use3DPassport=true;
		
		Context context = Util.connect(host, user, password, role, vault, use3DPassport);
		
		System.out.println(" --------->"+ context);

	}

}