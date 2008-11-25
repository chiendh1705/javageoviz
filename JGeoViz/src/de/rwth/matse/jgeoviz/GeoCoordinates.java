package de.rwth.matse.jgeoviz;

import de.rwth.matse.jgeoviz.exceptions.GeoCoordinateException;

public class GeoCoordinates {
	private double latitude;
	private double longitude;
	
	public GeoCoordinates(double lat, double lng){
		this.setLatitude(lat);
		this.setLongitude(lng);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double lat) {
		if(lat < 90 || lat > -90){
			this.latitude = lat;
		}else{
			throw new GeoCoordinateException();
		}
	}

	public void setLongitude(double lng) {
		if(lng < 180 || lng > -180){
			this.longitude = lng;
		}else{
			throw new GeoCoordinateException();
		}
	}
	
	
}
