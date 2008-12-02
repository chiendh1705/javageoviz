package de.rwth.matse.jgeoviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import de.rwth.matse.jgeoviz.georequests.GermanPostalCodeRequest;
import de.rwth.matse.jgeoviz.tilefactories.*;
import de.rwth.matse.jgeoviz.waypoints.*;

public class Test2 implements ActionListener{
	
	JTextField command;
	JXMapKit map;
	JFrame f;
	
	WaypointPainter<JXMapViewer> painter; 
	
	public static void main(String[] args) {
		new Test2();
	}
	
	public Test2(){
		f = new JFrame();
		
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
		
		painter = new WaypointPainter<JXMapViewer>();
		painter.setRenderer(new CircularWaypointRenderer());
		map.getMainMap().setOverlayPainter(painter);
		
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
			GeoPosition pos = c.getCoordinates().toGeoPosition();
			map.setCenterPosition(pos);
			
			Set<Waypoint> waypoints = painter.getWaypoints();
			waypoints.add(new Waypoint(pos));
			painter.setWaypoints(waypoints);
			
			Set<GeoPosition> positions = new HashSet<GeoPosition>();
			Iterator<Waypoint> it = waypoints.iterator();			
			while(it.hasNext()){
				positions.add(it.next().getPosition());
			}
			map.getMainMap().calculateZoomFrom(positions);
			
			String title = "";
			for(int i=0; i<c.getAdditionalInformation().length;i++){
				title += c.getAdditionalInformation()[i] + " ";
			}
			f.setTitle(title);
		}
	}
}
