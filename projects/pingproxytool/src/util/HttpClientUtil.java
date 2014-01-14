package util;

import httpclient.StringCharsetResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static boolean USE_PROXY = true;

	private HttpClientUtil() {
	}

	public static String doGet(String url) {

		return doGet(url, null);
	}

	public static String doGet(String url, String charset) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet hg = initGetMethod(url);

		ResponseHandler<String> handler = null;
		if (charset == null) {
			handler = new StringCharsetResponseHandler();
		} else {
			handler = new StringCharsetResponseHandler(charset);
		}

		String response = "";
		try {
			response = httpClient.execute(hg, handler);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return response;
	}

	public static String doGetAsString(String url, String charset) {
		String response = null;
		try {
			byte[] bytes = doGetAsBytes(url);
			if (bytes != null && bytes.length > 0) {
				response = new String(doGetAsBytes(url), charset);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return response;
	}

	public static byte[] doGetAsBytes(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet hg = initGetMethod(url);

		HttpResponse response = null;
		HttpEntity entity = null;
		byte[] bytes = null;
		try {
			HttpHost hh = new HttpHost("172.20.230.5", 3128);
			httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, hh);
			httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
			response = httpClient.execute(hg);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				bytes = EntityUtils.toByteArray(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return bytes;
	}

	public static int ping(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet hg = initGetMethod(url);

		HttpResponse response = null;
		HttpEntity entity = null;
		int responseCode = -1;
		try {
			 HttpHost hh = new HttpHost("172.20.230.5", 3128);
			 httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
			 hh);
			httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, true);
			response = httpClient.execute(hg);
			responseCode = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return responseCode;
	}

	public static String doPost(String url, NameValuePair data[]) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost hp = initPostMethod(url);

		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HttpClientConstants.CHARSET_UTF8);
		hp.setHeader("Content-Type", "text/xml");
		try {
			HttpEntity entity = new UrlEncodedFormEntity(Arrays.asList(data), HttpClientConstants.CHARSET_UTF8);
			hp.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			ResponseHandler<String> handler = new StringCharsetResponseHandler();
			String response = httpClient.execute(hp, handler);
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return null;

	}

	public static byte[] doPostAsBytes(String url, NameValuePair data[]) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost hp = initPostMethod(url);

		httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HttpClientConstants.CHARSET_UTF8);
		HttpHost hh = new HttpHost("172.20.230.5", 3128);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, hh);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(Arrays.asList(data), HttpClientConstants.CHARSET_UTF8);
			hp.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse response = null;
		HttpEntity entity = null;
		byte[] bytes = null;
		try {
			response = httpClient.execute(hp);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				bytes = EntityUtils.toByteArray(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return bytes;

	}

	public static String doPostAsXML(String url, String xmlString) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost hp = initPostMethod(url);

		// httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
		// HttpClientConstants.CHARSET_UTF8);
		// hp.setHeader("Content-Type", "text/html;charset=utf-8");
		try {
			HttpEntity entity = new StringEntity(xmlString, "text/xml", "iso-8859-1");
			hp.setEntity(entity);
			hp.addHeader("Content-Type", "application/xml;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse response = null;
		try {
			response = httpClient.execute(hp);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);
			}
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return null;

	}

	public static HttpGet initGetMethod(String url) {
		HttpGet hg = new HttpGet(url);

		hg.getParams().setParameter(CoreProtocolPNames.USER_AGENT, HttpClientConstants.USER_AGENT);

		return hg;
	}

	public static HttpPost initPostMethod(String url) {
		HttpPost hp = new HttpPost(url);

		hp.getParams().setParameter(CoreProtocolPNames.USER_AGENT, HttpClientConstants.USER_AGENT);

		return hp;
	}
}
