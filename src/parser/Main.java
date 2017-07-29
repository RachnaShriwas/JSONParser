package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.NullCipher;

public class Main {

	public static void main(String[] args) {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(args[0]));
			String jsonString = "";
			String s = null;
			
			while((s = br.readLine()) != null)
				jsonString += s;
			
			jsonString = jsonString.replaceAll("\\s+","");
			
			JSONParser p = new JSONParser(jsonString);
			
			if(p.validate() == true) 
				System.out.println("Yipee! The JSON file is correct");
			else
				System.out.println("Sorry.. Your JSON file does not seem to be correct.");
			
		}catch(FileNotFoundException e) {
		
			System.out.println("Oops! I could not find the file.");
			
		} catch (IOException e) {
			
			System.out.println("Something wrong with reading the file!!");
		
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Please pass the file name as the argument.");
		}finally {
			
			try {
				br.close();
			}catch(IOException e) {
				System.out.println("Attention!! The file could not be closed!!");
			}catch(NullPointerException e) {
				System.out.println("Oops! I could not find the file.");
			}
			
		}
	}
}
