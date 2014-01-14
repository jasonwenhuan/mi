package com.chinaunicom.filterman.ws.validate;

import com.chinaunicom.filterman.utilities.Logging;
import com.chinaunicom.filterman.ws.api.ExceptionHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

/**
 * User: larry
 */
public class JaxbUtils {

    @SuppressWarnings("unchecked")
    public static Object parseXmlDataObject(Class type, String xmlData) throws Exception {
        if(xmlData==null || xmlData.trim().length()==0)
            return null;

        try {
            Unmarshaller unMar = createUnMarshallerByType(type);
            ByteArrayInputStream bais = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
            return unMar.unmarshal(bais);
        } catch (Exception e) {
            throw new Exception("data un-marshall-ing failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Unmarshaller createUnMarshallerByType(Class type){
        Unmarshaller unMar = null;
        try {
            JAXBContext jax = JAXBContext.newInstance(type);
            unMar = jax.createUnmarshaller();
        } catch (Exception e) {
            Logging.logError("create unmarshaller failed", e);
        }

        return unMar;
    }

}
