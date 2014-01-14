package com.chinaunicom.filterman.ws;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: larry
 */
public class BaseTest {
    protected ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-ws.xml");

    public String rootPath = "http://localhost:8080/filterman";

    public ApacheHttpClient4 client;

    public WebResource wr;

    public ClientResponse clientResponse;

    public BaseTest() {
        ClientConfig clientConfig = new DefaultApacheHttpClient4Config();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
                Boolean.TRUE);
        clientConfig.getProperties().put(
                ApacheHttpClient4Config.PROPERTY_DISABLE_COOKIES, false);
        final ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();

        // TODO just to avoid deadlock
        threadSafeClientConnManager.setDefaultMaxPerRoute(100);
        threadSafeClientConnManager.setMaxTotal(1000);

        clientConfig.getProperties().put(
                ApacheHttpClient4Config.PROPERTY_CONNECTION_MANAGER,
                threadSafeClientConnManager);
        client = ApacheHttpClient4.create(clientConfig);
        client.getClientHandler().getHttpClient().getParams()
                .setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, false);
    }
}
