package jtest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonSimpleWriteExample {

	@SuppressWarnings("unchecked") 
	public static void main(String[] args) {
		
		JSONObject countryObj = new JSONObject();  
        countryObj.put("Name", "US");  
        countryObj.put("Population", new Integer(30000000));  
  
        JSONArray listOfStates = new JSONArray();  
        listOfStates.add("Arizone");  
        listOfStates.add("Florida");  
        listOfStates.add("Ohio");  
  
        countryObj.put("States", listOfStates);  
  
        try {  
              
            // Writing to a file  
            File file=new File("test.json");  
            file.createNewFile();
            
            FileWriter fileWriter = new FileWriter(file);  
            
            System.out.println("Writing JSON object to file");  
            System.out.println("-----------------------");  
            System.out.print(countryObj);  
  
            fileWriter.write(countryObj.toJSONString());  
            fileWriter.flush();  
            fileWriter.close();  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }

	}

}
