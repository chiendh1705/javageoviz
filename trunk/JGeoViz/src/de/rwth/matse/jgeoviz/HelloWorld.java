package de.rwth.matse.jgeoviz;

import javax.swing.*;
import org.jdesktop.swingx.*;

public class HelloWorld {
	public static void main(String[] args) {
		JXMapKit k = new JXMapKit();
		JFrame f = new JFrame();
		
		k.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
		
		f.add(k);
		
		f.setVisible(true);
		
	}
}
