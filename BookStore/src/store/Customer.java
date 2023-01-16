package store;

import java.sql.SQLException;

public class Customer{

	static User user = new User();
	static Admin admin = new Admin();

	public void dashboard() {

		Database.getInstance();
		
		while(true) {
			
			System.out.println("WELCOME CUSTOMER!");
			
			System.out.println("1. Buy Book");
			System.out.println("2. Show Book List");
			System.out.println("3. Transaction History");
			System.out.println("4. Exit");
			
			switch(Input.InputInteger("CHOOSE:")) {
			case 1:
				System.out.println(user.getEmail());
				Admin.show();
				buy();
				break;
			case 2:
				Admin.show();
				break;
			case 3:
				Admin.TransactionbyUser(11);
				break;
			case 4:
				System.out.println("BYE!");
				System.exit(0);
				break;
			default:
				System.err.println("WRONG INPUT!");
			}
		}
	}
	public void register() throws SQLException {
		user.setName(Input.InputString("Enter Your Name:"));
		user.setEmail(Input.InputString("Enter Your Email:"));
		user.setPassword(Input.InputString("Enter Your Password:"));
		
		//All Fields must not empty.
		if(user.getName() == "" || user.getEmail() == "" || user.getPassword() == "") {
			System.err.println("ALL FIELDS NEED TO SPECIFY!");
			return;
		}
		//Email should have @.
		if(!user.getEmail().contains("@")) {
			System.err.println("User Email should be an Email!");
			return;
		}
		
		//Password must 8 characters.
		if(user.getPassword().length() < 7){
			System.err.println("Password must have at least 8 Characters");
			return;
		}
		
		//Check email if it is already exist in database.
		if(!Database.CheckEmail(user.getEmail())) {
			Database.register(user.getName(), user.getEmail(), user.getPassword());
			System.out.println("ACCOUNT CREATED SUCCESSFULLY, YOU CAN LOGIN NOW!");
		}else {
			System.err.println("Email is already REGISTERED!");
		}
	}
	
	public void login() {
		
		try {
			
			String relogin = "";
			
			do {
				user.setEmail(Input.InputString("Enter Your Email:"));
				user.setPassword(Input.InputString("Enter Your Password:"));
				
				String validated = Database.login(user.getEmail(), user.getPassword());
				
				switch(validated) {
				case "user":
					dashboard();
					break;
				case "admin":
					admin.dashboard();
					break;
				default:
					System.err.println("WRONG EMAIL OR PASSWORD!");
					relogin = Input.InputString("Want to relogin? Y/N");
				}
			}while(relogin.equalsIgnoreCase("Y"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	public void buy() {
		
		try {
			
			int id = Input.InputInteger("ENTER BID OF BOOK YOU WANT TO BUY?");
			int quantity = Input.InputInteger("Quantity:");
			int total = Database.ShowBookPrice(id) * quantity;
			
			Database.buy(Database.ShowUserbyEmail(user.getEmail()), id, quantity, total);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
