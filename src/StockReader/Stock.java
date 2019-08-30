package StockReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import CentralLogger.SendLogThread;
import Client.GUI.EditStockBox;
import Client.GUI.StockEditFrame;


public class Stock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String statsUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="; //  API link for current statistics.
	static String symbolUrl = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="; // API link for general knowledge
	private static final String APIKEY = "&apikey=TJQA3XTGE2BLYKP4";
	private String name,label;
	private double price;
	private double changedollar,changepercent,opengate,yesterdaygate;
	private int volume;
	
	public Stock(String name, String label, double price) {
		this.name=name;
		this.label=label;
		this.price=price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public static Stock createStock(String name, String label, double price) {
		return new Stock(name,label,price);
	}
	
	private static String readAll(Reader rd) throws IOException { //Helps parsing the JSON file.
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	
	public static Stock findStockName(String label) throws IOException,NumberFormatException,StringIndexOutOfBoundsException {
		 InputStream istats = null;
		 InputStream isymbol = null;
	    try {
	    	
		  istats = new URL(statsUrl + label + APIKEY).openStream();
		  isymbol = new URL(symbolUrl + label + APIKEY).openStream();
	    	
	      //Search for Stock
	      BufferedReader symbolsearch = new BufferedReader(new InputStreamReader(isymbol, Charset.forName("UTF-8")));
	      String jsonTextSymbol = readAll(symbolsearch);
	      JSONObject jsonsy = new JSONObject(jsonTextSymbol);
	      JSONArray bestMatches = (JSONArray)jsonsy.get("bestMatches");
	      JSONObject jsonobject = bestMatches.getJSONObject(0);
	      String name = jsonobject.getString("2. name"); //Gets name.
	      
	      //STATS Gathering
	      BufferedReader stats = new BufferedReader(new InputStreamReader(istats, Charset.forName("UTF-8")));
	      String jsonTextStats = readAll(stats);
	      JSONObject jsonst = new JSONObject(jsonTextStats);
	      JSONObject GlobalQuote = null;
	      try {
	    	  GlobalQuote = (JSONObject)jsonst.get("Global Quote"); // fails here
	      }
	      catch(Exception e) {
	    	  throw new Exception ("Failed to get statistics from API call: " + statsUrl);
	      }
	      String priceString = (String)GlobalQuote.get("05. price");
	      Double price = Double.parseDouble(priceString);
	      String change = (String)GlobalQuote.get("09. change");
	      String changeP = (String)GlobalQuote.get("10. change percent");
	      changeP = changeP.substring(0, changeP.length()-3);
	      Stock tmp = new Stock(name,label,price);
	      tmp.setChangedollar(Double.parseDouble(change));
	      tmp.setChangepercent(Double.parseDouble(changeP));
	      new SendLogThread(Level.INFO,new Exception("Stock " + name + " was successfuly created")).start();
	      return tmp;
	    } 
	    catch(Exception e) {
	    	new SendLogThread(Level.SEVERE,e).start();
	    }
	    finally {
	    	istats.close();
	    	isymbol.close();
	    }
	    return null;
	}
	
	
	
	public void updateStats() throws IOException {
	    InputStream istats = null;
	    try {
	    	
	      istats = new URL(statsUrl + this.getLabel() + APIKEY).openStream();
         
	      //STATS Gathering
	      BufferedReader stats = new BufferedReader(new InputStreamReader(istats, Charset.forName("UTF-8")));
	      String jsonTextStats = readAll(stats);
	      JSONObject jsonst = new JSONObject(jsonTextStats);
	      JSONObject GlobalQuote = (JSONObject)jsonst.get("Global Quote");
	      String priceString = (String)GlobalQuote.get("05. price");
	      Double price = Double.parseDouble(priceString);
	      String change = (String)GlobalQuote.get("09. change");
	      String changeP = (String)GlobalQuote.get("10. change percent");
	      changeP = changeP.substring(0, changeP.length()-3);
	      this.setChangedollar(Double.parseDouble(change));
	      this.setChangepercent(Double.parseDouble(changeP));
	      this.setPrice(price);
	      new SendLogThread(Level.INFO,new Exception("Stock " + name + " was successfuly updated")).start();
	    } 
	    catch(Exception e) {
	    	new SendLogThread(Level.SEVERE,e).start();
	    }
	    finally {
	    	istats.close();
	    }
	}
	
	public String toString() {
		return "Stock name: " + this.getName() + " (" + this.getLabel() + ")" + "\nCurrent price: " + this.getPrice() + "\nChange in dollar: "+ this.getChangedollar() + "\nChange in percent: "+ this.getChangepercent() + "\nVolume: " + this.getVolumeString() + "\nOpen gate: " + this.getOpengate() + "\nYesterday closing gate: " + this.getYesterdaygate();
		
	}

	public double getChangedollar() {
		return changedollar;
	}

	public void setChangedollar(double changedollar) {
		this.changedollar = changedollar;
	}

	public double getChangepercent() {
		return changepercent;
	}

	public void setChangepercent(double changepercent) {
		this.changepercent = changepercent;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public String getVolumeString() {
		String vol = Integer.toString(this.volume);
		int num = vol.length()/3;
		char[] tmp = new char[vol.length()+num];
		int j=0;
		for(int i=0;i<tmp.length;i++) {
			if(i>0&&i%3==0&&i<tmp.length-1) {
				tmp[i] = ',';
			}
			else {
				tmp[i] = vol.charAt(j);
				j++;
			}
		}
		vol = String.copyValueOf(tmp);
		return vol;

	}

	public double getOpengate() {
		return opengate;
	}

	public void setOpengate(double opengate) {
		this.opengate = opengate;
	}

	public double getYesterdaygate() {
		return yesterdaygate;
	}

	public void setYesterdaygate(double yesterdaygate) {
		this.yesterdaygate = yesterdaygate;
	}
	
	public EditStockBox toEdit(int index,StockEditFrame p) {
		return new EditStockBox(this,index,p,1);
	}

}
