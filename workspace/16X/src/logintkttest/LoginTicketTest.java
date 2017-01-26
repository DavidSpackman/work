package logintkttest;

import honda.DBContextFromLoginicket;
import matrix.db.Context;

public class LoginTicketTest {

	public static void main(String[] args) {
		 
		DBContextFromLoginicket serContext = DBContextFromLoginicket.getInstance();		
	
		serContext.setLoginTicket("RTBDQUYwNkZBMzA2NDdBQkE2NDc4NTBERjYxNEEzNTZ8V0JPTSBhZG1pbnxXQk9NIGFkbWlufHx8MHw=");
		
		//create new properties key or use existing property key with non-CAS URL
		serContext.setServiceUrl("http://hdcdev86.am.mds.honda.com:9081/3dspace/");		
		
		Context context = serContext.getContext();
		System.out.println("Context for webservice------->" + context);

	}

}
