package googleapi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class GeoCoder {
	//google apis 
    private static final String GOOGLEGEO_BASE_URL = "http://maps.googleapis.com/maps/api/geocode/json?";
    
    //params
    private static final String CONST_PARAM = "sensor=false&address=";
    
    public String getDataFromGoogle(String address) throws IOException{
    	int retryTime = 0;
    	String geoCoderUrl = GOOGLEGEO_BASE_URL + CONST_PARAM + address;
    	//geoCoderUrl = URLEncoder.encode(geoCoderUrl, "UTF-8");
    	System.setProperty("http.proxyHost", "198.167.139.144");
        System.setProperty("http.proxyPort", String.valueOf(7070));
    	
        
        URL url;
        InputStream in = null;
        String geoStr = "";
		try {
			url = new URL(geoCoderUrl);
			
			HttpURLConnection httpurlConn = (HttpURLConnection) url.openConnection();
	        in = new BufferedInputStream(httpurlConn.getInputStream());
	        BufferedInputStream bis = new BufferedInputStream((in));
	        StringBuffer buf = new StringBuffer();
	        byte data[] = new byte[1024];
	        int count = 0;
	        while ((count = bis.read(data, 0, 1024)) > 0) {
	            buf.append(new String(data, 0, count));
	        }
	       
	        geoStr = buf.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}finally{
			 in.close();
		}
        String properData = getProperData(geoStr);
        if(properData == null){
        	System.out.println("find null" + address);
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	String retryData = getProperData(geoStr);
        	if(retryData == null){
        		return null;
        	}else{
        		return retryData;
        	}
        	//retryTime++;
        }
    	return properData;
    }
    
    public String getProperData(String sJons){
    	JSONArray results = null;
    	JSONObject geometry = null;
    	JSONObject location = null;
    	String jGeoLatitude = null;
    	String jGeoLogitude = null;
		try {
			JSONObject json = new JSONObject(sJons);
			results = json.getJSONArray("results");
			geometry = ((JSONObject)results.get(0)).getJSONObject("geometry");
			location = geometry.getJSONObject("location");
			jGeoLatitude = String.valueOf(location.get("lat"));
			jGeoLogitude = String.valueOf(location.get("lng"));
			//.getJSONObject("geometry").getJSONObject("location");
			//jGeoLatitude = al.getJSONObject("lat");
			//jGeoLogitude = al.getJSONArray("lng");
		} catch (Exception ex) {
			try {
				//System.out.println("test");
			} catch (Exception ex2) {
				return null;
			}

			return null;
		}

		return jGeoLatitude + ";" + jGeoLogitude;
    }
    
}
