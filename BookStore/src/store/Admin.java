package store;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	
	static Book book = new Book();
	public void dashboard() {

		Database.getInstance();
		
		while(true) {
			
			System.out.println("WELCOME ADMIN");
			
			System.out.println("1. Add Book");
			System.out.println("2. Show Book");
			System.out.println("3. Edit Book");
			System.out.println("4. Delete Book");
			System.out.println("5. View Transactions");
			System.out.println("6. Exit");
			
			switch(Input.InputInteger("CHOOSE:")) {
			case 1:
				System.out.println("Add Book");
				insert();
				break;
			case 2:
				System.out.println("Show Book");
				show();
				break;
			case 3:
				System.out.println("Update Book");
				update();
				break;
			case 4:
				System.out.println("Delete Book");
				delete();
				break;
			case 5:
				System.out.println("Transactions");
				transaction();
				break;
			case 6:
				System.out.println("BYE!");
				System.exit(0);
				break;
			default:
				System.err.println("WRONG INPUT!");
			}
		}
		
	}
	
	public void insert() {
		
		book.setTitle(Input.InputString("Title:"));

		categories();
		
		book.setCategories(Input.InputInteger("CHOOSE:"));
		book.setPrice(Input.InputInteger("Price:"));
		
		Database.InsertBook(book.getTitle(), book.getCategories(), book.getPrice());

	}
	
	public static void show() {
		try {
			ResultSet result = Database.ShowBook().getResultSet();
			System.out.printf("--------------------------------------------------------------------------------%n");
			System.out.printf("-                                  BOOK LIST                                   -%n");
			System.out.printf("--------------------------------------------------------------------------------%n");
			System.out.printf("| %-40s | %-20s | %-10s |%n", "Title", "Categories", "Price");
			System.out.printf("--------------------------------------------------------------------------------%n");
			while(result.next()) {
				System.out.printf("| %-40s | %20s | %-10s |%n","BID: " + result.getInt("id") + " => " + result.getString("title"), Database.ShowBookCategory( result.getInt("categories")), result.getString("price"));
			}
			System.out.printf("--------------------------------------------------------------------------------%n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
		book.setTitle(Input.InputString("Title:"));
		
		categories();
		
		book.setCategories(Input.InputInteger("CHOOSE:"));
		book.setPrice(Input.InputInteger("Price:"));
		book.setId(Input.InputInteger("Enter Book ID to Update the content: "));
		
		String confirmation = Input.InputString("Do you want to UPDATE this book? Y/N");
		
		if(confirmation.equalsIgnoreCase("Y"))
			Database.UpdateBook(book.getTitle(), book.getCategories(), book.getPrice(), book.getId());
		else 
			System.out.println("Update Cancelled!");
		
	}
	
	public void delete() {
		book.setId(Input.InputInteger("Enter Book ID to Delete from Database"));
		
		String confirmation = Input.InputString("Do you want to DELETE this book? Y/N");
		
		if(confirmation.equalsIgnoreCase("Y"))
			Database.DeleteBook(book.getId());
		else 
			System.out.println("Delete Cancelled!");
		
	}
	
	public void transaction() {
			System.out.printf("----------------------------------------------------------------------------------------------------%n");
			System.out.printf("-                                            Transactions                                          -%n");
			System.out.printf("----------------------------------------------------------------------------------------------------%n");
			System.out.printf("| %-25s | %-25s | %-10s | %-10s | %-15s|%n", "Customer", "Book", "Quantity", "Price", "Purchase Date");
			System.out.printf("----------------------------------------------------------------------------------------------------%n");
			Database.Transaction();
			System.out.printf("----------------------------------------------------------------------------------------------------%n");
	}
	
	public static void categories() {
		System.out.println("SELECT CATEGORIES:");
		System.out.println("1. Thriller");
		System.out.println("2. Poetry");
		System.out.println("3. Romance");
		System.out.println("4. Horror");
		System.out.println("5. Fantasy");
		System.out.println("6. Adventure");
		System.out.println("7. Classics");
		System.out.println("8. Historical");
	}
}
