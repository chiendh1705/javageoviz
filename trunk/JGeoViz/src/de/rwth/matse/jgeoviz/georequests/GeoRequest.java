package de.rwth.matse.jgeoviz.georequests;

import de.rwth.matse.jgeoviz.GeoCoordinates;

public abstract class GeoRequest {
	
	public abstract GeoCoordinates getCoordinates();
	
	public abstract Object[] getAdditionalInformation();
	
	public abstract String[] getAdditionalInformationDescription();
}
