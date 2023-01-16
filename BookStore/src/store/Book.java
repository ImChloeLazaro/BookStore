package store;

public class Book {

	private int id;
	private String title;
	private int Categories;
	private int price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategories() {
		return Categories;
	}

	public void setCategories(int categories) {
		Categories = categories;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
