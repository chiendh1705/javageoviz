package de.rwth.matse.jgeoviz.exceptions;

public class GeoCoordinateException extends RuntimeException{

	public GeoCoordinateException(){
		super("Unknown reason.");
	}
	
	public GeoCoordinateException(String msg) {
		super(msg);
	}

}
