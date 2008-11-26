package de.rwth.matse.jgeoviz;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import de.rwth.matse.jgeoviz.tilefactories.*;

public class Test2 {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		
		JButton submit = new JButton("Submit");
		JTextField command = new JTextField("52074");
		JPanel controls = new JPanel();
		
        TileFactoryInfo info = new CachedTileFactoryInfo();
        info.setDefaultZoomLevel(1);
        
        TileFactory tf = new DefaultTileFactory(info);
        JXMapKit map = new JXMapKit();
        map.setTileFactory(tf);
        
        map.setCenterPosition(new GeoPosition(51,30,25,0,7,39));
        map.setZoom(6);
		
		controls.setLayout(new FlowLayout());
		controls.add(command);
		controls.add(submit);
		
		f.setLayout(new BorderLayout());
		
		f.add(controls, BorderLayout.NORTH);
		f.add(map, BorderLayout.CENTER);
		
		f.setVisible(true);
		f.setSize(600,400);
	}
}
