package passport;
/*
 * 
 * This class works for 15X passport.
 * 
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class PassportUpdate_15X 
{
	public static void main(String[] args) 
	{
		try
		{
			String sPassportURL = "https://passport.sa.com/3dpassport";
			
			// Get skey
			String sKey = "";
			URL localURLskey = new URL(sPassportURL + "/api/public/skey/admin_platform");
			URLConnection localURLConnection = localURLskey.openConnection();
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localURLConnection.getInputStream()));
			String str1 = localBufferedReader.readLine();
			sKey = str1.replace("\"", "");
			//System.out.println(sKey);
			
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
			OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localHttpURLConnection.getOutputStream());
			localOutputStreamWriter.write(jsonCreateUpdatePerson);	// CREATE / UPDATE
			//localOutputStreamWriter.write(jsonGetPerson);	// GET
			localOutputStreamWriter.flush();
			BufferedReader localBufferedReader1 = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
			String sResult = null;
			for (String str2 = localBufferedReader1.readLine(); str2 != null; str2 = localBufferedReader1.readLine())
			{
				sResult = str2;
				System.out.println(sResult);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return;
	}	
}