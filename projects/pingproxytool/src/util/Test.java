package util;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<String> urls = FileUtil.readFileByLines("url.txt", "utf-8");
	}
}
