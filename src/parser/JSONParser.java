package parser;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONParser {
	String jsonString;
	static int i = 0;
	private char ch;
	private static final char EMPTY_CHAR = '\u0000';
	
	public JSONParser(String s) {
		jsonString = s;
		ch = jsonString.charAt(0);
	}
	
	boolean validate() {
		return isValidValue();
	}
	
	private boolean isValidValue() {

		switch(ch) {
			case '{': {
				if(!isValidObject())
					return false;
				break;
			}
			case '[': {
				if(!isValidArray())
					return false;
				break;
			}
			case '\"': {
				if(!isValidString())
					return false;
				break;
			}
			case 't': 
			case 'f': {
				if(!isValidBool()) 
					return false;
				break;
			}
			case 'n': {
				if(!isValidNUll()) 
					return false;
				break;
			}
			default: { 
				if(ch == '-' || (ch >= '0' && ch <= '9')) { 
					if(!isValidNumber())
						return false;
					return true;
				} else {
					System.out.println("Something wrong at position: " + (i+1));
					return false;
				}
			}
		}
		return true;
	}
	
	private void next()
	{
		
		if(i < jsonString.length()-1) {
			ch = jsonString.charAt(++i);
			
		}
		else ch = EMPTY_CHAR;
	}
	
	private boolean isValidObject() {
		
		
		if(ch == '{') {
			next();
			
			if(ch == '}') {
				next();
				return true; 
			}
		
			do {
				if(ch == ',') 
					next();
				if(isValidString()) {
					if(ch != ':'){
						System.out.println("Object property expecting \":\" at position: " + (i+1));
						return false;
					}
					next();
					if(isValidValue()) { 
						if(ch == '}') {  
							next();
							return true;
						}
					}
					else {
						System.out.println("Invalid value of object at position: " + (i+1));
						return false;
					}
				}
				else {
					System.out.println("Invalid string of object at position: " + (i+1));
					return false;
				}
			} while(ch == ','); 

			return false;
		}
		else
			return false;
	}
	
	private boolean isValidArray() {
		ArrayList<Character> arr = new ArrayList<Character>();
		
		if(ch == '[') {
			next();
			if(ch == ']')
				return true;
			
			do {
				if(ch == ',')	next();
				if(isValidValue()){
				arr.add(ch);
				if(ch == ']')
				{
					next();
					return true;
				}
				}
			}while(ch == ',');
			
			System.out.println("Invalid array at position: " + (i+1));
			return false;
		}
		else
			return false;
	}
	
	@SuppressWarnings("serial")
	private boolean isValidString() {
		HashMap<Character, Character> escapes = new HashMap<Character, Character>(){{
												put('b', '\b');
												put('n', '\n');
												put('t', '\t');
												put('r', '\r');
												put('f', '\f');
												put('\"', '\"');
												put('\\', '\\');
												}};
		
		String s = "";
		
		if(ch == '\"') {
			next();
			
			while(i < jsonString.length() && ch != EMPTY_CHAR){
				if(ch == '\"'){
					next();
					return true;
				}
				if(ch == '\\'){
					next();
					if(escapes.containsKey(ch))
					{
						s += escapes.get(ch);
					}
					else
					{
						s += ch;
					}
				}
				else
				{
					s += ch;
				}
				next();
			}
			
			System.out.println("Invalid string at position: " + (i+1));
			return false;
		}
		else
			System.out.println("Invalid string at position: " + (i+1));
		return false;
	}
	
	private boolean isValidBool() {
		if(ch == 't') {
			next();
			if(ch == 'r') {
				next();
				if(ch == 'u') {
					next();
					if(ch == 'e') {
						next();
						return true;
					}
					else
						System.out.println("Invalid Boolean at position: " + (i+1));
				}
				else
					System.out.println("Invalid Boolean at position: " + (i+1));
			}
			else
				System.out.println("Invalid Boolean at position: " + (i+1));
		}
		else if(ch == 'f') {
			next();
			if(ch == 'a') {
				next();
				if(ch == 'l') {
					next();
					if(ch == 's') {
						next();
						if(ch == 'e') {
							next();
							return true;
						}
						else
							System.out.println("Invalid Boolean at position: " + (i+1));
					}
					else
						System.out.println("Invalid Boolean at position: " + (i+1));
				}
				else
					System.out.println("Invalid Boolean at position: " + (i+1));
			}
			else
				System.out.println("Invalid Boolean at position: " + (i+1));
		}
		
		return false;
	}
	
	private boolean isValidNUll() {
		next();
		if(ch == 'u') {
			next();
			if(ch == 'l') {
				next();
				if(ch == 'l') {
					next();
					return true;
				}
				else 
					System.out.println("Invalid NULL at position: " + (i+1));
					
			}
			else
				System.out.println("Invalid NULL at position: " + (i+1));
		}
		else
			System.out.println("Invalid NULL at position: " + (i+1));
		return false;
	}
	
	private String getDigits() {
		String num = "";
		while(ch>='0' && ch<='9')
		{
			num += ch;
			next();
		}
		return num;
		
	}
	private boolean isValidNumber() {
		String num = "";
		
		//If a number starts with negative sign
		if(ch == '-') {
			num += ch;
			next();
		}
		
		//get digits of the number
		num += getDigits();
		
		//get decimal point if there
		if(ch == '.') {
			num += ch;
			next();
			getDigits();
		}

		//get exponential if there
		if(ch == 'e' || ch == 'E') {
			num += ch;
			next();
			
			// required - get sign of exponent
			if(ch == '-' || ch == '+') {
				num += ch;
				next();
			}
			getDigits(); // exponent
		}
		
		try {
			Double d = Double.parseDouble(num);
			if(d.isNaN())
				return false;
			else
				return true;
		}catch(NumberFormatException e) {
			System.out.println("Invalid Number at position: " + (i+1));
			return false;
		}
	}
	
	public JSONObject parseJson() {
		//TODO
		return null;
	}	
}
