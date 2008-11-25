package de.rwth.matse.jgeoviz.georequests;

import de.rwth.matse.jgeoviz.GeoCoordinates;

public interface GeoRequest {
	
	public GeoCoordinates getCoordinates();
	
	public Object[] getAdditionalInformation();
	
	public String[] getAdditionalInformationDescription();
}
