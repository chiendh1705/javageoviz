package de.rwth.matse.jgeoviz;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import de.rwth.matse.jgeoviz.exceptions.GeoCoordinateException;

public class GeoCoordinates {
	private double latitude;
	private double longitude;
	
	public GeoCoordinates(double lat, double lng){
		this.setLatitude(lat);
		this.setLongitude(lng);
	}
	
	public GeoCoordinates(GeoPosition p){
		this.setLatitude(p.getLatitude());
		this.setLongitude(p.getLongitude());
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
			throw new GeoCoordinateException("Latitude not valid.");
		}
	}

	public void setLongitude(double lng) {
		if(lng < 180 || lng > -180){
			this.longitude = lng;
		}else{
			throw new GeoCoordinateException("Longitude not valid.");
		}
	}
	
	public double distance(GeoCoordinates c){
		double distlat = (this.getLatitude()-c.getLatitude()) * Math.PI /180;
		double distlng = (this.getLongitude()-c.getLongitude()) * Math.PI /180;
		
		double a = Math.sin(distlat / 2) * Math.sin(distlat / 2) + 
		           Math.cos(this.getLatitude()) * Math.cos(c.getLatitude()) * Math.sin(distlng / 2) * Math.sin(distlng / 2);
		
		double d = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 6372.797;
		return d;
	}
	
	public String toString(){
		return "[" + latitude +"N, " + longitude + "E]"; 
	}
	
	public GeoPosition toGeoPosition(){
		return new GeoPosition(latitude, longitude);
	}
}
