package de.rwth.matse.jgeoviz.waypoints;

import java.awt.*;

import org.jdesktop.swingx.mapviewer.*;

public class SizeableWaypoint extends Waypoint{

	private Color color;
	private int size;
	
	public SizeableWaypoint(Waypoint wp){
		super(wp.getPosition());
		if((wp instanceof SizeableWaypoint)){
			color = ((SizeableWaypoint)wp).getColor();
			size = ((SizeableWaypoint)wp).getSize();
		}else{
			color = new Color(0,255,0,127);
			size = 30;
		}
	}
	
	public SizeableWaypoint(double latitude, double longitude) {
		super(latitude, longitude);
		color = new Color(0,0,0,0);
		size = 25;
	}

	public SizeableWaypoint(GeoPosition coord) {
		super(coord);
		color = new Color(0,0,0,0);
		size = 25;
	}

	public void setColor(int r, int g, int b, int a){
		this.color = new Color(r,g,b,a);
	}
	
	public void setSize(int s){
		this.size = s;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}
}
