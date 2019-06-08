package org.openjfx.hellofx.GUI;

import org.openjfx.hellofx.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			new MainFrame();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
