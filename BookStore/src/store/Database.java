package store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static Connection connection = null;
	public static PreparedStatement stmt = null;
	
	private Database() {
		System.out.println("Connected!");
	}
	
	public static Connection getInstance() {
		
		String url = "jdbc:mysql://localhost:3306/bookstore";
		String root = "root";
		String password = "";
		
		if(connection == null)
			
			try {
	            connection = DriverManager.getConnection(url, root, password);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		
		return connection;
	}


	public static void register(String name, String email, String password) {
		
		try {
			stmt = connection.prepareStatement("INSERT INTO `users`( `name`, `email`, `password`) VALUES (?, ?, ?)");
			
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);		
			stmt.execute();
			System.out.println("REGISTRATION SUCCESS!");
		} catch (SQLException e) {
			System.err.println("REGISTRATION ERROR!");
		}
		
	}
	
	public static boolean CheckEmail(String email) throws SQLException {
		
			stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
			stmt.setString(1, email);
			stmt.execute();
			ResultSet users = stmt.getResultSet();
			return users.next();
	}
	
	public static String login(String email, String password) throws SQLException {
			stmt = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			stmt.execute();
			ResultSet users = stmt.getResultSet();
			
			if(!users.next()) {
				return "";
			}
			return users.getString("type");
		
	}
	
	public static void InsertBook(String title, int categories, int price) {
		try {
			stmt = connection.prepareStatement("INSERT INTO `books`(`title`, `categories`, `price`) VALUES (?, ?, ?)");
			stmt.setString(1, title);
			stmt.setLong(2, categories);
			stmt.setInt(3, price);
			stmt.execute();
			
			System.out.println("New Book Added!");
		} catch (SQLException e) {
			System.err.println("Adding Error!");
		}
	}
	
	public static PreparedStatement ShowBook(){
			try {
				stmt = connection.prepareStatement("SELECT * FROM books");
				stmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return stmt;
	}
	
	public static String ShowBookName(int id) {
		
			String book = "";
			try {
				stmt = connection.prepareStatement("SELECT title FROM `books` WHERE id = ?");
				stmt.setInt(1, id);
				stmt.execute();
				ResultSet users = stmt.getResultSet();
				if(users.next()) {
					book = users.getString("title");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return book;
	}
	
	public static int ShowBookPrice(int id) throws SQLException {
		
			stmt = connection.prepareStatement("SELECT price FROM `books` WHERE id = ?");
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet book = stmt.getResultSet();
			if(!book.next()) {
				System.err.println("No Book Found!");
			}
			
			return book.getInt("price");
		
	}
	
	
	public static String ShowBookCategory(int id) {
		
		String category = "";
		
			switch(id) {
			case 1:
				category = "Thriller";
				break;
			case 2:
				category = "Poetry";
				break;
			case 3:
				category = "Romance";
				break;
			case 4:
				category = "Horror";
				break;
			case 5:
				category = "Fantasy";
				break;
			case 6:
				category = "Adventure";
				break;
			case 7:
				category = "Classic";
				break;
			case 8:
				category = "Historical";
				break;
			}
		return category;
	}
	
	public static void UpdateBook(String title, int categories, int price, int id) {
		try {
			stmt = connection.prepareStatement("UPDATE `books` SET `title`= ?,`categories`= ?,`price`= ? WHERE id  = ?");
			stmt.setString(1, title);
			stmt.setLong(2, categories);
			stmt.setInt(3, price);
			stmt.setInt(4, id);
			stmt.execute();
			
			System.out.println("Update Success!");
		} catch (SQLException e) {
			System.err.println("Update Error!");
		}
	}
	
	public static void DeleteBook(int id) {
		try {
			stmt = connection.prepareStatement("DELETE FROM `books` WHERE id = ?");
			stmt.setInt(1, id);
			stmt.execute();
			
			System.out.println("Delete Success!");
		} catch (SQLException e) {
			System.err.println("Delete Error!");
		}
	}
	
	public static void Transaction() {
		try {
			stmt = connection.prepareStatement("SELECT * FROM order_details");
			stmt.execute();
			ResultSet result = stmt.getResultSet();
			
			
			 while(result.next()) {
				System.out.printf("| %-25s | %-25s | %-10s | %-10s | %-15s|%n", ShowUser(result.getInt("customer")), ShowBookName(result.getInt("book")), result.getString("quantity"), result.getString("total"), result.getString("date"));
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public static void UserTransaction() {
		try {
			stmt = connection.prepareStatement("SELECT * FROM order_details ");
			stmt.execute();
			ResultSet result = stmt.getResultSet();
			
			
			 while(result.next()) {
				System.out.printf("| %-25s | %-25s | %-10s | %-10s | %-15s|%n", ShowUser(result.getInt("customer")), ShowBookName(result.getInt("book")), result.getString("quantity"), result.getString("total"), result.getString("date"));
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//	User Related Transactions.
	
	public static String ShowUser(int id) {
		
		String user = "";
		try {
			stmt = connection.prepareStatement("SELECT name FROM users WHERE id = ?");
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet users = stmt.getResultSet();
			if(users.next()) {
				user = users.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static void buy(int user, int book, int quantity, int total) {
		try {
			stmt = connection.prepareStatement("INSERT INTO `order_details`(`customer`, `book`, `quantity`, `total`) VALUES (?, ?, ?, ?)");
			stmt.setInt(1, user);
			stmt.setInt(2, book);
			stmt.setInt(3, quantity);
			stmt.setInt(4, total);
			stmt.execute();
			
			System.out.println("New Order Added!");
		} catch (SQLException e) {
			System.err.println("Order Error!" + e);
		}
	}
	
	public static int user(String email) {
		int id = 0;
		try {
			stmt = connection.prepareStatement("SELECT id FROM users WHERE email = ?");
			stmt.setString(1, email);
			stmt.execute();
			ResultSet users = stmt.getResultSet();
			if(users.next()) {
				id = users.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public static void CustomerTransaction(int id) {
		try {
			stmt = connection.prepareStatement("SELECT * FROM order_details WHERE customer = ?");
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet result = stmt.getResultSet();
			
			
			 while(result.next()) {
				System.out.printf("| %-25s | %-25s | %-10s | %-10s | %-15s|%n", ShowUser(result.getInt("customer")), ShowBookName(result.getInt("book")), result.getString("quantity"), result.getString("total"), result.getString("date"));
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
