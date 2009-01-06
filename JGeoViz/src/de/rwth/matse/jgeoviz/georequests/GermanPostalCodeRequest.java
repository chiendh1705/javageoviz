package de.rwth.matse.jgeoviz.georequests;

import de.rwth.matse.jgeoviz.*;
import de.rwth.matse.jgeoviz.exceptions.GeoPositionNotFoundException;

import java.io.*;

public class GermanPostalCodeRequest extends GeoRequest {

	private String postalcode;
	private String city;
	private String area;
	private String county;
	private String state_long;
	private String state_short;
	private GeoCoordinates position;
	
	private BufferedReader in;
	
	public GermanPostalCodeRequest(String code) {
		this.postalcode = code;
		this.position = null;
		
		getPostalCodeCoordinates();
	}
	
	private void getPostalCodeCoordinates(){
		try {
			this.in = new BufferedReader(new InputStreamReader(new FileInputStream("./res/postalcodes/DE.txt"),"UTF-8"));
			
			String line = in.readLine();
			String[] entries = null;
			if(line != null){
				entries = line.split("\t");
			}
			while(line != null && !postalcode.equals(entries[1])){
				line = in.readLine();
				if(line != null){
					entries = line.split("\t");
				}
			}
			
			if(line == null){
				//TODO throw Exception when postcode was not found!
				throw new GeoPositionNotFoundException();
			}
			
			this.position = new GeoCoordinates(Double.parseDouble(entries[8]), Double.parseDouble(entries[9]));
			this.city = entries[2];
			this.area = entries[7];
			this.county = entries[5];
			this.state_long = entries[3];
			this.state_short = entries[4];
			this.in.close();
		} catch (IOException e) {
			System.err.println(e);
		} catch (NumberFormatException e){
			System.err.println(e);	
		}
	}

	public String getPostalCode(){
		return this.postalcode;
	}
	
	@Override
	public Object[] getAdditionalInformation() {
		Object[] info = new Object[6];
		info[0] = this.postalcode;
		info[1] = this.city;
		info[2] = this.area;
		info[3] = this.county;
		info[4] = this.state_long;
		info[5] = this.state_short;
		return info;
	}

	@Override
	public String[] getAdditionalInformationDescription() {
		String[] desc = new String[6];
		desc[0] = "postalcode";
		desc[1] = "city";
		desc[2] = "area";
		desc[3] = "county";
		desc[4] = "state_long";
		desc[5] = "state_short";
		return desc;
	}

	@Override
	public GeoCoordinates getCoordinates() {
		return this.position;
	}

}
