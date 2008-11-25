package de.rwth.matse.jgeoviz;

import java.awt.*;
import javax.swing.*;
import org.jdesktop.swingx.*;

public class HelloWorld {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		JButton submit = new JButton("Submit");
		JTextField command = new JTextField("52074");
		JPanel controls = new JPanel();
		
		JXMapKit mapview = new JXMapKit();
		mapview.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
		
		controls.setLayout(new FlowLayout());
		controls.add(command);
		controls.add(submit);
		
		f.setLayout(new BorderLayout());
		
		f.add(controls, BorderLayout.NORTH);
		f.add(mapview, BorderLayout.CENTER);
		
		f.setVisible(true);
		f.setSize(600,400);
		
	}
}
