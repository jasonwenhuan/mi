package com.hyron.news.sina;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.hyron.news.sina.NewsListData.NewsData;

/**
 * @author wenhuan
 * copyright
 */
public class HtmlParser {
	private static Logger logger = Logger.getLogger(HtmlParserTest.class);
	private String url;
	private static final String DEFAULT_ENCODING = "GB2312";
	private String lastModifyDate;
	private int contentSize;
	private int readTimeout;
	private int connectTimeout;
	private int retryTimes;

	private static URLConnection urlConnection;

	public HtmlParser() {
		this.connectTimeout = 10000;
		
		this.readTimeout = 100000;
		this.retryTimes = 0;
	}

	public HtmlParser(String url, int connectTimeout, int readTimeout,
			int retryTimes) throws UnreachablePageException {
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.retryTimes = retryTimes;
		this.url = url;
		this.initWithHandleException();

	}

	public void setUrl(String url) throws UnreachablePageException {
		this.url = url;
		this.initWithHandleException();
	}

	public void initWithHandleException() throws UnreachablePageException {
		boolean flag = true;
		int counter = -1;
		while (flag) {
			try {
				this.initUrlConnection();
				flag = false;
			} catch (Exception e) {
				counter++;
				if (counter >= this.retryTimes) {
					flag = false;
					String exceptionURL = this.url;
					this.url = null;
					throw new UnreachablePageException(exceptionURL,
							e.toString());
				}
				try {
					Thread.sleep(2000);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
	}

	private void initUrlConnection() throws Exception {
		URLConnection pConnection = null;
		try {
			URL mUrl = new URL(this.url);
			pConnection = (URLConnection) mUrl.openConnection();
			pConnection.setConnectTimeout(this.connectTimeout);
			pConnection.setReadTimeout(this.readTimeout);
			this.lastModifyDate = pConnection.getHeaderField("Last-Modified");
			String strContentSize = pConnection
					.getHeaderField("Content-Length");
			if (strContentSize != null) {
				contentSize = Integer.parseInt(strContentSize);
			} else {
				contentSize = 0;
			}
		} catch (Exception e) {
			logger.warn("Set connection failed");
			logger.warn(e.toString());
		}
		urlConnection = pConnection;
	}

	// parser title and link
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<NewsData> initParser() {
		List<String> list = new ArrayList();
		List<NewsData> newsDataFilterBefore = new ArrayList<NewsData>();
		Parser parser = new Parser();
		URLConnection pConnection = urlConnection;
		try {
			parser.setConnection(pConnection);
			parser.setEncoding(DEFAULT_ENCODING);

			NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
			NodeFilter textFilter = new NodeClassFilter(TextNode.class);

			NodeList nodelist = new NodeList();

			OrFilter lastFilter = new OrFilter();
			lastFilter
					.setPredicates(new NodeFilter[] { textFilter, linkFilter });
			nodelist = parser.parse(lastFilter);
			Node[] nodes = nodelist.toNodeArray();
			String lineBefore = "";
			String lineNow = "";
			String line = "";
			for (int i = 0; i < nodes.length; i++) {
				Node node = nodes[i];
				if (node instanceof TextNode) {
					TextNode textnode = (TextNode) node;
					line = textnode.getText();
				} else if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					line = link.getLink();
				}

				if (lineNow == "" || lineNow == null) {
					lineBefore = "";
				} else {
					lineBefore = lineNow;
				}

				lineNow = line;

				if (!matchNews(lineNow) && matchNews(lineBefore)
						&& lineNow.length() > 10) {
					list.add(lineBefore.trim());
					list.add(lineNow.trim());
				} else {
					continue;
				}
			}

			for (int i = 0; i < list.size(); i++) {
				NewsData news = new NewsData();
				news.setUrl(list.get(i));
				news.setTitle(list.get(i + 1));
				i++;
				newsDataFilterBefore.add(news);
			}
		} catch (ParserException e) {
			logger.warn("Set initParser failed");
			logger.warn(e.toString());
		}
		return newsDataFilterBefore;
	}

	// parser linkText and image
	public static String[] getLinkTextAndImage(String linkUrl)
			throws ParserException {
		String[] textAndImageUrl = new String[3];
		try {
			Parser parser;
			parser = new Parser();
			parser.setURL(linkUrl);
			parser.setEncoding("GB2312");

			NodeFilter imageFilter = new NodeClassFilter(ImageTag.class);
			NodeFilter textFilter = new NodeClassFilter(TextNode.class);
			NodeList nodelist = new NodeList();
			OrFilter lastFilter = new OrFilter();

			lastFilter
					.setPredicates(new NodeFilter[] { textFilter, imageFilter });

			nodelist = parser.parse(lastFilter);

			Node[] nodes = nodelist.toNodeArray();
			String line = "";
			boolean getImage = false;
			boolean getText = false;
			int count = 0;
			boolean flag = false;
			String chinese = "";
			for (int i = 0; i < nodes.length; i++) {
				Node node = nodes[i];
				if (node instanceof TextNode) {
					TextNode textnode = (TextNode) node;
					line = textnode.getText();
				} else if (node instanceof ImageTag) {
					ImageTag titlenode = (ImageTag) node;
					line = titlenode.getImageURL();
				}
				// System.out.println("success"+line);
				// match Chinese and limit length,select text with link
				if (!getImage && line.length() < 200 && matchImage(line)) {
					getImage = true;
					textAndImageUrl[0] = line.trim();
					// System.out.println(line);
				} else if (!getText && line.trim().length() > 40
						&& matchText(line.trim())) {
					getText = true;
					textAndImageUrl[1] = line.trim();
					// System.out.println(line);
				}

				if (!flag && matchChinese(line) && line.length() < 200) {
					// System.out.println(line);
					chinese = chinese + line.trim();
					count++;
					continue;
				}
				// filter right text
				if (count > 1 && chinese.length() > 50 && chinese.length()<200) {
					flag = true;
					textAndImageUrl[1] = chinese;
				} else {
					flag = false;
					count = 0;
					chinese = "";
				}
				// System.out.println(line);
				if (getImage && getText) {
					break;
				}

			}
			if (textAndImageUrl[1] != null && textAndImageUrl[1] != "") {
				textAndImageUrl[1] = formatText(textAndImageUrl[1]);
			}
			// System.out.println(textAndImageUrl[1]);
		} catch (ParserException e) {
			logger.warn("getLinkTextAndImage failed");
			logger.warn(e.toString());
		}
		return textAndImageUrl;
	}

	private static String formatText(String line) {
		if (line == null) {
			return line;
		}

		try {
			int num = 12288;
			char[] c = { (char) num };
			String s = new String(c);
			line = line.replace(s, "");
			line = line.replace("&#8226;", ".");
			line = line.replace("&nbsp;", "");
			line = line.replace("&gt;", "");
			int indexStringEnd = line.indexOf(")");
			int indexEditorEnd = line.indexOf("】");
			if (indexStringEnd < 20 && indexEditorEnd < 30) {
				if (indexStringEnd > 0 && indexEditorEnd > 0) {
					if (indexStringEnd > indexEditorEnd) {
						line = line.substring(indexStringEnd + 1);
					} else {
						line = line.substring(indexEditorEnd + 1);
					}
				} else if (indexStringEnd > 0 && indexEditorEnd <= 0) {
					line = line.substring(indexStringEnd + 1);
				} else if (indexStringEnd <= 0 && indexEditorEnd > 0) {
					line = line.substring(indexEditorEnd + 1);
				} else {
					// line = line.substring(2);
				}
			} else if (indexStringEnd > 20 && indexEditorEnd < 30
					&& indexEditorEnd > 0) {
				line = line.substring(indexEditorEnd + 1);
			} else {
				// line = line.substring(2);
			}
		} catch (Exception e) {
			logger.warn("formatText failed");
			logger.warn(e.toString());
		}
		return line.toString();
	}

	private static boolean matchChinese(String line) {
		String regex = "[\\u4e00-\\u9fa5]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static boolean matchNews(String line) {
		String regex = "http://news.sina.com.cn/(c|w)/.*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return true;
		}
		return false;
	}

	private static boolean matchText(String line) {
		String regex = "[\\s\\s]*[。|，]$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return true;
		}
		return false;
	}

	private static boolean matchImage(String line) {
		String regex = "http://.*/dy/(c|w)/.*jpg";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getContentSize() {
		return contentSize;
	}

}
