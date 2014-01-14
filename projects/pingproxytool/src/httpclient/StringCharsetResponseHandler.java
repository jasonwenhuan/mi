package httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import util.HttpClientConstants;

public class StringCharsetResponseHandler implements ResponseHandler<String>{
	
	private String charset;
	public StringCharsetResponseHandler() {
	}
	
	public StringCharsetResponseHandler(String charset) {
		this.charset = charset;
	}
    public String handleResponse(final HttpResponse response)
            throws HttpResponseException, IOException {
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }

        Header header = response.getEntity().getContentType();
        if (header != null){
        	String s = header.getValue();
        }
        
        HttpEntity entity = response.getEntity();
        String cs = charset;
        
        if(cs == null) {
        	cs = EntityUtils.getContentCharSet(entity);
        }
        
        if(cs == null) {
        	cs = HttpClientConstants.CHARSET_UTF8;
        }
        
        return entity == null ? null : EntityUtils.toString(entity, cs);
    }

}
