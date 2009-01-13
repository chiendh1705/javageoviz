package de.rwth.matse.jgeoviz.waypoints;

import java.awt.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

public class CircularWaypointRenderer implements WaypointRenderer{

	@Override
	public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
		
		SizeableWaypoint swp = new SizeableWaypoint(wp);
		int size = swp.getSize();
		
		g.setColor(swp.getColor());
		g.fillOval(-size/2, -size/2, size, size);
		//g.fillOval(0, 0, size,size);
		
		g.setColor(Color.BLACK);
		g.drawOval(-size/2, -size/2, size, size);
		
		return true;
	}

}
