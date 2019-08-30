package Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import StockReader.Stock;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private ArrayList<Stock> stocks;
	
	
	public User(String u,String p,String e) {
		setUsername(u);
		setPassword(p);
		setEmail(e);
		stocks = new ArrayList<Stock>();
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public ArrayList<Stock> getStocks() {
		return stocks;
	}


	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public void initStocks(String st) {
	  if(st!=null) {
		  try {
			  String[] splitStr = st.split("\\s+");
			  for(int i=0;i<splitStr.length;i++) {
				  stocks.add(Stock.findStockName(splitStr[i]));
			  }
		  }
		  catch(Exception e) {
			  new SendLogThread(Level.SEVERE,e).start();
		  }
	  }
	}

}
