package googleapi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReadExcel {
	public static int time = 0;
	public Document load(String xmlPath){
    	Document document = null;
    	try{
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = factory.newDocumentBuilder();
    		builder.setEntityResolver(new EntityResolver() {
				@Override
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {
					 //return new InputSource(new ByteArrayInputStream("<!DOCTYPE beans PUBLIC '-//SPRING//DTD BEAN//EN' 'http://www.springframework.org/dtd/spring-beans.dtd'>".getBytes()));
					 return new InputSource(new ByteArrayInputStream("".getBytes()));
					 //return null;
				}
			});
    		document = builder.parse(new File(xmlPath));
    		document.normalize();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return document;
    }
	
	public List<String> getXmlData(Document document){
		List<String> allCities = new ArrayList<String>();
		
		NodeList list = document.getElementsByTagName("City");
         
         for (int i = 0; i < list.getLength(); i++) {
        	 String detailInf = "";
        	 detailInf += "Canada,";
        	 
        	 Node node = list.item(i);
    		 NamedNodeMap attrList = node.getAttributes();
    		 String cityName = attrList.getNamedItem("Name").getNodeValue();
    		 String cityId = attrList.getNamedItem("ID").getNodeValue();
    		 String state = getState(cityId.split("-")[1]);
    		 detailInf += (state.replaceAll(" ", "+") + ",");
    		 detailInf += cityName.replaceAll(" ", "+");
    		 
    		 //System.out.println(detailInf + "   " + (time++));
    		 allCities.add(detailInf);
         }
		return allCities;
	}
	
	public String getState(String shortName){
		if(shortName.equalsIgnoreCase("AB")){
			return "Alberta";
		}else if(shortName.equalsIgnoreCase("BC")){
			return "British Columbia";
		}else if(shortName.equalsIgnoreCase("MB")){
			return "Manitoba";
		}else if(shortName.equalsIgnoreCase("NB")){
			return "New Brunswick";
		}else if(shortName.equalsIgnoreCase("NF")){
			return "Newfoundland";
		}else if(shortName.equalsIgnoreCase("NL")){
			return "Newfoundland and Labrador";
		}else if(shortName.equalsIgnoreCase("NS")){
			return "Nova Scotia";
		}else if(shortName.equalsIgnoreCase("NT")){
			return "Northwest Territories";
		}else if(shortName.equalsIgnoreCase("NU")){
			return "Nunavut";
		}else if(shortName.equalsIgnoreCase("ON")){
			return "Ontario";
		}else if(shortName.equalsIgnoreCase("PE")){
			return "Prince Edward Island";
		}else if(shortName.equalsIgnoreCase("QC")){
			return "Quebec";
		}else if(shortName.equalsIgnoreCase("SK")){
			return "Saskatchewan";
		}else if(shortName.equalsIgnoreCase("YT")){
			return "Yukon";
		}else{
			return "";
		}
	}
}
