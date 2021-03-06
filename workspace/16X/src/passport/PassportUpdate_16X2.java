package passport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * 
 * This class works for 16X passport.
 * 
 */
public class PassportUpdate_16X2 {

	String sPassportURL = "https://passport.sa.com/3dpassport";

	public String getSKey(String sPassportURL) throws Exception{
		String sKey = "";
		try{

			URL localURLskey = new URL(sPassportURL + "/api/public/skey");
			HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURLskey.openConnection();
			localHttpURLConnection.setDoOutput(true);
			localHttpURLConnection.setRequestMethod("POST");
			localHttpURLConnection.addRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			localHttpURLConnection.addRequestProperty("Accept", "*/*");
			OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localHttpURLConnection.getOutputStream());
			localOutputStreamWriter.write("admin_platform");
			localOutputStreamWriter.flush();
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
			String str1 = localBufferedReader.readLine();
			sKey = str1.replace("\"", "");
		}
		catch (Exception e) {
			System.out.println("Error in getting passport skey.....");
		}

		return sKey;		
	}


	public String createUser(String sKey, String loginId,String password, String firstName, String lastName,String country,String email) {
				
		String success="";

		try{
		String jsonCreateUpdatePerson = "{\"active\":true,\"lastSynchronized\":0,\"sourceRepositories\":[],\"fields\":{\"lastName\":\"" + lastName + "\",\"country\":\"" + country + "\",\"username\":\"" + loginId + "\",\"email\":\"" + email + "\",\"firstName\":\"" + firstName + "\",\"password\":\"" + password + "\"},\"acls\":{\"3dexperience\":\"ACCEPTED\",\"passport\":\"ACCEPTED\"},\"tenant\":null}";	// CREATE / UPDATE
		
		URL localURL = new URL(sPassportURL + "/api/private/tenant1/user/register");	// CREATE
		HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
		localHttpURLConnection.setDoOutput(true);
		localHttpURLConnection.setRequestMethod("POST");	// CREATE / UPDATE
		//localHttpURLConnection.setRequestMethod("GET");	// GET	
		localHttpURLConnection.addRequestProperty("skey", sKey);
		localHttpURLConnection.addRequestProperty("Content-Type", "application/json");
		localHttpURLConnection.addRequestProperty("Accept", "application/json");
		OutputStreamWriter localOutputStreamWriter1 = new OutputStreamWriter(localHttpURLConnection.getOutputStream());
		localOutputStreamWriter1.write(jsonCreateUpdatePerson);	// CREATE / UPDATE
		//localOutputStreamWriter.write(jsonGetPerson);	// GET
		localOutputStreamWriter1.flush();
		BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
		String sResult = null;
		for (String str2 = localBufferedReader1.readLine(); str2 != null; str2 = localBufferedReader1.readLine())
		{
			sResult = str2;
			System.out.println(sResult);
		}
		}catch(Exception e){
			System.out.println("Error in creating user.....");
		}
		
		return success;

	}


	public String updateUser(String loginId,String password, String firstName, String lastName,String country,String email){
		String success="";

		String jsonCreateUpdatePerson = "{\"active\":true,\"lastSynchronized\":0,\"sourceRepositories\":[],\"fields\":{\"lastName\":\"" + lastName + "\",\"country\":\"" + country + "\",\"username\":\"" + loginId + "\",\"email\":\"" + email + "\",\"firstName\":\"" + firstName + "\",\"password\":\"" + password + "\"},\"acls\":{\"3dexperience\":\"ACCEPTED\",\"passport\":\"ACCEPTED\"},\"tenant\":null}";	// CREATE / UPDATE


		return success;

	}





	public static void main(String[] args) {
		try{
			
			

			String sPassportURL = "https://passport.sa.com/3dpassport";

			String sKey = "";
			URL localURLskey = new URL(sPassportURL + "/api/public/skey");
			HttpURLConnection localHttpURLConnection1 = (HttpURLConnection)localURLskey.openConnection();
			localHttpURLConnection1.setDoOutput(true);
			localHttpURLConnection1.setRequestMethod("POST");
			localHttpURLConnection1.addRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			localHttpURLConnection1.addRequestProperty("Accept", "*/*");
			OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localHttpURLConnection1.getOutputStream());
			localOutputStreamWriter.write("admin_platform");
			localOutputStreamWriter.flush();
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpURLConnection1.getInputStream()));
			String str1 = localBufferedReader.readLine();
			sKey = str1.replace("\"", "");

			System.out.println("---------------sKey-->" + sKey);



			String loginId = "testuser02";
			String lastName = "User02";
			String firstName = "FirstName1";
			String country = "US";
			String email = "testuser02@3ds.com";
			String password = "Passport2";

			String jsonCreateUpdatePerson = "{\"active\":true,\"lastSynchronized\":0,\"sourceRepositories\":[],\"fields\":{\"lastName\":\"" + lastName + "\",\"country\":\"" + country + "\",\"username\":\"" + loginId + "\",\"email\":\"" + email + "\",\"firstName\":\"" + firstName + "\",\"password\":\"" + password + "\"},\"acls\":{\"3dexperience\":\"ACCEPTED\",\"passport\":\"ACCEPTED\"},\"tenant\":null}";	// CREATE / UPDATE
			System.out.println(jsonCreateUpdatePerson);	// CREATE / UPDATE

			// GET
			//String jsonGetPerson = "{\"username\":\"" + loginId + "\"}";	// GET
			//System.out.println(jsonGetPerson);	// GET

			URL localURL = new URL(sPassportURL + "/api/private/tenant1/user/register");	// CREATE
			//URL localURL = new URL(sPassportURL + "/api/private/user/update/" + loginId);	// UPDATE
			//URL localURL = new URL(sPassportURL + "/api/private/user/get");	// GET
			HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
			localHttpURLConnection.setDoOutput(true);
			localHttpURLConnection.setRequestMethod("POST");	// CREATE / UPDATE
			//localHttpURLConnection.setRequestMethod("GET");	// GET	
			localHttpURLConnection.addRequestProperty("skey", sKey);
			localHttpURLConnection.addRequestProperty("Content-Type", "application/json");
			localHttpURLConnection.addRequestProperty("Accept", "application/json");
			OutputStreamWriter localOutputStreamWriter1 = new OutputStreamWriter(localHttpURLConnection.getOutputStream());
			localOutputStreamWriter1.write(jsonCreateUpdatePerson);	// CREATE / UPDATE
			//localOutputStreamWriter.write(jsonGetPerson);	// GET
			localOutputStreamWriter1.flush();
			BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
			String sResult = null;
			for (String str2 = localBufferedReader1.readLine(); str2 != null; str2 = localBufferedReader1.readLine())
			{
				sResult = str2;
				System.out.println(sResult);
			}

		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
