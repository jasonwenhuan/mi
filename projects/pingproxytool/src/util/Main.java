package util;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> urls = FileUtil.readFileByLines("url.txt", "utf-8");		urls = urls.subList(0, 100000);// Jason
//		urls = urls.subList(100000, 200000);// Sally
//		urls = urls.subList(200000, 300000);// Chris
//		urls = urls.subList(300000, 400000);// Cinderella
		//urls = urls.subList(400000, 500000);// Jim
//		urls = urls.subList(500000, 600000);// Craig
//		urls = urls.subList(600000, urls.size());// Jack

		int threadSize = 100;
		int urls4EachThread = urls.size() / threadSize;

		int from = 0;
		int to = 0;
		System.out.println("Run...");
		for (int i = 0; i < threadSize; i++) {
			from = urls4EachThread * i;
			if (i == threadSize - 1) {
				to = urls.size();
			} else {
				to = urls4EachThread * (i + 1);
			}
			List<String> list = urls.subList(from, to);
			MyThread t = new MyThread(list);
			t.start();
		}
	}
}
