package de.rwth.matse.jgeoviz;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
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
		
		//TEST Fimendaten:
		String[] plzs = {"52062", "52074", "52076", "12045", 
						 "60314", "52074", "50672", "52409", 
						 "52072", "52068", "41836", "52428",
						 "52425", "52068", "80807", "16761",
						 "52068", "52428", "52072", "81369",
						 "82024", "52072", "01187", "52072",
						 "44629", "10405", "64293", "52070",
						 "52428", "52220", "52076", "52072"};
		for(int i=0;i<plzs.length; i++){
			this.addWaypoint(plzs[i]);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		this.addWaypoint(command.getText());
	}

	public void addWaypoint(String plz){
		Set<Waypoint> waypoints = painter.getWaypoints();
		try{
			SizeableWaypoint wpoint = new GermanAddressWaypoint(plz);
			waypoints.add(wpoint);
			map.setCenterPosition(wpoint.getPosition());
		}catch(GeoPositionNotFoundException e){
			System.err.println(e.getMessage());
			System.err.println(plz);
		}		
		Set<GeoPosition> positions = new HashSet<GeoPosition>();
		Iterator<Waypoint> it = waypoints.iterator();			
		while(it.hasNext()){
			positions.add(it.next().getPosition());
		}
		map.getMainMap().calculateZoomFrom(positions);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		GeoCoordinates mouse = new GeoCoordinates(map.getMainMap().convertPointToGeoPosition(point));
		GeoCoordinates waypCoord = null;
		GeoCoordinates closeCoord = null;		
		Waypoint wayp = null;
		Waypoint close = null;
		Set<Waypoint> waypoints = painter.getWaypoints();
		Iterator<Waypoint> it = waypoints.iterator();
		
		double distance = Double.MAX_VALUE;
		
		while(it.hasNext()){
			wayp = it.next();
			waypCoord = new GeoCoordinates(wayp.getPosition());
			double distToWp = waypCoord.distance(mouse);
			if(Math.abs(distance-distToWp) < 0.1){
				if(waypCoord.distance(closeCoord) < 0.1){
					System.out.println("too close!");
					GeoPosition pos = wayp.getPosition();
					double lat = pos.getLatitude()+(0.004*Math.random()-0.002);
					double lon = pos.getLongitude()+(0.004*Math.random()-0.002);
					wayp.setPosition(new GeoPosition(lat,lon));
					map.repaint();
				}
			}
			if(distance > distToWp){
				distance=distToWp;
				closeCoord = waypCoord;
				close = wayp;
			}
		}
		Point2D waypointPixel = map.getMainMap().getTileFactory().geoToPixel(closeCoord.toGeoPosition(), map.getMainMap().getZoom());
		Point2D clickPixel = map.getMainMap().getTileFactory().geoToPixel(mouse.toGeoPosition(), map.getMainMap().getZoom());
		if(clickPixel.distance(waypointPixel)<15){
			System.out.println(((GermanAddressWaypoint)close).getPostalc() + " "+ ((GermanAddressWaypoint)close).getCity());
		}
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
