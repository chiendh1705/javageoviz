package de.rwth.matse.jgeoviz;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import de.rwth.matse.jgeoviz.georequests.GermanPostalCodeRequest;
import de.rwth.matse.jgeoviz.tilefactories.*;

public class Test2 implements ActionListener{
	
	JTextField command;
	JXMapKit map;
	
	public static void main(String[] args) {
		new Test2();
	}
	
	public Test2(){
		JFrame f = new JFrame();
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		command = new JTextField("52074",8);
		JPanel controls = new JPanel();
		
        TileFactoryInfo info = new CachedTileFactoryInfo();
        info.setDefaultZoomLevel(1);
        
        TileFactory tf = new DefaultTileFactory(info);
        map = new JXMapKit();
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
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent arg0) {
		GermanPostalCodeRequest c = new GermanPostalCodeRequest(command.getText());
		if(c != null){
			map.setCenterPosition(c.getCoordinates().toGeoPosition());
		}
		
		for(int i=0; i<c.getAdditionalInformation().length; i++){
			System.out.print((String)(c.getAdditionalInformation()[i]) + " ");
		}
		System.out.println();
	}
}
