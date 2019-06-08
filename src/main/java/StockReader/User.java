package StockReader;

import java.util.ArrayList;

public class User {
	
	private ArrayList<String> stocks;
	private String name,password;
	
	public User() {
		stocks = new ArrayList<String>();
	}
	
	public User(String name,String password) {
		this.name=name;
		this.password=password;
		stocks = new ArrayList<String>();
	}
	
	public void addStock(String label) {
		stocks.add(label);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<String> getLabels(){
		return this.stocks;
	}

}
