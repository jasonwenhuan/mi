package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import util.httpclienthelper.HttpConfig;
import util.httpclienthelper.ProxyServerConfig;

public class HttpClientHelper2 {

    public static final String USER_AGENT = "Mozilla/6.0 (compatible; MSIE 8.0; Windows NT 5.1)";

    private ProxyServerConfig proxy;

    public HttpClientHelper2() {
    }

    public HttpClientHelper2(ProxyServerConfig proxy) {
        this.proxy = proxy;
    }

    public ProxyServerConfig getProxy() {
        return proxy;
    }

    public void setProxy(ProxyServerConfig proxy) {
        this.proxy = proxy;
    }

    public enum HttpMethod {
        GET(1),
        POST(2);

        private int value;

        private HttpMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static HttpMethod getInstance(int value) {
            switch (value) {
                case 1:
                    return GET;
                case 2:
                    return POST;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


    private abstract class AbstractHttpMethodCreator {

        HttpRequest httpRequest;

        public HttpRequest getHttpRequest(HttpConfig httpConfig) {
            httpRequest = createHttpRequest(httpConfig);
            return httpRequest;
        }

        protected abstract HttpRequest createHttpRequest(HttpConfig httpConfig);
    }

    private class HttpMethodCreator extends AbstractHttpMethodCreator {

        @Override
        protected HttpRequest createHttpRequest(HttpConfig httpConfig) {

            HttpRequest httpRequest;
            switch (httpConfig.getMethod()) {
                case GET:
                    httpRequest = new GetMethodRequest().createRequest(httpConfig);
                    return httpRequest;
                case POST:
                    httpRequest = new PostMethodRequest().createRequest(httpConfig);
                    return httpRequest;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private abstract class HttpMethodRequest {

        abstract HttpRequest createRequest(HttpConfig httpConfig);
    }

    private class GetMethodRequest extends HttpMethodRequest {

        public HttpRequest createRequest(HttpConfig httpConfig) {

            String path = httpConfig.getPath();
            NameValuePair[] params = httpConfig.getParams();
            String strParams = "";

            for (int i = 0; i < params.length; i++) {
                strParams += params[i].getName() + "=" + params[i].getValue() + "&";
            }
            strParams = strParams.substring(0, strParams.length() - 1);


            String uri = path + "?" + strParams;
            HttpGet httpGet = new HttpGet(uri);
            return httpGet;
        }
    }

    private class PostMethodRequest extends HttpMethodRequest {

        public HttpRequest createRequest(HttpConfig httpConfig) {

            String uri = httpConfig.getPath();
            NameValuePair[] params = httpConfig.getParams();

            HttpPost httpPost = new HttpPost(uri);

            try {
                HttpEntity httpEntity = new UrlEncodedFormEntity(Arrays.asList(params), "utf-8");
                httpPost.setEntity(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                return httpPost;
            }

        }
    }

    private void applyProxy(HttpClient httpClient) {
        if (httpClient != null && proxy != null) {

            if ((proxy.getUserName() != null && !proxy.getUserName().trim().equals(""))
                    && (proxy.getPassWord() != null && !proxy.getPassWord().trim().equals(""))) {
                //todo-jack need test
                ((DefaultHttpClient) httpClient).getCredentialsProvider().setCredentials(
                        new AuthScope(proxy.getHost(), proxy.getPort()),
                        new UsernamePasswordCredentials(proxy.getUserName(), proxy.getPassWord()));

            } else {
                HttpHost httpHost = new HttpHost(proxy.getHost(), proxy.getPort());
                httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);
            }
        }
    }

    public String executeAsString(HttpConfig httpConfig, String charset) {
        String response = null;
        try {
            byte[] bytes = executeAsBytes(httpConfig);
            if (bytes != null && bytes.length > 0) {
                response = new String(executeAsBytes(httpConfig), charset);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return response;
    }

    public byte[] executeAsBytes(HttpConfig httpConfig) {
        HttpClient httpClient = new DefaultHttpClient();

        applyProxy(httpClient);
        AbstractHttpMethodCreator httpMethodCreator = new HttpMethodCreator();

        HttpHost httpHost;
        if (httpConfig.getPort() != null) {
            httpHost = new HttpHost(httpConfig.getHost(), httpConfig.getPort());
        } else {
            httpHost = new HttpHost(httpConfig.getHost());
        }

        HttpRequest request = httpMethodCreator.getHttpRequest(httpConfig);
        request.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);

        byte[] bytes = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpHost, request);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                bytes = EntityUtils.toByteArray(httpEntity);
            }

            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return bytes;
    }

}
