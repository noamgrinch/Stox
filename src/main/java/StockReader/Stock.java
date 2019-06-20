package StockReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.openjfx.hellofx.GUI.EditStockBox;
import org.openjfx.hellofx.GUI.StockEditFrame;

public class Stock {
	
	private String name,label;
	private double price;
	private double changedollar,changepercent,opengate,yesterdaygate;
	private int volume;
	private static final long SKIP_STATS = 46100; 
	private static final long SKIP_NAME=46000;
	
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
	
	public static Stock findStockName(String label) throws IOException,NumberFormatException,StringIndexOutOfBoundsException {
		
		String ur = "http://wallstreet.bizportal.co.il/stock.php?id=" + label;
		URL url = new URL(ur);
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buf = new BufferedReader(inStream);
		String name = "Not found";
		buf.skip(SKIP_NAME);
		String line = buf.readLine();
		name = line;
		while(line!=null) {
			if(line.contains("ìà ðîöà")) {
				name = "Not found";
				break;
			}
			/*Extracts name*/
			if(line.contains("TR1") && line.contains("</tr>")) {
				name = line;
				name = buf.readLine();
				name = buf.readLine();
				int td = name.indexOf("</td>");
				int beg = td;
				while(name.charAt(beg) != '>') {
					beg--;
				}
				name = name.substring(beg+1,td);
				System.out.println(name);
				break;
			}
			line = buf.readLine();
		}
		if(name==null || name.equals("Not found")) {
			return null;
		}
		else {
			return findStock(label,name);
		}
	}
	
	public static Stock findStock(String label,String name) throws IOException,NumberFormatException,StringIndexOutOfBoundsException {
		boolean finished=false;
		String ur = "http://wallstreet.bizportal.co.il/stock.php?id=" + label + "&story=abs";
		URL url = new URL(ur);
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buf = new BufferedReader(inStream);
		String lastprice = "Not found";
		String changedol = "Not found";
		String changeper = "Not found";
		String opengate = "Not found";
		String yesterdaygate = "Not found";
		String volume = "Not found";
		buf.skip(SKIP_STATS);
		String line = buf.readLine();
		int i=0;
		while(line!=null) {
			if(line.contains("#c5c5c5")&&i<6) {
				line = buf.readLine();
				int end = line.indexOf("</div>");
				int back = end;
				while(line.charAt(back) != '>') {
					back--;
				}
				lastprice = line.substring(back+1,end);
				i++;
				while(i<6&&line!=null) {
					if(line.contains("#c5c5c5") && i==1) { //change in dollars
						line = buf.readLine();
						end = line.indexOf("</span>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}
						changedol = line.substring(back+1,end);
						i++;
					}
					if( line.contains("#c5c5c5") && i==2) { //change in percentage
						line = buf.readLine();
						end = line.indexOf("</span>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}
						
						changeper = line.substring(back+1,end-1);
						i++;
					}
					
					if( line.contains("#c5c5c5") && i==3) { //volume
						line = buf.readLine();
						end = line.indexOf("</div>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}
						volume = line.substring(back+1,end);
						i++;
					}
					
					if( line.contains("#c5c5c5") && i==4) { //Opening gate
						line = buf.readLine();
						end = line.indexOf("</div>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}
						opengate = line.substring(back+1,end);
						i++;
					}
					
					if( line.contains("#c5c5c5") && i==5) { //yesterday's closing gate.
						line = buf.readLine();
						end = line.indexOf("</div>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}
						yesterdaygate = line.substring(back+1,end);
						i++;
					}
					if(i==6) {
						finished=true;
						break;
					}
					line = buf.readLine();
					
				}
				
			}
			if(finished) {
				break;
			}
			line = buf.readLine();		
		}
		if(lastprice.equals("Not found") || volume.equals("Not found") || opengate.equals("Not found") || yesterdaygate.equals("Not found") || changedol.equals("Not found") || changeper.equals("Not found")) {
			return null;
		}
		if(volume.contains(",")) {
			volume = volume.replace(",", "");
		}
		if(lastprice.contains(",")) {
			lastprice = lastprice.replace(",", "");
		}
		if(opengate.contains(",")) {
			opengate = opengate.replace(",", "");
		}
		if(yesterdaygate.contains(",")) {
			yesterdaygate = yesterdaygate.replace(",", "");
		}
		Stock tmp = new Stock(name,label,Double.parseDouble(lastprice));
		if(changedol.charAt(0)=='+') {
			tmp.setChangedollar(Double.parseDouble(changedol.substring(1, changedol.length())));
		}
		else {
			if(changedol.equals("0")) {
				tmp.setChangedollar(0);
			}
			else {
				tmp.setChangedollar(-(Double.parseDouble(changedol.substring(1, changedol.length()))));
			}
		}
		if(changeper.charAt(0)=='+') {
			tmp.setChangepercent(Double.parseDouble(changeper.substring(1, changeper.length())));
		}
		else {
			if(changeper.equals("0")) {
				tmp.setChangedollar(0);
			}
			else {
				tmp.setChangepercent(-(Double.parseDouble(changeper.substring(1, changeper.length()))));
			}
		}
		tmp.setVolume(Integer.parseInt(volume));
		tmp.setOpengate(Double.parseDouble(opengate));
		tmp.setYesterdaygate(Double.parseDouble(yesterdaygate));
		return tmp;
	}
	
	
	public void updateStats() throws IOException {
		boolean finished=false;
		String ur = "http://wallstreet.bizportal.co.il/stock.php?id=" + label + "&story=abs";
		URL url = new URL(ur);
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buf = new BufferedReader(inStream);
		String lastprice = "Not found";
		String changeper = "Not found";
		buf.skip(SKIP_STATS);
		String line = buf.readLine();
		int i=0;
		while(line!=null) {
			if(line.contains("#c5c5c5")) {
				line = buf.readLine();
				int end = line.indexOf("</div>");
				int back = end;
				while(line.charAt(back) != '>') {
					back--;
				}
				lastprice = line.substring(back+1,end);
				i++;
				while(line!=null) {
					if( line.contains("#c5c5c5")&&i==2) { //change in percentage
						line = buf.readLine();
						end = line.indexOf("</span>");
						back = end;
						while(line.charAt(back) != '>') {
							back--;
						}						
						changeper = line.substring(back+1,end-1);
						finished=true;
						break;
					}
					if(line.contains("#c5c5c5")) {
						i++;
					}
					line = buf.readLine();
					
				}
				
			}
			if(finished) {
				break;
			}
			line = buf.readLine();
		}

		
		


		if(lastprice.contains(",")) {
			lastprice = lastprice.replace(",", "");
		}

		if(changeper.charAt(0)=='+') {
			this.setChangepercent(Double.parseDouble(changeper.substring(1, changeper.length())));
		}
		else {
			if(changeper.equals("0")) {
				this.setChangedollar(0);
			}
			else {
				this.setChangepercent(-(Double.parseDouble(changeper.substring(1, changeper.length()))));
			}
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
