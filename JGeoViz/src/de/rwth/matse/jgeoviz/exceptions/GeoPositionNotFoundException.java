package de.rwth.matse.jgeoviz.exceptions;

public class GeoPositionNotFoundException extends GeoCoordinateException {
	
	public GeoPositionNotFoundException(){
		super("Requested Position not found!");
	}
	
}
