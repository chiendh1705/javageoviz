package de.rwth.matse.jgeoviz.tilefactories;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class CachedTileFactoryInfo extends TileFactoryInfo {
	private static final int MAX = 17;
	
	public CachedTileFactoryInfo(){
		super(0,MAX,MAX,
	            256, true, true,
	            "http://tile.openstreetmap.org",
	            "x","y","z");
	}
	
	public String getTileUrl(int x, int y, int zoom) {
	    zoom = MAX-zoom;
	    URL u = null;
	    String local = "./res/openStreetMap";
	    String path =  "/"+zoom+"/"+x+"/";
	    String filename = y+".png";
	    try{
	    	File f = new File(local + path + filename);
	    	
	    	if(!f.exists()){
	        	u = new URL(this.baseURL + path + filename);
	        	InputStream is = u.openStream();
	        	f = new File(local + path);
	        	f.mkdirs();
	        	FileOutputStream fos = new FileOutputStream(local + path + filename);
	        	
	        	int b = is.read();
	        	while(b != -1){
	        		fos.write(b);
	        		b = is.read();
	        	}
	        	
	        	is.close();
	        	fos.close();
	    	}
	    	
	    	u = new File(local + path + filename).toURL();
	    }catch(Exception e){
	    	try{
				u = new File("./res/images/empty.png").toURL();
			}catch (MalformedURLException e1) {
		    	System.err.println(e);
			}
	    }
	
		return u.toExternalForm();
	}
}
