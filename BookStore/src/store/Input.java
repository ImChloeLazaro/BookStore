package store;

import java.util.Scanner;

public class Input {
	
	static Scanner scan = new Scanner(System.in);
	public static String inputString;
	public static int inputInteger;
	public static double inputDouble;
	public static boolean isValid;
	
	public static String InputString(String message) {
		
		do {

			isValid = true;
			System.out.println(message);
			try {
				
				inputString = scan.nextLine();
				
			}catch(Exception e) {
				
				isValid = false;
				System.err.println("UNKNOWN ERROR!");
			}
		}while(!isValid);
		
		return inputString;
	}
	
	public static int InputInteger(String message) {
		
		do {
			isValid = true;
			System.out.println(message);
			try {
				inputInteger = Integer.parseInt(scan.nextLine());
				
			}catch(Exception e) {
				
				System.err.println("YOUR INPUT MUST INTEGER!");

				isValid = false;
			}
			
		}while(!isValid);
		
		return inputInteger;
	}
	
	public static int InputDouble(String message) {
		
		do {
			isValid = true;
			System.out.println(message);
			try {
				inputDouble = Double.parseDouble(scan.nextLine());
				
			}catch(Exception e) {
				
				System.err.println("YOUR INPUT MUST HAVE DECIMAL POINT!");

				isValid = false;
			}
			
		}while(!isValid);
		
		return inputInteger;
	}
}
