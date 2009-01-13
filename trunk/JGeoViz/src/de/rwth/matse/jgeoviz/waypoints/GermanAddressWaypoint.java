package de.rwth.matse.jgeoviz.waypoints;

import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;

import de.rwth.matse.jgeoviz.exceptions.GeoPositionNotFoundException;
import de.rwth.matse.jgeoviz.georequests.GermanPostalCodeRequest;

public class GermanAddressWaypoint extends SizeableWaypoint {

	private String street;
	private int number;
	private String postalc;
	private String city;
	
	private GermanPostalCodeRequest request;
	
	public GermanAddressWaypoint(String pcode) {
		super(0,0);
		street = "";
		number = -1;
		this.setPostalcode(pcode);
		int base = postalc.hashCode();
		this.setColor((int)(base % 256), 
					  (int)((base/256) % 256), 
					  (int)(((base/256)/256) % 256), 
					  100);
	} 
	
	public void setPostalcode(String pcode){
		GermanPostalCodeRequest c = null;	
		c = new GermanPostalCodeRequest(pcode);
		postalc = pcode;
		super.setPosition(c.getCoordinates().toGeoPosition());
		Object[] info = c.getAdditionalInformation();
		String[] desc = c.getAdditionalInformationDescription();
		this.setCity(desc, info);
	}
	
	private void setCity(String[] desc, Object[] info){
		for(int i=0; i<desc.length; i++){
			if(desc[i].equals("city")){
				try{
					city = (String)info[i];
				}catch(ClassCastException e){
					city = "";
				}
				break;
			}
		}
	}
	
	public void setStreet(String street){
		this.street = street;
	}
	
	public void setStreetNumber(int num){
		this.number = num;
	}

	public String getStreet() {
		return street;
	}

	public int getStreetNumber() {
		return number;
	}

	public String getPostalc() {
		return postalc;
	}

	public String getCity() {
		return city;
	}
}
