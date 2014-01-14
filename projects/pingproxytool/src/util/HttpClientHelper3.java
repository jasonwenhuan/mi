package util;

import java.util.Map;

import org.apache.http.HttpRequest;

public class HttpClientHelper3 {

    public enum HttpMethod{
        GET(1),
        POST(2);

        private int value;

        private HttpMethod(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static HttpMethod getInstance(int value){
            switch (value){
                case 1:
                    return GET;
                case 2:
                    return POST;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public abstract class HttpMethodCreator{

        protected HttpRequest createHttpRequest(HttpMethod httpMethod, String path, Map<String, String> params) {

            HttpRequest httpRequest;
            switch (httpMethod) {
                case GET:
                    httpRequest = new GetMethodRequest().createRequest(path, params);
                    return httpRequest;
                case POST:
                    httpRequest = new PostMethodRequest().createRequest(path, params);
                    return httpRequest;
                default:
                    throw new IllegalArgumentException();
            }
        }

    }

    public class GetMethodRequest extends HttpMethodCreator{

        public HttpRequest createRequest(String path, Map<String, String> params) {
            return null;
        }
    }

    public class PostMethodRequest extends HttpMethodCreator{

        public HttpRequest createRequest(String path, Map<String, String> params) {
            return null;
        }
    }

    public String execute(){
        HttpMethodCreator httpMethodCreator  = new GetMethodRequest();;

        return null;
    }
}
