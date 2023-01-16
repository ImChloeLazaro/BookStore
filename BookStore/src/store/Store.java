package store;

import java.sql.SQLException;

public class Store {
	
	public static void menu() {
		Customer customer = new Customer();
		while(true) {
			System.out.println("Welcome to MEMA Book Store");
			System.out.println("1. Login");
			System.out.println("2. Register");
			
			switch(Input.InputInteger("CHOOSE:")) {
				case 1:
					customer.login();
					break;
				case 2:
				try {
					customer.register();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				default:
					System.err.println("INVALID OPTION!!!");
			}
		}
	}
	
}
