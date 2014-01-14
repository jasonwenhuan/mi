package com.hyron.generatepdf;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.lowagie.text.DocumentException;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PDFGenerater {
    
    public String generatePDF()
            throws IOException, DocumentException, com.itextpdf.text.DocumentException {
    	String rootPath = getRootPath();
        String inputFile = rootPath + "/pages/pdfTest.xhtml";
        String url = new File(inputFile).toURI().toURL().toString();
        String outputFile = rootPath + "/firstdoc.pdf";
        OutputStream os = new FileOutputStream(outputFile);
        
        ITextRenderer renderer = new ITextRenderer();
        //renderer.setDocument(url);
        renderer.setDocument(load(inputFile), rootPath+"/");
        
        renderer.getSharedContext().setBaseURL("file:/" + rootPath + "/css"); 
        
        renderer.layout();
        renderer.createPDF(os);
        
        os.close();
        return "success";
    }
    
    public String getRootPath(){
    	 String result = PDFGenerater.class.getResource("PDFGenerater.class").toString();    
         int index = result.indexOf("WEB-INF");    
         if(index == -1){    
              index = result.indexOf("bin");    
          }    
         result = result.substring(0,index);    
         if(result.startsWith("jar")){    
              result = result.substring(10);    
          }else if(result.startsWith("file")){    
              result = result.substring(6);    
          }    
         if(result.endsWith("/"))result = result.substring(0,result.length()-1);  
         return result;    
    }
    
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
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return document;
    }
/*    
    public static void main(String[] args){
    	PDFGenerater p = new PDFGenerater();
    	p.getRootPath();
    	System.out.println();
    }*/
}