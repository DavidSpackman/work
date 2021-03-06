package jtest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonSimpleReadExample {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{		
		JSONParser parser = new JSONParser();  

		try {  
			
			// Read the file into object format
			Object obj = parser.parse(new FileReader("test.json"));  
			
			//convert object into JSONObject
			JSONObject jsonObject = (JSONObject) obj;  
			
			//Read NAME from JSONObject
			String nameOfCountry = (String) jsonObject.get("Name");  
			System.out.println("Name Of Country: "+nameOfCountry);  
			
			//Read population from JSONObject
			long population = (Long) jsonObject.get("Population");  
			System.out.println("Population: "+population);  

			System.out.println("States are :");  
			
			//Read states array into JSONArray format
			JSONArray listOfStates = (JSONArray) jsonObject.get("States");  
			
			// string iterator
			Iterator<String> iterator = listOfStates.iterator();  
			while (iterator.hasNext()) {  
				System.out.println("====>"+iterator.next());  
			}  

		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
	}
}