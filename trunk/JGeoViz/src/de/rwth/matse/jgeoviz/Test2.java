package de.rwth.matse.jgeoviz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import de.rwth.matse.jgeoviz.exceptions.GeoPositionNotFoundException;
import de.rwth.matse.jgeoviz.georequests.GermanPostalCodeRequest;
import de.rwth.matse.jgeoviz.tilefactories.*;
import de.rwth.matse.jgeoviz.waypoints.*;

public class Test2 implements ActionListener, MouseListener{
	
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
		
		map.getMainMap().addMouseListener(this);
		
		f.setLayout(new BorderLayout());
		
		f.add(controls, BorderLayout.NORTH);
		f.add(map, BorderLayout.CENTER);
		
		f.setVisible(true);
		f.setSize(600,400);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent arg0) {
		GermanPostalCodeRequest c = null;
		
		try{
			c = new GermanPostalCodeRequest(command.getText());
		}catch(GeoPositionNotFoundException e){
			System.out.println(e);
		}
		
		if(c != null){
			GeoPosition pos = c.getCoordinates().toGeoPosition();
			map.setCenterPosition(pos);
			
			Set<Waypoint> waypoints = painter.getWaypoints();
			Waypoint wpoint = new Waypoint(pos);
			waypoints.add(wpoint);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		GeoCoordinates mouse = new GeoCoordinates(map.getMainMap().convertPointToGeoPosition(e.getLocationOnScreen()));
		System.out.println(mouse);
		GeoCoordinates wayp = null;
		GeoCoordinates close = null;
		Set<Waypoint> waypoints = painter.getWaypoints();
		Iterator<Waypoint> it = waypoints.iterator();
		
		double distance = Double.MAX_VALUE;
		
		while(it.hasNext()){
			wayp = new GeoCoordinates(it.next().getPosition());
			double distToWp = wayp.distance(mouse);
			if(distance > distToWp){
				distance=distToWp;
				close = wayp;
			}
		}
		
		System.out.println(distance);
		// TODO change distance to 50 pixels depending on the maps zoom level
		if(distance < 2)System.out.println(close);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
