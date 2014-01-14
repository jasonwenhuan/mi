package util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class TestHttpClientUtil {

	// @Before
	public void setProxy() {
		System.setProperty("http.proxyHost", "172.20.230.5");
		System.setProperty("http.proxyPort", String.valueOf(3128));

	}

	@Test
	public void doGetForPing() {
		List<String> urls = FileUtil.readFileByLines("url.txt", "utf-8");

		List<String> subList = urls.subList(0, 100);

		MyThread t = new MyThread(subList);
		t.start();

	}

	

	// @Test
	public void doGetByUrl() throws InterruptedException {
		String charset = "gbk";
		String url = "http://www.baidu.com";
		String response = HttpClientUtil.doGetAsString(url, charset);
		FileUtil.write2File("D:\\workspace_trunk\\PlayGround4JackWang\\trunk\\folder\\test.html", response, charset);

		// byte[] response = HttpClientUtil.doGetAsBytes(url);
		// FileUtil.writeFileByBytes("D:\\workspace_trunk\\PlayGround4JackWang\\trunk\\folder\\test.html",
		// response);
	}

	// @Test
	public void doGetForBingMap() throws URIException, UnsupportedEncodingException {
		String url = "http://dev.virtualearth.net/REST/V1/Imagery/Map/Road/space needle seattle?mapLayer=TrafficFlow&format=jpeg&key=AmTnKaIe4oBzLQemNpFRNlNYVxJ3J54OjYnHdxlAgZTmiiQitdlpAMJIgvBp95mn";
		String charset = "UTF-8";
		url = URIUtil.encodeQuery(url, charset);

		byte[] bytes = HttpClientUtil.doGetAsBytes(url);

		// FileUtil.writeFileByBytes("folder/bingmap.jpeg", bytes);
	}

	// @Test
	public void doPostForBingMap() {
		String url = "http://dev.virtualearth.net/REST/v1/Imagery/Map/Road/?key=AmTnKaIe4oBzLQemNpFRNlNYVxJ3J54OjYnHdxlAgZTmiiQitdlpAMJIgvBp95mn";

		NameValuePair[] nvp = new NameValuePair[] {
				new BasicNameValuePair("pp", "38.889586530732335,-77.05010175704956;23;LM"),
				new BasicNameValuePair("pp", "38.88772364638439,-77.0472639799118;7;KM"),
				new BasicNameValuePair("pp", "38.890479451480054,-77.04744637012482;1;VM"),
				new BasicNameValuePair("pp", "38.8896854931628,-77.03519403934479;45;WM") };

		byte[] bytes = HttpClientUtil.doPostAsBytes(url, nvp);

		FileUtil.writeFileByBytes("D:\\workspace_trunk\\playground\\folder\\bingmap.jpeg", bytes);
	}

	public void doPostFaceApi() {
		String url = "http://faceapi.cn/api/detect.json";

		NameValuePair[] nvp = new NameValuePair[] { new BasicNameValuePair("api_key", "21035489"),
				new BasicNameValuePair("api_secret", "bb24a7e86bc7296ce9276c0b1673fdb6"),
				new BasicNameValuePair("img_file", "38.890479451480054,-77.04744637012482;1;VM"),
				new BasicNameValuePair("pp", "38.8896854931628,-77.03519403934479;45;WM") };

		byte[] bytes = HttpClientUtil.doPostAsBytes(url, nvp);

		FileUtil.writeFileByBytes("folder/bingmap.jpeg", bytes);
	}

	// @Test
	public void doGetForIpromote() throws URIException {
		String url = "http://servedby.ipromote.com/ad/?typ=1&nid=0&site=0&adfmt=4&aid=1851055&audit=1&ccb=";

		String charset = "UTF-8";
		url = URIUtil.encodeQuery(url, charset);

		byte[] bytes = HttpClientUtil.doGetAsBytes(url);

		FileUtil.writeFileByBytes("folder/ipromote.html", bytes);
	}

	// @Test
	public void doPost() {
		String URL_CH2JP = "http://www.excite.co.jp/world/jiantizi/";
		String srcText = "べんきょうする";
		NameValuePair[] data = { new BasicNameValuePair("before", srcText), new BasicNameValuePair("wb_lp", "JACH"),
				new BasicNameValuePair("big5", "no") };

		String result = HttpClientUtil.doPost(URL_CH2JP, data);

		FileUtil.write2File("output/testHttpClient.html", result, null);
		System.out.println(result);
	}

	// @Test
	public void doPost4Estimate() {
		String url = "http://localhost:9080/estimator/ybestimator/v1.1";
		String data = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\"?><request><geos key=\"\" type=\"National\"><values></values></geos><keywords>air cleaning, air cleaning companies, air cleaning company</keywords></request>";

		String result = HttpClientUtil.doPostAsXML(url, data);

		System.out.println(result);
	}

	// @Test
	public void test() {

		File f = new File("folder/testHttpClient.html");
		System.out.println(f.getAbsolutePath());
	}

}
