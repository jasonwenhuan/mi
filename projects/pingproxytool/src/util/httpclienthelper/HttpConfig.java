package util.httpclienthelper;

import org.apache.http.NameValuePair;

public class HttpConfig {
    String scheme;
    String host;
    String path;
    Integer port;
    NameValuePair[] params;
    HttpClientHelper.HttpMethod method;
    Credential credential;

    class Credential {
        String userName;
        String passWord;

        Credential(String passWord, String userName) {
            this.passWord = passWord;
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public HttpClientHelper.HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpClientHelper.HttpMethod method) {
        this.method = method;
    }

    public NameValuePair[] getParams() {
        return params;
    }

    public void setParams(NameValuePair[] params) {
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
